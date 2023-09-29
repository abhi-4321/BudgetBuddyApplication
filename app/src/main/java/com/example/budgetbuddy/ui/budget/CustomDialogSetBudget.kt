package com.example.budgetbuddy.ui.budget

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.SetBudgetDilogBinding
import com.example.budgetbuddy.repository.BudgetRepository
import com.example.budgetbuddy.ui.transactions.Drawables
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomDialogSetBudget(context: Context, private val activity : Activity) : Dialog(context), View.OnClickListener {

    private lateinit var binding : SetBudgetDilogBinding
    private lateinit var viewModel: SharedViewModel

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        viewModel = ViewModelProvider(activity as ViewModelStoreOwner)[SharedViewModel::class.java]

        binding = SetBudgetDilogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setButton.setOnClickListener(this)
        binding.cancelButton.setOnClickListener(this)

        viewModel.getIcon().observe(activity as LifecycleOwner) {
            binding.image.setImageResource(Drawables.asRes(it))
        }

        viewModel.getCategory().observe(activity as LifecycleOwner) {
            binding.category.text = it
        }
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