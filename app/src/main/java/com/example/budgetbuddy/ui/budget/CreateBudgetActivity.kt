package com.example.budgetbuddy.ui.budget

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.inflate
import android.widget.Button
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.ActivityCreateBudgetBinding

class CreateBudgetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBudgetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.set_budget_dilog)

        binding.foodBtn.setOnClickListener {
            dialog.show()
        }
        val cancelBtn = dialog.findViewById<Button>(R.id.cancelButton)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }


    }
}