package com.example.budgetbuddy.ui.home

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.FragmentHomeBinding
import com.example.budgetbuddy.repository.HomeRepository
import com.example.budgetbuddy.repository.IncomeSpentRepo
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var incomeSpentRepo : IncomeSpentRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transactionDao = Database.getInstance(requireContext()).transactionDao()
        val homeRepository = HomeRepository(transactionDao)
        viewModel = ViewModelProvider(requireActivity(),HomeViewModelFactory(homeRepository))[HomeViewModel::class.java]

        val incomeSpentDao = Database.getInstance(requireContext()).incomeSpentDao()
        incomeSpentRepo = IncomeSpentRepo(incomeSpentDao)
    }

    inner class HomeViewModelFactory(private val homeRepository: HomeRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(homeRepository) as T
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val adapter = ViewPagerAdapter(
            childFragmentManager, lifecycle
        )
        binding.viewpager.adapter = adapter


        TabLayoutMediator(
            binding.tabLayout, binding.viewpager
        ) { tab: TabLayout.Tab, _: Int ->
            tab.text = "Monthly"
        }.attach()

        incomeSpentRepo.gets().observe(viewLifecycleOwner) {
            if (it.isEmpty())
                binding.balance.text = "₹0"
            else if (it[0].spent == null)
                binding.balance.text = "₹" + it[0].income.toString()
            else if (it[0].income == null)
                binding.balance.text = "₹0"
            else
                binding.balance.text = "₹" + (it[0].income?.minus(it[0].spent!!)).toString()
        }

        viewModel.getPieEntries().observe(viewLifecycleOwner){
            val records = it.map { originalEntry ->
                PieEntry(originalEntry.amount.toFloat(), originalEntry.category)
            }
            val piedataset = PieDataSet(records, "Report")
            piedataset.colors = ColorTemplate.COLORFUL_COLORS.toList()
            piedataset.valueTextColor = Color.WHITE
            piedataset.valueTypeface = Typeface.DEFAULT
            piedataset.valueTextSize = 14f

            val legend = binding.piChart.legend
            legend.textSize = 13f
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.isWordWrapEnabled = true

            legend.form = Legend.LegendForm.SQUARE // Set the desired form for the legend
            legend.formSize = 8f // Adjust the form size as needed

            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            // Adjust the horizontal offset
            legend.yOffset = -20f

            binding.piChart.description.isEnabled = false
            val piedata = PieData(piedataset)
            piedata.setValueTextSize(15f)
            binding.piChart.data = piedata
            binding.piChart.transparentCircleRadius = 2f
            binding.piChart.setEntryLabelTextSize(10f)
            binding.piChart.invalidate()
            binding.piChart.centerText = "Spending based on category"
            binding.piChart.setDrawSliceText(false)
        }

        return binding.root
    }
}