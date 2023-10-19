package com.example.budgetbuddy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.repository.HomeRepository

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    fun getPieEntries() : LiveData<List<PieEntries>> {
        return homeRepository.getPieEntries()
    }

    fun getBarEntries() : LiveData<ArrayList<BarEntries>> {
        return homeRepository.getMonthlyData() as LiveData<ArrayList<BarEntries>>
    }
}