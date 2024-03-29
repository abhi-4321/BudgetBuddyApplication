package com.example.budgetbuddy.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.example.budgetbuddy.util.Constants.PREF_TOKEN_FILE
import com.example.budgetbuddy.util.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor (@ApplicationContext context : Context) {

    private var pref = context.getSharedPreferences(PREF_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token : String){
        Log.d("Authtoken", pref.getString(USER_TOKEN ,null).toString())

        val editor = pref.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        Log.d("Authtoken", pref.getString(USER_TOKEN ,null).toString())
        return pref.getString(USER_TOKEN ,null)
    }

}