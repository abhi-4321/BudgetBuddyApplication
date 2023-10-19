package com.example.budgetbuddy.ui.home

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.FragmentMonthlyBinding
import com.example.budgetbuddy.repository.HomeRepository
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.*

class MonthlyFragment : Fragment() {

    companion object {
        fun newInstance() = MonthlyFragment()
    }

    private lateinit var viewModel: MonthlyViewModel
    private lateinit var binding: FragmentMonthlyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transactionDao = Database.getInstance(requireContext()).transactionDao()
        val repository = HomeRepository(transactionDao)
        viewModel = ViewModelProvider(this,MonthlyViewModelFactory(repository))[MonthlyViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonthlyBinding.inflate(layoutInflater, container, false)

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

        viewModel.getMonthlyData().observe(parentFragment as LifecycleOwner) { itt ->
            val monthNames = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

            // Create a list of BarEntry objects, one for each month
            val information = monthNames.mapIndexed { index, month ->
                val dataForMonth = itt.find { it.month.substring(0,3) == month } // Find data for the month
                val value = dataForMonth?.total?.toFloat() ?: 0f // Use 0 as a placeholder for null data
                BarEntry(index.toFloat(), value)
            }

            val dataSet = BarDataSet(information, "Report")

            dataSet.setColors(ColorTemplate.rgb("#317773"))
            dataSet.valueTextColor = Color.BLACK
            dataSet.valueTextSize = 10f


            val barData = BarData(dataSet)
            binding.barChart.data = barData
            binding.barChart.barData.barWidth = 0.8f

            val customXAxisLabels = monthNames.toTypedArray()
            xAxis.valueFormatter = IndexAxisValueFormatter(customXAxisLabels)
            xAxis.position = XAxis.XAxisPosition.BOTTOM

            binding.barChart.invalidate()

            for (entry in itt)
                if (entry.month.substring(0,3) == SimpleDateFormat("MMMM", Locale.getDefault()).format(System.currentTimeMillis()).substring(0,3))
                    binding.thisMonth.text = "₹"+entry.total.toString()
        }

        return binding.root
    }

    inner class MonthlyViewModelFactory(private val homeRepository: HomeRepository) : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MonthlyViewModel(homeRepository) as T
        }
    }
}