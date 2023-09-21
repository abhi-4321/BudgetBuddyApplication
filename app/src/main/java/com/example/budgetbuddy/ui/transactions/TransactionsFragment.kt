package com.example.budgetbuddy.ui.transactions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.FragmentTransactionsBinding
import com.example.budgetbuddy.repository.TransactionRepository
import com.example.budgetbuddy.util.addTransaction.AddTransactionViewModel
import com.example.budgetbuddy.util.addTransaction.AddTransactionViewModelFactory
import com.example.budgetbuddy.util.addTransaction.Transaction

class TransactionsFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionsFragment()
    }

    private lateinit var viewModel: AddTransactionViewModel
    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Fragment view is not available yet")
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: TransactionAdapter
    private lateinit var itemSectionDecoration: TransactionSectionDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transactionDao = Database.getInstance(requireContext()).transactionDao()
        val transactionRepository = TransactionRepository(transactionDao)
        viewModel = ViewModelProvider(requireActivity(),AddTransactionViewModelFactory(transactionRepository))[AddTransactionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        initList()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initList() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            Handler.createAsync(Looper.getMainLooper()).postDelayed(Runnable {
                binding.swipeRefreshLayout.isRefreshing = false
                reload()
            },1500)

        }
        layoutManager = LinearLayoutManager(requireContext())
        adapter = TransactionAdapter{
//            loadMore()
        }
        itemSectionDecoration = TransactionSectionDecoration(requireContext()) {
            adapter.list
        }
        binding.recyclerView.addItemDecoration(itemSectionDecoration)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun reload() {

        var list : LiveData<ArrayList<Transaction>> = dummyData(0)
//        binding.recyclerView.post {
//            adapter.reload(list)
//            Log.d("list", list.toString())
//        }
        lateinit var arraylist : ArrayList<Transaction>
        list.observe(requireActivity(), Observer {
            arraylist = it as ArrayList<Transaction>
            binding.recyclerView.post {
                adapter.reload(arraylist)
                adapter.notifyDataSetChanged()
            }
        })

    }

    private fun loadMore() {

        val list = dummyData(adapter.itemCount)
        lateinit var arraylist : ArrayList<Transaction>
        list.observe(requireActivity(), Observer {
            arraylist=it as ArrayList<Transaction>
            binding.recyclerView.post {
                adapter.loadMore(arraylist)
            }
        })
//        binding.recyclerView.post {
//            adapter.loadMore(list)
//        }
    }

    private fun dummyData(offset: Int, limit : Int = 20): LiveData<ArrayList<Transaction>> {
        val list = MutableLiveData<ArrayList<Transaction>>()
        for (i in offset until offset + limit) {
            when (i) {
                in 0..15 -> {
                    Toast.makeText(context,"1 2 15",Toast.LENGTH_SHORT).show()
                    return viewModel.getTransactions() as LiveData<ArrayList<Transaction>>

                }
                in 16..30 -> {
                    Toast.makeText(context,"16 2 30",Toast.LENGTH_SHORT).show()
                    return viewModel.getTransactions() as LiveData<ArrayList<Transaction>>
                }
                in 31..45 -> {
                    Toast.makeText(context,"31 2 45",Toast.LENGTH_SHORT).show()
                    return viewModel.getTransactions() as LiveData<ArrayList<Transaction>>
                }
                else -> {
                    Toast.makeText(context,"46 2 60",Toast.LENGTH_SHORT).show()
                    return viewModel.getTransactions() as LiveData<ArrayList<Transaction>>
                }
            }
        }
        return list
    }

//    private fun getDummyDataString(day: String): String {
//        return "2021-10-$day"
//    }

}