package com.example.fxbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fxbuddy.databinding.FragmentForeignExchangeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForeignExchangeFragment : Fragment() {

    private lateinit var binding: FragmentForeignExchangeBinding

    private val fxViewModel: FXViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForeignExchangeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = fxViewModel
        }

        binding.spFromCurrency.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    setFromCurrency(p0?.selectedItem.toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }

        binding.spToCurrency.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    setToCurrency(p0?.selectedItem.toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        return binding.root
    }

    fun setToCurrency(item: String) {
        fxViewModel.selectedToCurrency?.value = item
    }

    fun setFromCurrency(item: String) {
        fxViewModel.selectedFromCurrency?.value = item
    }
}