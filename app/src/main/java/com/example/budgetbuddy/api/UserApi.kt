package com.example.budgetbuddy.api

import com.example.budgetbuddy.model.Signup
import com.example.budgetbuddy.model.userSignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/budget/signup")
   suspend fun signup(@Body userRequest : userSignUpRequest) : Response<Signup>
    @POST("/budget/signin")
    suspend fun signin(@Body userRequest : userSignUpRequest) : Response<Signup>

}