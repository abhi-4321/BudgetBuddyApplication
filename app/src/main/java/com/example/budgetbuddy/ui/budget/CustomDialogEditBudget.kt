package com.example.budgetbuddy.ui.budget

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.EditBudgetDialogBinding
import com.example.budgetbuddy.repository.BudgetRepository
import com.example.budgetbuddy.ui.transactions.Drawables
import kotlinx.coroutines.*

class CustomDialogEditBudget (private val image : Int, private val category : String, context : Context): Dialog(context),View.OnClickListener{

    private lateinit var binding : EditBudgetDialogBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = EditBudgetDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setButton.setOnClickListener(this)
        binding.cancelButton.setOnClickListener(this)

        binding.image.setImageResource(Drawables.asRes(image))

        binding.category.text = category
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
                    val budgetDao = Database.getInstance(context).budgetDao()
                    val budgetRepository = BudgetRepository(budgetDao)

                    GlobalScope.launch(Dispatchers.IO) {
                        budgetRepository.update(Budget(image,category,binding.amount.text.toString().toInt(),0))
                    }
                    dismiss()
                }
            }
            R.id.cancel -> dismiss()
            else -> {}
        }

        dismiss()
    }
}