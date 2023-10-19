package com.example.budgetbuddy.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.ActivityMainBinding
import com.example.budgetbuddy.ui.budget.BudgetFragment
import com.example.budgetbuddy.ui.home.HomeFragment
import com.example.budgetbuddy.ui.profile.ProfileFragment
import com.example.budgetbuddy.ui.transactions.TransactionsFragment
import com.example.budgetbuddy.util.addTransaction.BottomSheetAddTransaction


class MainActivity : AppCompatActivity(), BottomSheetAddTransaction.BottomSheetDismissListener {

    private lateinit var binding: ActivityMainBinding
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        replaceFragment(HomeFragment.newInstance())

        binding.btn.setOnClickListener {
            val bottomSheetDialog = BottomSheetAddTransaction(this)
            bottomSheetDialog.isCancelable = false
            bottomSheetDialog.show(supportFragmentManager, "Bottom Sheet")
        }

        binding.botNavView.setOnItemSelectedListener {
            when (it.itemId) {
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

        fragmentTransaction.replace(R.id.frame, fragment)
        fragmentTransaction.commit()

        currentFragment = fragment
    }

    override fun onBottomSheetDismiss() {
        if (currentFragment is TransactionsFragment)
            if ((currentFragment as TransactionsFragment).isAdded && (currentFragment as TransactionsFragment).view != null)
                (currentFragment as TransactionsFragment).updateRecyclerView()
    }
}