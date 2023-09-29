package com.example.budgetbuddy.ui.budget

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.EditBudgetDialogBinding
import com.example.budgetbuddy.repository.BudgetRepository
import kotlinx.coroutines.*

class CustomDialogSetBudget2 (context : Context ,private val activity : Activity,private val category: String?): Dialog(context),View.OnClickListener{

    private lateinit var binding : EditBudgetDialogBinding
    private lateinit var viewModel : SharedViewModel

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = EditBudgetDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(activity as ViewModelStoreOwner)[SharedViewModel::class.java]

        binding.setButton.setOnClickListener(this)
        binding.cancelButton.setOnClickListener(this)

        binding.image.setImageResource(R.drawable.category)
        binding.category.text = category
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.setButton -> {
                if (binding.amount.text.isNullOrEmpty())
                {
                    binding.amount.error = "Value cannot be blank"
                }
                else
                {
                    val sharedPreferences = context.getSharedPreferences("Amount",MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString(category,binding.amount.text.toString())
                    editor.apply()

                    viewModel.setAmount(binding.amount.text.toString())
                    viewModel.addBudgets(binding.category.text.toString(),binding.amount.text.toString())
                    dismiss()
                }
            }
            R.id.cancel -> dismiss()
            else -> {}
        }

        dismiss()
    }
}