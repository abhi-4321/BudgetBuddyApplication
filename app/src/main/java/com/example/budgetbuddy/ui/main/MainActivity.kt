package com.example.budgetbuddy.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.ActivityMainBinding
import com.example.budgetbuddy.ui.budget.BudgetFragment
import com.example.budgetbuddy.ui.home.HomeFragment
import com.example.budgetbuddy.ui.profile.ProfileFragment
import com.example.budgetbuddy.ui.transactions.TransactionsFragment
import com.example.budgetbuddy.util.BottomSheetAddTransaction

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        replaceFragment(HomeFragment.newInstance())

        binding.btn.setOnClickListener{
            val bottomSheetDialog = BottomSheetAddTransaction()
            bottomSheetDialog.isCancelable = false
            bottomSheetDialog.show(supportFragmentManager,"Bottom Sheet")
        }

        binding.botNavView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment.newInstance())
                }
                R.id.transactions -> {
                    replaceFragment(TransactionsFragment.newInstance())
                }
                R.id.budget -> {
                    replaceFragment(BudgetFragment.newInstance())
                }
                R.id.profile -> {
                    replaceFragment(ProfileFragment.newInstance())
                }

            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frame,fragment)
        fragmentTransaction.commit()
    }
}