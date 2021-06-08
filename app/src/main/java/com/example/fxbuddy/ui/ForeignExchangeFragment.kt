package com.example.fxbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fxbuddy.R
import com.example.fxbuddy.databinding.FragmentForeignExchangeBinding
import com.example.fxbuddy.util.EventObserver
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoField

@AndroidEntryPoint
class ForeignExchangeFragment : Fragment() {

    private lateinit var binding: FragmentForeignExchangeBinding

    private val fxBuddyViewModel: FxBuddyViewModel by viewModels()

    var fromSelectedIndex = 0
    var toSelectedIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForeignExchangeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = fxBuddyViewModel
        }
        setupObservers()
        val date = LocalDate.now()

        fxBuddyViewModel.setCurrentDate(getPastDate())
        fxBuddyViewModel.setFutureDate(date.toString())

        binding.floatingActionButton.setOnClickListener {
            swapCurrencies()
        }

        binding.spFromCurrency.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    items: AdapterView<*>?,
                    p1: View?,
                    selectedIndex: Int,
                    p3: Long
                ) {
                    fxBuddyViewModel.setFromCurrency(items?.selectedItem.toString())
                    fromSelectedIndex = selectedIndex
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        binding.spToCurrency.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    items: AdapterView<*>?,
                    p1: View?,
                    selectedIndex: Int,
                    p3: Long
                ) {
                    fxBuddyViewModel.setToCurrency(items?.selectedItem.toString())
                    toSelectedIndex = selectedIndex
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        return binding.root
    }

    private fun setupObservers() {
        fxBuddyViewModel.showMessage.observe(viewLifecycleOwner, EventObserver { message ->
            showMessage(message)
        })

        fxBuddyViewModel.navigate.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(ForeignExchangeFragmentDirections.actionForeignExchangeFragmentToForeignExchangeHistoryFragment())
        })
    }

    private fun swapCurrencies() {
        fxBuddyViewModel.getForeignExchangeRate()
        binding.editTextTextPersonName.text.clear()
        val tempFromPosition = fromSelectedIndex
        binding.spToCurrency.setSelection(fromSelectedIndex)
        binding.spFromCurrency.setSelection(toSelectedIndex)
        fromSelectedIndex = toSelectedIndex
        toSelectedIndex = tempFromPosition
    }

    private fun getPastDate(): String {
        //This can be moved to a "DateUtil" class
        var numberOfDays: Long = 30
        val date = LocalDate.now()
        var pastDate: LocalDate = date

        for (i in 1..3) {
            pastDate = date.minusDays(numberOfDays)
            val day: DayOfWeek = DayOfWeek.of(pastDate[ChronoField.DAY_OF_WEEK])
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                numberOfDays++
            } else {
                continue
            }
        }

        return pastDate.toString()
    }

    private fun showMessage(message: String) {
        val snackbar = Snackbar.make(
            requireView(),
            message, Snackbar.LENGTH_SHORT
        )
        snackbar.show()
    }
}