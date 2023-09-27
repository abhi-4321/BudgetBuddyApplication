package com.example.budgetbuddy.ui.budget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.FragmentBudgetBinding
import com.example.budgetbuddy.databinding.FragmentHomeBinding
import com.example.budgetbuddy.databinding.FragmentProfileBinding
import com.example.budgetbuddy.repository.BudgetRepository
import com.example.budgetbuddy.ui.home.HomeViewModel
import org.jetbrains.annotations.Contract

class BudgetFragment : Fragment() {

    companion object {
        fun newInstance() = BudgetFragment()
    }

    private lateinit var viewModel: BudgetViewModel
    private var _binding: FragmentBudgetBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val budgetDao = Database.getInstance(requireContext()).budgetDao()
        val budgetRepository = BudgetRepository(budgetDao)

        sharedPreferences = requireContext().getSharedPreferences("Income", MODE_PRIVATE)
        sharedViewModel = ViewModelProvider(context as ViewModelStoreOwner)[SharedViewModel::class.java]
        viewModel = ViewModelProvider(
            this,
            BudgetViewModelFactory(budgetRepository)
        )[BudgetViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        binding.recycler.layoutManager = LinearLayoutManager(context)

        binding.income.text = (
                if ((sharedPreferences.getString("Amount","")).isNullOrEmpty())
                    "₹0.0"
                else
                    "₹"+sharedPreferences.getString("Amount","")
                )

        viewModel.getBudgets().observe(viewLifecycleOwner, Observer {
            val adapter = BudgetAdapter(it, requireContext())
            binding.recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        })

        binding.btn.setOnClickListener {
            var intent = Intent(requireContext(), CreateBudgetActivity::class.java)
            startActivity(intent)
        }


        @NonNull
        val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Handle the result here
                val data = result.data
                if (data != null) {
                    val receivedData = data.getStringExtra("keyName")
                    binding.income.text = "₹$receivedData"
                }
            } else {
                // Handle the case where the child activity didn't succeed
                binding.income.text = (
                        if ((sharedPreferences.getString("Amount","")).isNullOrEmpty())
                            "₹0.0"
                        else
                            "₹"+sharedPreferences.getString("Amount","")
                        )
            }
        }

        binding.floatingActionBtn.setOnClickListener {
            var intent = Intent(requireContext(), CreateBudgetActivity::class.java)
            activityResultLauncher.launch(intent)
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
        _binding = null
    }

}