package com.example.budgetbuddy.util.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.CategoryBottomSheetBinding
import com.example.budgetbuddy.repository.CategoryRepository
import com.example.budgetbuddy.util.addTransaction.SharedTransactionViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetCategory : BottomSheetDialogFragment(),OnClickListener,CategoryAdapter.ClickListener{

    lateinit var behavior: BottomSheetBehavior<FrameLayout>
    lateinit var binding:CategoryBottomSheetBinding
    private var arrayList = ArrayList<Category>()
    private lateinit var sharedTransactionViewModel: SharedTransactionViewModel
    private lateinit var viewModel : CategoryViewModel

    private fun getWindowHeight() =resources.displayMetrics.heightPixels

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {

        val categoryDao = Database.getInstance(requireContext()).categoryDao()
        val categoryRepository = CategoryRepository(categoryDao)

        viewModel = ViewModelProvider(this,CategoryViewModelFactory(categoryRepository))[CategoryViewModel::class.java]
        sharedTransactionViewModel = ViewModelProvider(requireActivity())[SharedTransactionViewModel::class.java]

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

        var icon = 0
        var category = ""
        val id = p0?.id
        when(id) {
            R.id.food -> {
                category="Food & Beverages"
                icon=0
            }

            R.id.transport -> {
                category="Transportation"
                icon=1
            }

            R.id.rental -> {
                category="Rentals"
                icon=2
            }

            R.id.water -> {
                category="Water Bill"
                icon=3
            }

            R.id.phone -> {
                category="Phone Bill"
                icon=4
            }

            R.id.electricity -> {
                category="Electricity Bill"
                icon=5
            }

            R.id.gas -> {
                category="Gas Bill"
                icon=6
            }

            R.id.tv -> {
                category="Television Bill"
                icon=7
            }

            R.id.internet -> {
                category="Internet Bill"
                icon=8
            }

            R.id.otherBill -> {
                category="Other Utility Bills"
                icon=9
            }

            R.id.homeMain -> {
                category="Home Maintenance"
                icon=10
            }

            R.id.vehicleMain -> {
                category="Vehicle Maintenance"
                icon=11
            }

            R.id.medicalCheck -> {
                category="Medical Check-up"
                icon=12
            }

            R.id.insurance -> {
                icon=13
                category="Insurance"
            }

            R.id.education -> {
                category="Education"
                icon=14
            }

            R.id.houseware -> {
                category="Houseware"
                icon=15
            }

            R.id.personal -> {
                category="Personal Items"
                icon=16
            }

            R.id.pets -> {
                category="Pets"
                icon=17
            }

            R.id.homeService -> {
                category="Home Service"
                icon=18
            }

            R.id.otherExpense -> {
                category="Other Expense"
                icon=19
            }

            R.id.fitness -> {
                category="Fitness"
                icon=20
            }

            R.id.makeup -> {
                category="Makeup"
                icon=21
            }

            R.id.gifts -> {
                category="Gifts & Donations"
                icon=22
            }

            R.id.streaming -> {
                category="Streaming Services"
                icon=23
            }

            R.id.fungames -> {
                category="Fun Money"
                icon=24
            }

            R.id.investment -> {
                category="Investment"
                icon=25
            }

            R.id.debtCollect -> {
                category="Debt Collection"
                icon=26
            }

            R.id.debt -> {
                category="Debt"
                icon=27
            }

            R.id.loan -> {
                category="Loan"
                icon=28
            }


            R.id.repayment -> {
                category="Repayment"
                icon=29
            }

            R.id.payInterest -> {
                category="Pay Interest"
                icon=30
            }

            R.id.collectInterest -> {
                category="Collect Interest"
                icon=31
            }

            R.id.salary -> {
                category="Salary"
                icon=32
            }


            R.id.income -> {
                category="Other Income"
                icon=33
            }
        }

        sharedTransactionViewModel.setCategory(category)
        sharedTransactionViewModel.setIcon(icon)
        dismiss()
    }

    override fun onItemClick(category: String) {
        sharedTransactionViewModel.setCategory(category)
        sharedTransactionViewModel.setIcon(34)
        dismiss()
    }

}