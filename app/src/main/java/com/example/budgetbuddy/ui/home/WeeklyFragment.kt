package com.example.budgetbuddy.ui.home

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.FragmentMonthlyBinding
import com.example.budgetbuddy.databinding.FragmentWeeklyBinding
import com.example.budgetbuddy.repository.HomeRepository
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.*

class WeeklyFragment : Fragment() {

    companion object {
        fun newInstance() = WeeklyFragment()
    }

    private lateinit var viewModel: WeeklyViewModel
    private lateinit var binding: FragmentWeeklyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transactionDao = Database.getInstance(requireContext()).transactionDao()
        val repository = HomeRepository(transactionDao)
        viewModel = ViewModelProvider(this,WeeklyViewModelFactory(repository))[WeeklyViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeeklyBinding.inflate(layoutInflater,container,false)

        binding.barChart.description.isEnabled = false
        binding.barChart.setDrawBorders(false)
        binding.barChart.setDrawValueAboveBar(true)
        binding.barChart.axisRight.apply {
            setDrawGridLines(false)
            setDrawLabels(false)
            setDrawAxisLine(false)
        }
        binding.barChart.axisLeft.apply {
            setDrawGridLines(false)
            setDrawLabels(false)
            setDrawAxisLine(false)
        }
        binding.barChart.xAxis.apply {
            setDrawGridLines(false)
            setDrawLabels(false)
            setDrawAxisLine(false)
        }
        binding.barChart.setTouchEnabled(false)

// Disable zooming on both the X and Y axes
        binding.barChart.setScaleEnabled(false)
        binding.barChart.isScaleXEnabled = false
        binding.barChart.isScaleYEnabled = false
        binding.barChart.setDrawGridBackground(false)
        binding.barChart.setDrawBorders(false)
        binding.barChart.legend.isEnabled = false
        binding.barChart.setDrawMarkers(false)

        val xAxis = binding.barChart.xAxis
        xAxis.isEnabled = true // Show the X-axis
        xAxis.position = XAxis.XAxisPosition.BOTTOM // Set the X-axis position to the bottom (default)
        xAxis.setDrawGridLines(false) // Hide X-axis grid lines (if needed)
        xAxis.setDrawAxisLine(true) // Hide X-axis axis line (if needed)
        xAxis.setDrawLabels(true) // Show X-axis labels
        xAxis.labelRotationAngle = 0f // Set the label rotation angle (if needed)

        // Set X-axis label text color
        xAxis.textSize = 9f
        xAxis.labelCount = 12
        binding.barChart.axisLeft.axisMinimum = 0.1f
        binding.barChart.setVisibleXRange(1f,12f)
        binding.barChart.moveViewToX(1f)

        viewModel.getWeeklyData().observe(parentFragment as LifecycleOwner) {



            val information = it.map { originalEntry ->
                BarEntry(originalEntry.month.toFloat(), originalEntry.total.toFloat())
            }
            val dataSet = BarDataSet(information, "Report")

            dataSet.setColors(ColorTemplate.rgb("#317773"))
            dataSet.valueTextColor = Color.BLACK
            dataSet.valueTextSize = 10f


            val barData = BarData(dataSet)
            binding.barChart.data = barData

            binding.barChart.invalidate()

            for (entry in it)
                if (entry.month.substring(0,3) == SimpleDateFormat("MMMM", Locale.getDefault()).format(System.currentTimeMillis()).substring(0,3))
                    binding.thisWeek.text = "₹"+entry.total.toString()
        }

        return binding.root
    }

    inner class WeeklyViewModelFactory(private val homeRepository: HomeRepository) : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WeeklyViewModel(homeRepository) as T
        }
    }
}