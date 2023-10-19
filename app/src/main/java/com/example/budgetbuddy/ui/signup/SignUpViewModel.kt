package com.example.budgetbuddy.ui.signup

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
class SignUpViewModel @Inject constructor(private val userRepositery: UserRepositery):ViewModel(){

    val userResponseLiveData : LiveData<NetworkResult<Signup>>
    get()= userRepositery.userResponseLiveData
 fun registerUser(userSignUpRequest: userSignUpRequest){
     viewModelScope.launch(Dispatchers.IO) {
         userRepositery.registerUser(userSignUpRequest)
     }
 }
}