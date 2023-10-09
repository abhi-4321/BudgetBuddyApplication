package com.example.budgetbuddy.ui.budget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.database.IncomeSpentDao
import com.example.budgetbuddy.databinding.FragmentBudgetBinding
import com.example.budgetbuddy.repository.BudgetRepository
import com.example.budgetbuddy.repository.IncomeSpentRepo
import com.example.budgetbuddy.repository.TransactionRepository
import com.example.budgetbuddy.util.addTransaction.AddTransactionViewModel
import com.example.budgetbuddy.util.addTransaction.AddTransactionViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Types.NULL
import java.text.SimpleDateFormat
import java.util.*

class BudgetFragment : Fragment() {

    companion object {
        fun newInstance() = BudgetFragment()
    }

    private lateinit var viewModel: BudgetViewModel
    private var _binding: FragmentBudgetBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var incomeSpentRepo : IncomeSpentRepo
    private lateinit var addTransactionViewModel: AddTransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val budgetDao = Database.getInstance(requireContext()).budgetDao()
        val budgetRepository = BudgetRepository(budgetDao)

        val incomeSpentDao = Database.getInstance(requireContext()).incomeSpentDao()
        incomeSpentRepo = IncomeSpentRepo(incomeSpentDao)

        sharedViewModel = ViewModelProvider(context as ViewModelStoreOwner)[SharedViewModel::class.java]
        viewModel = ViewModelProvider(
            this,
            BudgetViewModelFactory(budgetRepository)
        )[BudgetViewModel::class.java]
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        binding.recycler.layoutManager = LinearLayoutManager(context)

        incomeSpentRepo.gets().observe(viewLifecycleOwner){
            val income : Int = if (it.isEmpty()) 0 else if (it[0].income == null) 0 else it[0].income!!
            binding.income.text = "₹$income"

            val spent : Int = if (it.isEmpty()) 0 else if (it[0].spent == null) 0 else it[0].spent!!
            binding.spend.text = "₹$spent"

            val saved = (income-spent)
            binding.saved.text = "₹$saved"

            val per = if(income ==0){
                0f
            } else {
                ((saved.toFloat())/(income.toFloat()))*100f
            }

            binding.progress.text = "$per%"
            //circular progress bar
            binding.circularProgressBar.apply {
                progressMax = 100f
                setProgressWithAnimation(per,1500)
                roundBorder = true
                Log.d("Tool","hiu")
            }
        }

        viewModel.getBudgets().observe(viewLifecycleOwner, Observer {
            val adapter = BudgetAdapter(it, requireContext())
            binding.recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        })

        val transactionDao = Database.getInstance(requireContext()).transactionDao()
        val transactionRepository = TransactionRepository(transactionDao)
        val currentMonth = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(System.currentTimeMillis()).toString()

//        transactionRepository.getTotalSpent(currentMonth).observe(viewLifecycleOwner){

//        }


        binding.btn.setOnClickListener {
            val intent = Intent(requireContext(), CreateBudgetActivity::class.java)
            startActivity(intent)
        }

        binding.floatingActionBtn.setOnClickListener {
            val intent = Intent(requireContext(), CreateBudgetActivity::class.java)
            startActivity(intent)
        }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}