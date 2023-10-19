package com.example.budgetbuddy.util.addTransaction

data class ProcessedData(
    val inflow: Int,   // Total inflow amount
    val outflow: Int,  // Total outflow amount
    val total: Int,    // Difference between inflow and outflow
    val textColor: Int // Color for the total (based on its value)
)