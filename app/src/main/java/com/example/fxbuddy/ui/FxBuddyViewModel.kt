package com.example.fxbuddy.ui

import androidx.lifecycle.*
import com.example.fxbuddy.db.model.DatabaseRate
import com.example.fxbuddy.domain.RateData
import com.example.fxbuddy.domain.RateHistoryData
import com.example.fxbuddy.repository.FxBuddyRepository
import com.example.fxbuddy.util.DispatcherProvider
import com.example.fxbuddy.util.Event
import com.example.fxbuddy.util.NetworkUtils
import com.example.fxbuddy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FxBuddyViewModel @Inject constructor(
    private val repository: FxBuddyRepository,
    private val dispatchers: DispatcherProvider,
    private val networkConnection: NetworkUtils
) : ViewModel() {

    private val _selectedFromCurrency = MutableLiveData<String>()
    val selectedFromCurrency: LiveData<String> = _selectedFromCurrency

    private val _selectedToCurrency = MutableLiveData<String>()
    val selectedToCurrency: LiveData<String> = _selectedToCurrency

    private val _currentDate = MutableLiveData<String>()
    val currentDate: LiveData<String> = _currentDate

    private val _futureDate = MutableLiveData<String>()
    val futureDate: LiveData<String> = _futureDate

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isCurrencyLoading = MutableLiveData<Boolean>()
    val isCurrencyLoading: LiveData<Boolean> = _isCurrencyLoading

    private val _fxRate = MutableLiveData<String>()
    val fxRate: LiveData<String> = _fxRate

    private val _allRates = repository.getAllRates()
    val allRates: LiveData<List<DatabaseRate>> = _allRates.asLiveData()

    private val _showMessage = MutableLiveData<Event<String>>()
    val showMessage: LiveData<Event<String>> = _showMessage

    private val _navigate = MutableLiveData<Event<Unit>>()
    val navigate: LiveData<Event<Unit>> = _navigate

    fun getForeignExchangeRate() {
        viewModelScope.launch(dispatchers.io) {
            val ratesData = currentDate.value?.let {
                selectedFromCurrency.value?.let { selectedFromCurrency ->
                    selectedToCurrency.value?.let { selectedToCurrency ->
                        RateData(
                            selectedFromCurrency, selectedToCurrency
                        )
                    }
                }
            }
            if (networkConnection.hasNetworkConnection()) {
                if (selectedToCurrency.value == selectedFromCurrency.value) {
                    _showMessage.postValue(Event("Please select two different currencies."))
                    return@launch
                }
                _isLoading.postValue(true)
                when (val ratesResponse =
                    ratesData?.let { repository.getForeignExchangeRate(it) }) {
                    is Resource.Error<*> -> {
                        _isLoading.postValue(false)
                        _showMessage.postValue(Event(ratesResponse.message.orEmpty()))
                    }
                    is Resource.Success<*> -> {
                        _isLoading.postValue(false)
                        val rates = ratesResponse.data?.price
                        _isCurrencyLoading.postValue(true)
                        _fxRate.postValue(rates.toString())
                    }
                }
            } else {
                _showMessage.postValue(Event("Please check your network."))
            }
        }
    }

    fun getForeignExchangeRateHistory() {
        viewModelScope.launch(dispatchers.io) {
            val rateHistoryData = currentDate.value?.let { currentDate ->
                futureDate.value?.let { futureDate ->
                    RateHistoryData(
                        currentDate,
                        futureDate,
                        "daily",
                        "${selectedFromCurrency.value}${selectedToCurrency.value}",
                        "close"
                    )
                }
            }

            if (networkConnection.hasNetworkConnection()) {
                if (selectedFromCurrency.value != "USD" && selectedToCurrency.value != "USD") {
                    _showMessage.postValue(Event("At least one currency must be USD"))
                    return@launch
                }
                _isLoading.postValue(true)
                when (val ratesResponse =
                    rateHistoryData?.let { repository.getForeignExchangeRateHistory(it) }) {
                    is Resource.Error<*> -> {
                        _isLoading.postValue(false)
                        _showMessage.postValue(Event(ratesResponse.message.orEmpty()))
                    }
                    is Resource.Success<*> -> {
                        _isLoading.postValue(false)
                        _navigate.postValue(Event(Unit))
                    }
                }
            } else {
                _showMessage.postValue(Event("Please check your network."))
            }
        }
    }

    fun setToCurrency(currency: String) {
        _selectedToCurrency.postValue(currency)
    }

    fun setFromCurrency(currency: String) {
        _selectedFromCurrency.postValue(currency)
    }

    fun setCurrentDate(date: String) {
        _currentDate.postValue(date)
    }

    fun setFutureDate(date: String) {
        _futureDate.postValue(date)
    }
}