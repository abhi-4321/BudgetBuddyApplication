package com.example.budgetbuddy.util.category

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.CategoryBottomSheetBinding
import com.example.budgetbuddy.repository.CategoryRepository
import com.example.budgetbuddy.util.addTransaction.BottomSheetAddTransaction
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetCategory : BottomSheetDialogFragment(),OnClickListener,CategoryAdapter.ClickListener{

    lateinit var behavior: BottomSheetBehavior<FrameLayout>
    lateinit var binding:CategoryBottomSheetBinding
    private var arrayList = ArrayList<Category>()
    private lateinit var viewModel : CategoryViewModel
    private lateinit var fragment1: BottomSheetAddTransaction
    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragment1 = parentFragmentManager.fragments.find { it is BottomSheetAddTransaction } as BottomSheetAddTransaction
    }

    private fun getWindowHeight() =resources.displayMetrics.heightPixels

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {

        val categoryDao = Database.getInstance(requireContext()).categoryDao()
        val categoryRepository = CategoryRepository(categoryDao)
        viewModel = ViewModelProvider(this,
            CategoryViewModelFactory(categoryRepository)
        )[CategoryViewModel::class.java]

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

        var myAdapter : CategoryAdapter? = null

        viewModel.getCategories().observe(this, Observer {
            arrayList = it as ArrayList<Category>
            arrayList.add(
                Category(R.drawable.profile,"NEW CATEGORY")
            )
            myAdapter = CategoryAdapter(arrayList,requireContext(),this)
            binding.recycler.adapter = myAdapter
        })

        myAdapter?.notifyDataSetChanged()

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

                    fragment1.updateCategoryField(category)
                    dismiss()
            }

            R.id.transport -> {
                category="Transportation"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.rental -> {
                category="Rentals"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.water -> {
                category="Water Bill"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.phone -> {
                category="Phone Bill"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.electricity -> {
                category="Electricity Bill"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.gas -> {
                category="Gas Bill"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.tv -> {
                category="Television Bill"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.internet -> {
                category="Internet Bill"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.otherBill -> {
                category="Other Utility Bills"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.homeMain -> {
                category="Home Maintenance"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.vehicleMain -> {
                category="Vehicle Maintenance"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.medicalCheck -> {
                category="Medical Check-up"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.insurance -> {
                category="Insurance"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.education -> {
                category="Education"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.houseware -> {
                category="Houseware"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.personal -> {
                category="Personal Items"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.pets -> {
                category="Pets"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.homeService -> {
                category="Home Service"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.otherExpense -> {
                category="Other Expense"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.fitness -> {
                category="Fitness"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.makeup -> {
                category="Makeup"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.gifts -> {
                category="Gifts & Donations"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.streaming -> {
                category="Streaming Services"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.fungames -> {
                category="Fun Money"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.investment -> {
                category="Investment"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.debtCollect -> {
                category="Debt Collection"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.debt -> {
                category="Debt"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.loan -> {
                category="Loan"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.repayment -> {
                category="Repayment"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.payInterest -> {
                category="Pay Interest"
            }

            R.id.collectInterest -> {
                category="Collect Interest"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.salary -> {
                category="Salary"
                fragment1.updateCategoryField(category)
                dismiss()
            }

            R.id.income -> {
                category="Other Income"
                fragment1.updateCategoryField(category)
                dismiss()
            }
        }


        val sharedPreferences = requireContext().getSharedPreferences("Category",MODE_PRIVATE)
        sharedPreferences.getString("category","")

        val editor = sharedPreferences.edit()
        editor.putString("category",category)
        editor.apply()

        dismiss()
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(context,position.toString(),Toast.LENGTH_SHORT).show()
        dismiss()
    }

}