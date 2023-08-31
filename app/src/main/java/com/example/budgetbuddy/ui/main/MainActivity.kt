package com.example.budgetbuddy.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.budgetbuddy.R
import com.example.budgetbuddy.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }
    }
}