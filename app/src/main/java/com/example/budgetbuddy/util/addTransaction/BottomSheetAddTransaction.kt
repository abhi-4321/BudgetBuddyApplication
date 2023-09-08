package com.example.budgetbuddy.util.addTransaction

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.budgetbuddy.databinding.AddTransactionBottomSheetBinding
import com.example.budgetbuddy.util.category.BottomSheetCategory
import com.example.budgetbuddy.util.date.DatePicker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.Integer.parseInt
import java.sql.Time
import java.text.DateFormat
import java.time.Instant
import java.util.*


class BottomSheetAddTransaction : BottomSheetDialogFragment(), DatePickerDialog.OnDateSetListener {
    lateinit var behavior: BottomSheetBehavior<FrameLayout>
    lateinit var binding: AddTransactionBottomSheetBinding
    lateinit var viewModel: AddTransactionViewModel

    private fun getWindowHeight() = resources.displayMetrics.heightPixels


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[AddTransactionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AddTransactionBottomSheetBinding.inflate(layoutInflater)
        binding.close.setOnClickListener {
            dialog?.cancel()
        }

        viewModel.getCategory().observe(requireActivity(), Observer {
            binding.category.setText(it)
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

        binding.time.setOnClickListener {
            val timePicker = TimePickerDialog(
                // pass the Context
                context,
                // listener to perform task
                // when time is picked
                pickTime(),
                // default hour when the time picker
                // dialog is opened
                currentHour,
                // default minute when the time picker
                // dialog is opened
                currentMin,
                // 24 hours time picker is
                // false (varies according to the region)
                false
            )

            // then after building the timepicker
            // dialog show the dialog to user
            timePicker.show()
        }

        binding.date.setOnClickListener {
            val datePicker = DatePicker()
            datePicker.show(childFragmentManager, "PICK DATE")
        }

        return binding.root
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
                    dismiss()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })
    }

    override fun onDateSet(p0: android.widget.DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val mCalendar: Calendar = Calendar.getInstance()
        mCalendar.set(Calendar.YEAR, year)
        mCalendar.set(Calendar.MONTH, month)
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val selectedDate: String =
            DateFormat.getDateInstance(DateFormat.DEFAULT).format(mCalendar.time)
        Toast.makeText(context, selectedDate, Toast.LENGTH_SHORT).show()
        binding.date.text = selectedDate
    }

    private fun pickTime(): TimePickerDialog.OnTimeSetListener {
        val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute -> // logic to properly handle
                // the picked timings by user
                val formattedTime: String = when {
                    hourOfDay == 0 -> {
                        if (minute < 10) {
                            "${hourOfDay + 12}:0${minute} am"
                        } else {
                            "${hourOfDay + 12}:${minute} am"
                        }
                    }
                    hourOfDay > 12 -> {
                        if (minute < 10) {
                            "${hourOfDay - 12}:0${minute} pm"
                        } else {
                            "${hourOfDay - 12}:${minute} pm"
                        }
                    }
                    hourOfDay == 12 -> {
                        if (minute < 10) {
                            "${hourOfDay}:0${minute} pm"
                        } else {
                            "${hourOfDay}:${minute} pm"
                        }
                    }
                    else -> {
                        if (minute < 10) {
                            "${hourOfDay}:${minute} am"
                        } else {
                            "${hourOfDay}:${minute} am"
                        }
                    }
                }

                binding.time.text = formattedTime
            }
        return timePickerDialogListener
    }
}