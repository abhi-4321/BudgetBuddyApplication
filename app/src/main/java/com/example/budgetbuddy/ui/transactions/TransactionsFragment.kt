package com.example.budgetbuddy.ui.transactions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.FragmentBudgetBinding
import com.example.budgetbuddy.databinding.FragmentProfileBinding
import com.example.budgetbuddy.databinding.FragmentTransactionsBinding
import com.example.budgetbuddy.ui.profile.ProfileViewModel
import java.time.ZoneOffset

class TransactionsFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionsFragment()
    }

    private lateinit var viewModel: TransactionsViewModel
    private var _binding : FragmentTransactionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: TransactionAdapter
    private lateinit var itemSectionDecoration: TransactioSectionDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[TransactionsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        initList()
        relode()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    private fun initList() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            relode()
        }
        layoutManager = LinearLayoutManager(requireContext())
        adapter = TransactionAdapter {
            loadMore()
        }
itemSectionDecoration  = TransactioSectionDecoration(requireContext()){
adapter.list
}
binding.recyclerView.addItemDecoration(itemSectionDecoration)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter =adapter
    }
    private fun relode() {
val list = dummyData(0,20)
        binding.recyclerView.post {
            adapter.reload(list)
            Log.d("list", list.toString())
        }
    }
    private fun loadMore() {
        val list = dummyData(adapter.itemCount,20)
        binding.recyclerView.post {
            adapter.loadMore(list)
        }
    }

    private fun dummyData(offset: Int, limit : Int): MutableList<TransactionModel>{
        val list = mutableListOf<TransactionModel>()

        var itemModel:TransactionModel
        for (i in offset until offset + limit){
            itemModel = when (i) {
             in 0..15->{
                 TransactionModel("title$i",getDummyDataString("01"))
             }
                in 16..30->{
                    TransactionModel("title$i",getDummyDataString("02"))
                }
                in 31..45->{
                    TransactionModel("title$i",getDummyDataString("03"))
                }
                else->{
                    TransactionModel("title$i",getDummyDataString("04"))
                }
            }
            list.add(itemModel)
        }
        return list
    }
    private fun getDummyDataString(day: String):String{
        return "2021-10-$day"
    }

}