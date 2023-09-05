package com.example.budgetbuddy.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun insert(user : User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insert(user)
        }
    }

    fun delete(user : User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.delete(user)
        }
    }

    fun update(user : User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.update(user)
        }
    }

    fun getUsers() : LiveData<List<User>> {
        return userRepository.getUsers()
    }

}