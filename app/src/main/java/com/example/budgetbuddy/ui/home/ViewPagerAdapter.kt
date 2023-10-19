package com.example.budgetbuddy.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager : FragmentManager, lifeCycle : Lifecycle) :
    FragmentStateAdapter(fragmentManager,lifeCycle){

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            MonthlyFragment.newInstance()
        else
            WeeklyFragment.newInstance()
    }

    override fun getItemCount(): Int {
        return 2
    }
}