package com.example.budgetbuddy.ui.home

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentHomeBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val information = ArrayList<BarEntry>()

        information.add(BarEntry(1f, 10f))
        information.add(BarEntry(4f, 20f))
        information.add(BarEntry(5f, 30f))
        information.add(BarEntry(2f, 50f))
        information.add(BarEntry(3f, 10f))
        information.add(BarEntry(6f, 10f))

        val dataSet = BarDataSet(information, "Report")
//        dataSet.colors = ColorTemplate.MATERIAL_COLORS.asList()
        dataSet.setColors(ColorTemplate.rgb("#FF5733"))
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 10f


        val barData = BarData(dataSet)
         binding.barChart.setDrawValueAboveBar(true)
        binding.barChart.data = barData


//        binding.barChart.xAxis.setDrawGridLines(false)
//        binding.barChart.axisRight.setDrawGridLines(false)
//       binding.barChart.axisLeft.setDrawGridLines(false)

//        binding.barChart.xAxis.setDrawLabels(false)
//        binding.barChart.axisLeft.setDrawLabels(false)
//        binding.barChart.axisRight.setDrawLabels(false)
        binding.barChart.setDrawBorders(false)

//        binding.barChart.description.text = "Bar Report Demo"
//        binding.barChart.animateY(2000, Easing.Linear)
        binding.barChart.description.isEnabled = false


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
        xAxis.textSize = 10f


        return binding.root
    }


}