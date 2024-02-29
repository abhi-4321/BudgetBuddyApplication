package com.example.budgetbuddy.util

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Patterns

class ValidateCredentials {

    fun validate(userName: String, emailAddress : String, password : String, isLogin : Boolean) : Pair<Boolean, String>{
        var result = Pair(true,"")
        if (!isLogin && TextUtils.isEmpty(userName) || TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password)){
            result = Pair(false, "Please provide the credentials")
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            result = Pair(false, "Please provide valid email")
        }
        else if(password.length <= 5){
            result = Pair(false, "password length should be greater than 5")
        }
        return result
    }
}