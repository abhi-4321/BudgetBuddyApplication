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
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.tabs.TabLayout

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
        dataSet.setColors(ColorTemplate.rgb("#317773"))
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
        binding.barChart.setTouchEnabled(false)

// Disable zooming on both the X and Y axes
        binding.barChart.setScaleEnabled(false)
        binding.barChart.setScaleXEnabled(false)
        binding.barChart.setScaleYEnabled(false)
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


        //implementing tab layout
        val monthsTab = binding.tabLayout.newTab()
        val weeksTab = binding.tabLayout.newTab()
        monthsTab.text = "Months"
        weeksTab.text = "Weeks"
        binding.tabLayout.addTab(monthsTab)
        binding.tabLayout.addTab(weeksTab)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // Handle tab selection
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // Handle tab unselection
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // Handle tab reselection
            }
        })
//implementing pichart which shows data related to category

        val records = ArrayList<PieEntry>()
        records.add(PieEntry(32f, "food & beverages"))
        records.add(PieEntry(62f, "petrol"))
        records.add(PieEntry(20f, "rent"))
        records.add(PieEntry(72f, "bills"))
        records.add(PieEntry(82f, "other expenses"))
        records.add(PieEntry(22f, "vehicle Maintenance"))
        records.add(PieEntry(34f, "medical check up"))
        records.add(PieEntry(12f, "pets"))

        val piedataset = PieDataSet(records, "Report")

        val colors = ColorTemplate.COLORFUL_COLORS.toList()
        piedataset.setColors(colors)
        piedataset.setValueTextColor(Color.BLACK)
        piedataset.setValueTextSize(10f)
        binding.piChart.description.isEnabled = false
        val piedata = PieData(piedataset)
        binding.piChart.data = piedata

       binding.piChart.invalidate()
        binding.piChart.centerText = "Spending based on category"
        binding.piChart.setDrawSliceText(false)
        return binding.root
    }


}