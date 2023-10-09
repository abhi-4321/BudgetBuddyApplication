package com.example.budgetbuddy.ui.transactions

import com.example.budgetbuddy.util.addTransaction.Transaction

data class Header(
    val section : String,
    val list : ArrayList<Transaction>
)