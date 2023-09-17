package com.example.budgetbuddy.util.addTransaction

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.AddTransactionBottomSheetBinding
import com.example.budgetbuddy.repository.TransactionRepository
import com.example.budgetbuddy.ui.transactions.Drawables
import com.example.budgetbuddy.util.category.BottomSheetCategory
import com.example.budgetbuddy.util.date.DatePicker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.Integer.parseInt
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class BottomSheetAddTransaction : BottomSheetDialogFragment(), DatePickerDialog.OnDateSetListener,CompoundButton.OnCheckedChangeListener {
    lateinit var behavior: BottomSheetBehavior<FrameLayout>
    lateinit var binding: AddTransactionBottomSheetBinding
    private lateinit var viewModel: SharedTransactionViewModel
    lateinit var addTransactionViewModel: AddTransactionViewModel
    private var selectedModeTextView : TextView? = null
    private var selectedTextView : TextView? = null
    private var it : Int = 0
    private fun getWindowHeight() = resources.displayMetrics.heightPixels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SharedTransactionViewModel::class.java]

        val transactionDao = Database.getInstance(requireContext()).transactionDao()
        val transactionRepository = TransactionRepository(transactionDao)

        addTransactionViewModel = ViewModelProvider(
            requireActivity(),
            AddTransactionViewModelFactory(transactionRepository)
        )[AddTransactionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AddTransactionBottomSheetBinding.inflate(layoutInflater)
        binding.close.setOnClickListener {
            dismiss()
        }

        binding.date.text = SimpleDateFormat("dd MMMM yyyy").format(System.currentTimeMillis()).toString()

        viewModel.getCategory().observe(requireActivity(), Observer {
            binding.category.setText(it)
        })
        viewModel.getIcon().observe(requireActivity(), Observer {
            this.it=it
            val icon : Int? = Drawables.asRes(it)
            binding.icon.setImageResource(icon!!)
        })

        binding.category.isFocusable = false
        binding.category.setOnClickListener {
            val bottomSheetDialog = BottomSheetCategory()
            bottomSheetDialog.isCancelable = false
            bottomSheetDialog.show(
                requireActivity().supportFragmentManager,
                "Bottom Sheet Category"
            )
        }

        val cal = Calendar.getInstance()
        val currentHour = parseInt(cal.get(Calendar.HOUR).toString())
        val currentMin = parseInt(cal.get(Calendar.MINUTE).toString())

        binding.time.text = SimpleDateFormat("hh : mm a").format(System.currentTimeMillis())

        val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute -> // logic to properly handle
                // the picked timings by user
                val formattedTime: String = when {
                    hourOfDay == 0 -> {
                        if (minute < 10) {
                            "${hourOfDay + 12} : 0${minute} am"
                        } else {
                            "${hourOfDay + 12} : ${minute} am"
                        }
                    }
                    hourOfDay in 1..9 -> {
                        if (minute < 10) {
                            "0${hourOfDay} : 0${minute} pm"
                        } else {
                            "0${hourOfDay} : ${minute} pm"
                        }
                    }
                    hourOfDay in 10..11 -> {
                        if (minute < 10) {
                            "${hourOfDay} : 0${minute} pm"
                        } else {
                            "${hourOfDay} : ${minute} pm"
                        }
                    }
                    hourOfDay == 12 -> {
                        if (minute < 10) {
                            "${hourOfDay} : 0${minute} pm"
                        } else {
                            "${hourOfDay} : ${minute} pm"
                        }
                    }
                    else -> {
                        if (minute < 10) {
                            "0${hourOfDay} : 0${minute} am"
                        } else {
                            "${hourOfDay} : ${minute} am"
                        }
                    }
                }

                binding.time.text = formattedTime
            }

        binding.actionImage.setOnClickListener {
            loadImageFromGallery()
        }

        binding.time.setOnClickListener {
            val timePicker = TimePickerDialog(
                requireContext(),
                timePickerDialogListener,
                currentHour,
                currentMin,
                false
            )
            timePicker.show()
        }

        binding.cashRdo.setOnCheckedChangeListener(this)
        binding.onlineRdo.setOnCheckedChangeListener(this)
        binding.creditRdo.setOnCheckedChangeListener(this)
        binding.debitRdo.setOnCheckedChangeListener(this)



        binding.date.setOnClickListener {
            val datePicker = DatePicker()
            datePicker.show(childFragmentManager, "PICK DATE")
        }

        binding.done.setOnClickListener {
            done()
        }

        return binding.root
    }

    private fun done() {

        if (selectedTextView == null || selectedModeTextView == null)
        {
            Toast.makeText(context,"Please Select All Options",Toast.LENGTH_SHORT).show()
            return
        }

        addTransactionViewModel.insert(
            Transaction(
                0,
                it,
                binding.amount.text.toString(),
                binding.remark.text.toString(),
                binding.text.text.toString(),
                selectedModeTextView?.text.toString(),
                binding.category.text.toString(),
                selectedTextView?.text.toString(),
                binding.date.text.toString(),
                binding.time.text.toString()
            )
        )
        dismiss()
    }

    override fun onStart() {
        super.onStart()

        val view: FrameLayout? =
            dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        view?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        view?.layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT

        behavior = BottomSheetBehavior.from(view!!)
        behavior.peekHeight = getWindowHeight()

        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
//                    dismiss()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })
    }

    @SuppressLint("SimpleDateFormat")
    override fun onDateSet(p0: android.widget.DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val mCalendar: Calendar = Calendar.getInstance()
        mCalendar.set(Calendar.YEAR, year)
        mCalendar.set(Calendar.MONTH, month)
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd MMMM yyyy")
        val selectedDate: String =
            dateFormat.format(mCalendar.time)
        binding.date.text = selectedDate
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show()
                val selectedImageURI = data?.data
                context?.contentResolver?.takePersistableUriPermission(
                    selectedImageURI!!,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                binding.text.text = selectedImageURI.toString()
            }
        }
    }

    private fun loadImageFromGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).also {
            it.addCategory(Intent.CATEGORY_OPENABLE)
            it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            it.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            it.type = "image/*"
        }
        startActivityForResult(intent, 100)
    }
    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {

        if (p1){
            when (p0?.id) {
                R.id.cashRdo -> {
                    binding.onlineRdo.isChecked = false
                    selectedModeTextView=binding.cash
                }
                R.id.onlineRdo -> {
                    binding.cashRdo.isChecked = false
                    selectedModeTextView=binding.online
                }
                R.id.debitRdo -> {
                    binding.creditRdo.isChecked = false
                    selectedTextView=binding.debit
                }
                R.id.creditRdo -> {
                    binding.debitRdo.isChecked = false
                    selectedTextView=binding.credit
                }
            }
        }
    }
}

