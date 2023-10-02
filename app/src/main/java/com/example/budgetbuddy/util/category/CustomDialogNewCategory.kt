package com.example.budgetbuddy.util.category

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.repository.CategoryRepository
import com.example.budgetbuddy.ui.budget.SharedViewModel
import com.example.budgetbuddy.util.addTransaction.BottomSheetAddTransaction
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomDialogNewCategory(context : Context,private val viewModel : CategoryViewModel) : Dialog(context), View.OnClickListener {

    var add: MaterialButton? = null
    var cancel:MaterialButton? = null
    var editText : EditText?= null

    val catDao = Database.getInstance(context).categoryDao()
    val categoryRepository = CategoryRepository(catDao)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.new_category)
        add = findViewById<View>(R.id.add) as MaterialButton
        editText = findViewById<View>(R.id.category) as EditText
        cancel = findViewById<View>(R.id.cancel) as MaterialButton
        add!!.setOnClickListener(this)
        cancel!!.setOnClickListener(this)


    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.add -> {
                if (editText?.text.isNullOrEmpty())
                {
                    editText?.error = "Name cannot be blank"
                }
                else
                {
                    viewModel.insert(Category(0,editText?.text.toString()))
                }


            }
            R.id.cancel -> dismiss()
            else -> {}
        }

        dismiss()
    }
}
