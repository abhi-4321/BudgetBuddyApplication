package com.example.budgetbuddy.ui.budget

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.inflate
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.ActivityCreateBudgetBinding
import com.example.budgetbuddy.repository.BudgetRepository

class CreateBudgetActivity : AppCompatActivity() , View.OnClickListener{
    private lateinit var binding: ActivityCreateBudgetBinding
    private lateinit var budgetViewModel: BudgetViewModel
    private lateinit var sharedViewModel : SharedViewModel
    private lateinit var dialog : CustomDialogSetBudget
    private var icon : Int = 0
    private var category : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val budgetDao = Database.getInstance(this).budgetDao()
        val budgetRepository = BudgetRepository(budgetDao)
        budgetViewModel = ViewModelProvider(this,BudgetViewModelFactory(budgetRepository))[BudgetViewModel::class.java]

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        dialog = CustomDialogSetBudget(this,this)

        binding.foodBtn.setOnClickListener(this)

        binding.recycler.layoutManager = LinearLayoutManager(this)
    }

    inner class BudgetViewModelFactory(private val budgetRepository: BudgetRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BudgetViewModel(budgetRepository) as T
        }
    }

    override fun onClick(p0: View?) {
        dialog.show()

        when(p0?.id) {
            R.id.food -> {
                category = "Food & Beverages"
                icon=0
            }
        }

        sharedViewModel.setCategory(category!!)
        sharedViewModel.setIcon(icon)
    }
}