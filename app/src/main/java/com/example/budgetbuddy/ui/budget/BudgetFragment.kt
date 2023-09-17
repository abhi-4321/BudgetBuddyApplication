package com.example.budgetbuddy.ui.budget

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentBudgetBinding
import com.example.budgetbuddy.databinding.FragmentHomeBinding
import com.example.budgetbuddy.databinding.FragmentProfileBinding
import com.example.budgetbuddy.ui.home.HomeViewModel

class BudgetFragment : Fragment() {

    companion object {
        fun newInstance() = BudgetFragment()
    }

    private lateinit var viewModel: BudgetViewModel
    private var _binding : FragmentBudgetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[BudgetViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetBinding.inflate(inflater,container, false)


        binding.btn.setOnClickListener {
           var intent = Intent(requireContext(), CreateBudgetActivity::class.java)
            startActivity(intent)
        }

       binding.floatingActionBtn.setOnClickListener {
           var intent = Intent(requireContext(), CreateBudgetActivity::class.java)
           startActivity(intent)
       }

        //circular progress bar
        binding.circularProgressBar.apply {
            progressMax = 100f
            setProgressWithAnimation(50f, 1000)
            roundBorder = true

        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}