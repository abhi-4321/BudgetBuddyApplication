package com.example.budgetbuddy.ui.budget

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.SetBudgetDilogBinding
import com.example.budgetbuddy.repository.BudgetRepository
import com.example.budgetbuddy.repository.CategoryRepository
import com.example.budgetbuddy.ui.transactions.Drawables
import com.example.budgetbuddy.util.category.Category
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.function.IntBinaryOperator

class CustomDialogSetBudget(context: Context,val activity : Activity) : Dialog(context), View.OnClickListener {

    lateinit var binding : SetBudgetDilogBinding
    lateinit var viewModel: SharedViewModel
    private val budgetDao = Database.getInstance(context).budgetDao()
    val budgetRepository = BudgetRepository(budgetDao)

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        viewModel = ViewModelProvider(activity)[SharedViewModel::class.java]

        binding = SetBudgetDilogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getIcon().observe(activity, Observer {
            binding.image.setImageResource(Drawables.asRes(it))
        })

        viewModel.getCategory().observe(activity, Observer {
            binding.category.text = it
        })

        binding.category.text = TODO()
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.setButton -> {
                if (binding.amount.text.isNullOrEmpty())
                {
                    binding.amount.error = "Value cannot be blank"
                }
                else
                {
                    GlobalScope.launch(Dispatchers.IO) {
                        budgetRepository.insert(Budget(0,1,binding.category.text.toString(),
                            binding.amount.text.toString().toInt(),0))
                    }
                }
            }
            R.id.cancel -> dismiss()
            else -> {}
        }

        dismiss()
    }
}