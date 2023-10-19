package com.example.budgetbuddy.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.budgetbuddy.R
import com.example.budgetbuddy.ui.main.MainActivity
import com.example.budgetbuddy.ui.signup.SignUpActivity
import com.example.budgetbuddy.util.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
             if(tokenManager.getToken() != null){
                 startActivity(Intent(this,MainActivity::class.java))
                 finish()
             }
            else{
                 startActivity(Intent(this,SignUpActivity::class.java))
                 finish()
             }

        },1000)

    }
}