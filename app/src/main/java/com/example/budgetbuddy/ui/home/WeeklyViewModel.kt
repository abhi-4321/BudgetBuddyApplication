package com.example.budgetbuddy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.repository.HomeRepository

class WeeklyViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    fun getWeeklyData(): LiveData<List<BarEntries>> {
        return homeRepository.getWeeklyData()
    }
}