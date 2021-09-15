package com.example.fxbuddy.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.fxbuddy.R
import com.example.fxbuddy.databinding.FragmentForeignExchangeRateHistoryBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForeignExchangeHistoryFragment : Fragment() {

    private lateinit var binding: FragmentForeignExchangeRateHistoryBinding

    private val fxBuddyViewModel: FxBuddyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentForeignExchangeRateHistoryBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = fxBuddyViewModel
            }
        setupBarChart()
        subscribeToObservers()

        return binding.root
    }

    private fun subscribeToObservers() {
        fxBuddyViewModel.allRates.observe(viewLifecycleOwner, Observer {
            it?.let {
                val allRates =
                    it.indices.map { i ->
                        BarEntry(i.toFloat(), it[i].close.toFloat())

                    }
                val barDataSet =
                    BarDataSet(allRates, getString(R.string.graph_description)).apply {
                        valueTextColor = Color.BLUE
                        color = ContextCompat.getColor(requireContext(), R.color.primaryLightColor)
                    }
                val data = BarData(barDataSet)
                data.barWidth = 0.6f
                binding.barChart.data = data

                setupBarChart()

                binding.barChart.notifyDataSetChanged()
                binding.barChart.invalidate()
            }
        })
    }

    private fun setupBarChart() {
        binding.barChart.setVisibleXRangeMaximum(8f)//mine
        binding.barChart.extraBottomOffset = 16f
        binding.barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            axisLineColor = Color.BLUE
            textColor = Color.BLUE
            setDrawGridLines(false)
        }

        binding.barChart.axisLeft.apply {
            axisLineColor = Color.BLUE
            textColor = Color.BLUE
            setDrawGridLines(false)
        }
        binding.barChart.axisRight.apply {
            axisLineColor = Color.BLUE
            textColor = Color.BLUE
            setDrawGridLines(false)
        }
        binding.barChart.apply {
            description.text = getString(R.string.graph_description)
            legend.isEnabled = false
        }
    }
}