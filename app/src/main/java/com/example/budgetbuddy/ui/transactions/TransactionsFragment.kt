package com.example.budgetbuddy.ui.transactions

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.FragmentTransactionsBinding
import com.example.budgetbuddy.repository.IncomeSpentRepo
import com.example.budgetbuddy.repository.TransactionRepository
import com.example.budgetbuddy.util.addTransaction.AddTransactionViewModel
import com.example.budgetbuddy.util.addTransaction.AddTransactionViewModelFactory
import com.example.budgetbuddy.util.addTransaction.Transaction
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class TransactionsFragment : Fragment(), TransactionsAdapter.OnClick,
    TransactionDetailBottomSheet.CallBack {

    companion object {
        fun newInstance() = TransactionsFragment()
    }

    private lateinit var viewModel: AddTransactionViewModel
    private lateinit var incomeSpentRepo: IncomeSpentRepo
    private lateinit var binding: FragmentTransactionsBinding

    private lateinit var observer: Observer<List<Flow>>

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer = Observer {
            Log.d("TAG", it.toString())

            when (it.size) {
                0 -> {
                    binding.inflow.text = "₹0"
                    binding.outflow.text = "₹0"
                    binding.total.text = "₹0"
                }
                1 -> {
                    if (it[0].debit == "Credit") {
                        binding.inflow.text = "+₹" + it[0].total.toString()
                        binding.outflow.text = "+₹0"
                    }
                    else {
                        binding.outflow.text = "-₹" + it[0].total.toString()
                        binding.inflow.text = "₹0"
                    }
                    binding.total.text = "₹"+it[0].total.toString()
                }
                2 -> {
                    var inflow = 0
                    var outflow = 0
                    for (flow in it) {
                        when (flow.debit) {
                            "Debit" -> {
                                binding.outflow.text = "-${flow.total}"
                                outflow = flow.total
                            }
                            "Credit" -> {
                                binding.inflow.text = "+${flow.total}"
                                inflow = flow.total
                            }
                        }
                    }
                    if ((inflow - outflow) >= 0) {
                        binding.total.setTextColor(Color.parseColor("#4CAF50"))
                    } else {
                        binding.total.setTextColor(Color.parseColor("#F44336"))
                    }
                    binding.total.text = (inflow - outflow).toString()
                }
            }
        }
        viewModel.getInOutFlow().observeForever(observer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transactionDao = Database.getInstance(requireContext()).transactionDao()
        val transactionRepository = TransactionRepository(transactionDao)

        binding = FragmentTransactionsBinding.inflate(layoutInflater)

        val incomeSpentDao = Database.getInstance(requireContext()).incomeSpentDao()
        incomeSpentRepo = IncomeSpentRepo(incomeSpentDao)

        viewModel = ViewModelProvider(
            requireActivity(),
            AddTransactionViewModelFactory(transactionRepository)
        )[AddTransactionViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        incomeSpentRepo.gets().observe(viewLifecycleOwner) {
            if (it.isEmpty())
                binding.balance.text = "₹0"
            else if (it[0].spent == null)
                binding.balance.text = "₹" + it[0].income.toString()
            else if (it[0].income == null)
                binding.balance.text = "₹0"
            else
                binding.balance.text = "₹" + (it[0].income?.minus(it[0].spent!!)).toString()
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addOnScrollListener(object : OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    val firstPos = layoutManager.findFirstCompletelyVisibleItemPosition();
                    if (firstPos > 0) {
                        binding.swipeRefreshLayout.isEnabled = false;
                    } else {
                        binding.swipeRefreshLayout.isEnabled = true;
                        if (binding.recyclerView.scrollState == 1)
                            if (binding.swipeRefreshLayout.isRefreshing)
                                binding.recyclerView.stopScroll();
                    }

                } catch (e: Exception) {
                    Log.e("TAG", "Scroll Error : " + e.localizedMessage); }
            }
        })

        binding.recyclerView.layoutManager = layoutManager
        initList()

        binding.swipeRefreshLayout.setOnRefreshListener {
            initList()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.getInOutFlow().removeObserver(observer)
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
                val adapter = SectionAdapter(arrayList, this@TransactionsFragment)
                binding.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                binding.swipeRefreshLayout.isRefreshing = false
            })
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

    override fun onClick(id: Int) {
        val bottomSheet = TransactionDetailBottomSheet(id, this)
        bottomSheet.isCancelable = false
        bottomSheet.show(childFragmentManager, "T")
    }

    override fun completed() {
        initList()
    }
}