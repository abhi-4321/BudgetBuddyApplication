package com.example.budgetbuddy.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentBudgetBinding
import com.example.budgetbuddy.databinding.FragmentHomeBinding
import com.example.budgetbuddy.databinding.FragmentProfileBinding
import com.example.budgetbuddy.ui.home.HomeViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    companion object {
        fun newInstance() = ProfileFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        return binding.root
    }



}