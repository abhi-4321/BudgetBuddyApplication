package com.example.budgetbuddy.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.model.Signup
import com.example.budgetbuddy.model.userSignUpRequest
import com.example.budgetbuddy.repository.UserRepositery
import com.example.budgetbuddy.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor(private val userRepositery: UserRepositery) : ViewModel(){
    val userResponseLiveData : LiveData<NetworkResult<Signup>>
        get()= userRepositery.userResponseLiveData
    fun loginUser(userSignUpRequest: userSignUpRequest){
        viewModelScope.launch(Dispatchers.IO) {
            userRepositery.loginUser(userSignUpRequest)
        }
    }
}