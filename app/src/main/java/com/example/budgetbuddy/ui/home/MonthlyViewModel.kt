package com.example.budgetbuddy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.repository.HomeRepository

class MonthlyViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    fun getMonthlyData() : LiveData<List<BarEntries>>{
        return homeRepository.getMonthlyData()
    }
}