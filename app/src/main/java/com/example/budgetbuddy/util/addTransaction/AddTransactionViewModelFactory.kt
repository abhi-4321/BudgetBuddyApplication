package com.example.budgetbuddy.util.addTransaction

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddTransactionViewModelFactory(val context : Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddTransactionViewModel(context) as T
    }

}