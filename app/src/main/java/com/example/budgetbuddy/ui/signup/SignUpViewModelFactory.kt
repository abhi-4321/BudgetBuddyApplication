package com.example.budgetbuddy.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.budgetbuddy.repository.UserRepositery
import com.example.budgetbuddy.ui.budget.BudgetViewModel

class SignUpViewModelFactory(private val userRepositery: UserRepositery): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpViewModel(userRepositery) as T
    }
}