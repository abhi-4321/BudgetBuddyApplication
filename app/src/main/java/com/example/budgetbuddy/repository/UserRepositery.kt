package com.example.budgetbuddy.repository

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.budgetbuddy.api.UserApi
import com.example.budgetbuddy.model.Signup
import com.example.budgetbuddy.model.userSignUpRequest
import com.example.budgetbuddy.util.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepositery @Inject constructor(private val userApi: UserApi) {

    private  val _userResponseLiveData = MutableLiveData<NetworkResult<Signup>>()
    val userResponseLiveData : LiveData<NetworkResult<Signup>>
        get() = _userResponseLiveData

    @SuppressLint("SuspiciousIndentation")
    suspend fun registerUser(userRequest:userSignUpRequest){
        _userResponseLiveData.postValue(NetworkResult.Loading())
     val response = userApi.signup(userRequest)
        handleResponse(response)
    }

    suspend fun loginUser(userRequest: userSignUpRequest){
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userApi.signin(userRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<Signup>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))

        } else if (response.errorBody() != null) {
            val error0bj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(error0bj.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong!!"))
        }
    }



}