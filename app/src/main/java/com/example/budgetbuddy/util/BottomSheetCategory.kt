package com.example.budgetbuddy.util

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.CategoryBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetCategory : BottomSheetDialogFragment(),OnClickListener{
    lateinit var behavior: BottomSheetBehavior<FrameLayout>
    lateinit var binding:CategoryBottomSheetBinding
    private val arrayList = ArrayList<CategoryItem>()

    private fun getWindowHeight() =resources.displayMetrics.heightPixels

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= CategoryBottomSheetBinding.inflate(layoutInflater)
        binding.close.setOnClickListener {
            dialog?.cancel()
        }

        binding.food.setOnClickListener(this)
        binding.transport.setOnClickListener(this)
        binding.rental.setOnClickListener(this)
        binding.water.setOnClickListener(this)
        binding.phone.setOnClickListener(this)
        binding.electricity.setOnClickListener(this)
        binding.gas.setOnClickListener(this)
        binding.tv.setOnClickListener(this)
        binding.internet.setOnClickListener(this)
        binding.otherBill.setOnClickListener(this)
        binding.homeMain.setOnClickListener(this)
        binding.vehicleMain.setOnClickListener(this)
        binding.medicalCheck.setOnClickListener(this)
        binding.insurance.setOnClickListener(this)
        binding.education.setOnClickListener(this)
        binding.houseware.setOnClickListener(this)
        binding.personal.setOnClickListener(this)
        binding.pets.setOnClickListener(this)
        binding.homeService.setOnClickListener(this)
        binding.otherExpense.setOnClickListener(this)
        binding.fitness.setOnClickListener(this)
        binding.makeup.setOnClickListener(this)
        binding.gifts.setOnClickListener(this)
        binding.streaming.setOnClickListener(this)
        binding.fungames.setOnClickListener(this)
        binding.investment.setOnClickListener(this)
        binding.debtCollect.setOnClickListener(this)
        binding.debt.setOnClickListener(this)
        binding.loan.setOnClickListener(this)
        binding.repayment.setOnClickListener(this)
        binding.payInterest.setOnClickListener(this)
        binding.collectInterest.setOnClickListener(this)
        binding.salary.setOnClickListener(this)
        binding.income.setOnClickListener(this)

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.isVerticalScrollBarEnabled = false

        arrayList.add(CategoryItem(R.drawable.transferout,"Outgoing Transfer"))
        arrayList.add(CategoryItem(R.drawable.transferin,"Incoming Transfer"))
        arrayList.add(CategoryItem(R.drawable.profile,"NEW CATEGORY"))

        val myAdapter = CategoryAdapter(arrayList)
        binding.recycler.adapter = myAdapter
        myAdapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val view : FrameLayout? = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        view?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        view?.layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT

        behavior = BottomSheetBehavior.from(view!!)
        behavior.peekHeight = getWindowHeight()

        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED){
//                    dismiss()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })
    }

    override fun onClick(p0: View?) {

        var category = ""
        val id = p0?.id
        when(id) {
            R.id.food -> {
                category="Food & Beverages"
            }

            R.id.transport -> {
                category="Transportation"
            }

            R.id.rental -> {
                category="Rentals"
            }

            R.id.water -> {
                category="Water Bill"
            }

            R.id.phone -> {
                category="Phone Bill"
            }

            R.id.electricity -> {
                category="Electricity Bill"
            }

            R.id.gas -> {
                category="Gas Bill"
            }

            R.id.tv -> {
                category="Television Bill"
            }

            R.id.internet -> {
                category="Internet Bill"
            }

            R.id.otherBill -> {
                category="Other Utility Bills"
            }

            R.id.homeMain -> {
                category="Home Maintenance"
            }

            R.id.vehicleMain -> {
                category="Vehicle Maintenance"
            }

            R.id.medicalCheck -> {
                category="Medical Check-up"
            }

            R.id.insurance -> {
                category="Insurance"
            }

            R.id.education -> {
                category="Education"
            }

            R.id.houseware -> {
                category="Houseware"
            }

            R.id.personal -> {
                category="Personal Items"
            }

            R.id.pets -> {
                category="Pets"
            }

            R.id.homeService -> {
                category="Home Service"
            }

            R.id.otherExpense -> {
                category="Other Expense"
            }

            R.id.fitness -> {
                category="Fitness"
            }

            R.id.makeup -> {
                category="Makeup"
            }

            R.id.gifts -> {
                category="Gifts & Donations"
            }

            R.id.streaming -> {
                category="Streaming Services"
            }

            R.id.fungames -> {
                category="Fun Money"
            }

            R.id.investment -> {
                category="Investment"
            }

            R.id.debtCollect -> {
                category="Debt Collection"
            }

            R.id.debt -> {
                category="Debt"
            }

            R.id.loan -> {
                category="Loan"
            }

            R.id.repayment -> {
                category="Repayment"
            }

            R.id.payInterest -> {
                category="Pay Interest"
            }

            R.id.collectInterest -> {
                category="Collect Interest"
            }

            R.id.salary -> {
                category="Salary"
            }

            R.id.income -> {
                category="Other Income"
            }
        }

        val sharedPreferences = requireContext().getSharedPreferences("Category",MODE_PRIVATE)
        sharedPreferences.getString("category","")

        val editor = sharedPreferences.edit()
        editor.putString("category",category)
        editor.apply()

    }
}