package com.example.budgetbuddy.ui.transactions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.FragmentTransactionsBinding
import com.example.budgetbuddy.repository.TransactionRepository
import com.example.budgetbuddy.util.addTransaction.AddTransactionViewModel
import com.example.budgetbuddy.util.addTransaction.AddTransactionViewModelFactory
import com.example.budgetbuddy.util.addTransaction.Transaction
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class TransactionsFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionsFragment()
    }

    private lateinit var viewModel: AddTransactionViewModel
    private var _binding: FragmentTransactionsBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Fragment view is not available yet")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transactionDao = Database.getInstance(requireContext()).transactionDao()
        val transactionRepository = TransactionRepository(transactionDao)
        viewModel = ViewModelProvider(
            requireActivity(),
            AddTransactionViewModelFactory(transactionRepository)
        )[AddTransactionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        initList()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initList() {

        val arrayList = ArrayList<Header>()
        val list = ArrayList<ArrayList<Transaction>>()

        GlobalScope.launch(Dispatchers.IO) {

            val distinctMonths = viewModel.distinctMonths().await()
            var transactionSection = ArrayList<Transaction>()
            var i = 0

            for (month in distinctMonths) {
                transactionSection = viewModel.getTransactionsByMonth(month).await()
                if (!list.contains(transactionSection)) {
                    list.add(transactionSection)
                    arrayList.add(Header(month, list[i]))
                    i++
                }
            }
            requireActivity().runOnUiThread(Runnable {
                binding.recyclerView.adapter = SectionAdapter(arrayList)
            })
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            initList()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun <T> LiveData<T>.await(): T = withContext(Dispatchers.Main) {
        suspendCancellableCoroutine { cont ->
            val observer = object : Observer<T> {
                override fun onChanged(value: T) {
                    cont.resume(value) {}
                    removeObserver(this)
                }
            }
            observeForever(observer)
            cont.invokeOnCancellation {
                removeObserver(observer) // Remove the observer in case of cancellation
            }
        }
    }

    fun updateRecyclerView() {
        initList()
    }
}