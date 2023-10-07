package com.example.budgetbuddy.ui.budget

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.Database
import com.example.budgetbuddy.databinding.ActivityCreateBudgetBinding
import com.example.budgetbuddy.repository.BudgetRepository
import com.example.budgetbuddy.repository.CategoryRepository
import com.example.budgetbuddy.repository.IncomeSpentRepo
import com.example.budgetbuddy.repository.TransactionRepository
import com.example.budgetbuddy.util.addTransaction.AddTransactionViewModel
import com.example.budgetbuddy.util.addTransaction.AddTransactionViewModelFactory
import com.example.budgetbuddy.util.category.Category
import com.example.budgetbuddy.util.category.CategoryViewModel
import com.example.budgetbuddy.util.category.CategoryViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CreateBudgetActivity : AppCompatActivity(), View.OnClickListener,
    CategoryAdapter2.ClickListener, CustomDialogSetBudget2.AddItem {
    private lateinit var binding: ActivityCreateBudgetBinding
    private lateinit var viewModel: SharedViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var dialog: CustomDialogSetBudget
    private lateinit var budgetViewModel: BudgetViewModel
    private lateinit var arrayListNew: ArrayList<Budget>
    private val map = HashMap<String, String>()
    private val arrayLists = ArrayList<Item>()
    private val list = ArrayList<Item>()
    private lateinit var addTransactionViewModel: AddTransactionViewModel
    private val hashMap = HashMap<String, String>()
    private var arrayList = ArrayList<IncomeSpent>()
    private lateinit var incomeSpentRepo : IncomeSpentRepo

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val budgetDao = Database.getInstance(this).budgetDao()
        val budgetRepository = BudgetRepository(budgetDao)

        val categoryDao = Database.getInstance(this).categoryDao()
        val categoryRepository = CategoryRepository(categoryDao)

        val addTransactionDao = Database.getInstance(this).transactionDao()
        val transactionRepository = TransactionRepository(addTransactionDao)

        val incomeSpentDao = Database.getInstance(this).incomeSpentDao()
        incomeSpentRepo = IncomeSpentRepo(incomeSpentDao)

        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        budgetViewModel = ViewModelProvider(
            this,
            BudgetViewModelFactory(budgetRepository)
        )[BudgetViewModel::class.java]
        categoryViewModel = ViewModelProvider(
            this,
            CategoryViewModelFactory(categoryRepository)
        )[CategoryViewModel::class.java]

        addTransactionViewModel = ViewModelProvider(
            this,
            AddTransactionViewModelFactory(transactionRepository)
        )[AddTransactionViewModel::class.java]

        prePopulate()

        incomeSpentRepo.gets().observe(this){
                arrayList = it
            if (it.isNotEmpty()){
                binding.amount.setText((it[0].income).toString())
            }
            else{
                binding.amount.setText("0")
            }
        }

        binding.done.setOnClickListener {
            if (binding.amount.text.isNullOrEmpty()){
                binding.amount.error = "Enter a valid Amount"
            }else {
                insert()
                finish()
            }

        }

        binding.recycler.layoutManager = LinearLayoutManager(this)
        lateinit var adapter: CategoryAdapter2
        var arrayList = ArrayList<Category>()

        categoryViewModel.getCategories().observe(this) {
            arrayList = it as ArrayList<Category>
            arrayList.add(
                Category(R.drawable.profile, "NEW CATEGORY")
            )
            adapter = CategoryAdapter2(arrayList, this, this, categoryViewModel, this)
            binding.recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        val currentMonth =
            SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(System.currentTimeMillis())
                .toString()

        addTransactionViewModel.getMonthlyTransactions(currentMonth).observe(this) {

            for (transaction in it) {
                hashMap[transaction.category] = transaction.total
            }
        }

        budgetViewModel.getBudgets().observe(this) {
            arrayListNew = it
        }

        binding.close.setOnClickListener {
            onBackPressed()
        }

        viewModel.getBudget().observe(this) {
            val cat = it.first
            val amount = it.second

            map[cat] = amount
            setView(cat, amount)
        }

        dialog = CustomDialogSetBudget(this, this)

        binding.foodBtn.setOnClickListener(this)
        binding.transportationBills.setOnClickListener(this)
        binding.rentals.setOnClickListener(this)
        binding.waterBills.setOnClickListener(this)
        binding.phoneBills.setOnClickListener(this)
        binding.electricitybill.setOnClickListener(this)
        binding.gasBill.setOnClickListener(this)
        binding.telivisionBill.setOnClickListener(this)
        binding.internetBill.setOnClickListener(this)
        binding.otherUtilityBill.setOnClickListener(this)
        binding.homeMaintainenceBill.setOnClickListener(this)
        binding.vehicleMaintainenceBill.setOnClickListener(this)
        binding.medialCheckupBill.setOnClickListener(this)
        binding.insuranceBill.setOnClickListener(this)
        binding.educationBills.setOnClickListener(this)
        binding.houseWareBill.setOnClickListener(this)
        binding.personalItemsbills.setOnClickListener(this)
        binding.petsBills.setOnClickListener(this)
        binding.homeServiceBills.setOnClickListener(this)
        binding.otherExpenseBills.setOnClickListener(this)
        binding.fitnessBills.setOnClickListener(this)
        binding.makeupBills.setOnClickListener(this)
        binding.giftAndDonationBills.setOnClickListener(this)
        binding.stremingServicesBills.setOnClickListener(this)
        binding.funZoneBills.setOnClickListener(this)
        binding.investmentBills.setOnClickListener(this)
        binding.debtCollectBills.setOnClickListener(this)
        binding.debtBills.setOnClickListener(this)
        binding.loanbills.setOnClickListener(this)
        binding.repaymentBills.setOnClickListener(this)
        binding.payInterestBills.setOnClickListener(this)
        binding.collectInterestBills.setOnClickListener(this)
        binding.salaryBills.setOnClickListener(this)
        binding.otherIncomeBills.setOnClickListener(this)
    }

    private fun prePopulate() {


        budgetViewModel.getBudgets().observe(this) {
            for (item in it) {
                setView(item.category, item.limit.toString())
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun insert() {

        if(arrayList.isEmpty())
        {
            GlobalScope.launch(Dispatchers.IO) {
                incomeSpentRepo.insertIncome(binding.amount.text.toString().toInt())
            }
        }
        else{
            GlobalScope.launch(Dispatchers.IO) {
                incomeSpentRepo.updateIncome(binding.amount.text.toString().toInt())
            }
        }


        for (budget in map) {


            val budgett = if (hashMap.containsKey(budget.key)) {
                Budget(
                    getIcon(budget.key),
                    budget.key,
                    budget.value.toInt(),
                    hashMap[budget.key]!!.toInt()
                )
            } else {
                Budget(getIcon(budget.key), budget.key, budget.value.toInt(), 0)
            }


            if (arrayListNew.contains(budgett)) {
                budgetViewModel.update(budgett)
            } else {
                budgetViewModel.insert(budgett)
            }

        }


    }

    private fun setView(cat: String, _amount: String) {
        val amount = "₹$_amount"
        when (cat) {
            "Food & Beverages" -> {
                binding.food.text = amount
            }
            "Transportation" -> {
                binding.transport.text = amount
            }
            "Rentals" -> {
                binding.rental.text = amount
            }
            "Water Bill" -> {
                binding.water.text = amount
            }
            "Phone Bill" -> {
                binding.phone.text = amount
            }
            "Electricity Bill" -> {
                binding.electricity.text = amount
            }
            "Gas Bill" -> {
                binding.gas.text = amount
            }
            "Television Bill" -> {
                binding.tv.text = amount
            }
            "Internet Bill" -> {
                binding.internet.text = amount
            }
            "Other Utility Bills" -> {
                binding.otherBill.text = amount
            }
            "Home Maintenance" -> {
                binding.homeMain.text = amount
            }
            "Vehicle Maintenance" -> {
                binding.vehicleMain.text = amount
            }
            "Medical Check-up" -> {
                binding.medicalCheck.text = amount
            }
            "Insurance" -> {
                binding.insurance.text = amount
            }
            "Education" -> {
                binding.education.text = amount
            }
            "Houseware" -> {
                binding.houseware.text = amount
            }
            "Personal Items" -> {
                binding.personal.text = amount
            }
            "Pets" -> {
                binding.pets.text = amount
            }
            "Home Service" -> {
                binding.homeService.text = amount
            }
            "Other Expense" -> {
                binding.otherExpense.text = amount
            }
            "Fitness" -> {
                binding.fitness.text = amount
            }
            "Makeup" -> {
                binding.makeup.text = amount
            }
            "Gifts & Donations" -> {
                binding.gifts.text = amount
            }
            "Streaming Services" -> {
                binding.streaming.text = amount
            }
            "Fun Money" -> {
                binding.fungames.text = amount
            }
            "Investment" -> {
                binding.investment.text = amount
            }
            "Debt Collection" -> {
                binding.debtCollect.text = amount
            }
            "Debt" -> {
                binding.debt.text = amount
            }
            "Loan" -> {
                binding.loan.text = amount
            }
            "Repayment" -> {
                binding.repayment.text = amount
            }
            "Pay Interest" -> {
                binding.payInterest.text = amount
            }
            "Collect Interest" -> {
                binding.collectInterest.text = amount
            }
            "Salary" -> {
                binding.salary.text = amount
            }
            "Other Income" -> {
                binding.income.text = amount
            }
            else -> {
                list.add(Item(cat, amount))
            }
        }
        viewModel.setArrayList(list)
    }

    private fun getIcon(cat: String): Int {
        val icon: Int
        when (cat) {
            "Food & Beverages" -> {
                icon = 0
            }
            "Transportation" -> {
                icon = 1
            }
            "Rentals" -> {
                icon = 2
            }
            "Water Bill" -> {
                icon = 3
            }
            "Phone Bill" -> {
                icon = 4
            }
            "Electricity Bill" -> {
                icon = 5
            }
            "Gas Bill" -> {
                icon = 6
            }
            "Television Bill" -> {
                icon = 7
            }
            "Internet Bill" -> {
                icon = 8
            }
            "Other Utility Bills" -> {
                icon = 9
            }
            "Home Maintenance" -> {
                icon = 10
            }
            "Vehicle Maintenance" -> {
                icon = 11
            }
            "Medical Check-up" -> {
                icon = 12
            }
            "Insurance" -> {
                icon = 13
            }
            "Education" -> {
                icon = 14
            }
            "Houseware" -> {
                icon = 15
            }
            "Personal Items" -> {
                icon = 16
            }
            "Pets" -> {
                icon = 17
            }
            "Home Service" -> {
                icon = 18
            }
            "Other Expense" -> {
                icon = 19
            }
            "Fitness" -> {
                icon = 20
            }
            "Makeup" -> {
                icon = 21
            }
            "Gifts & Donations" -> {
                icon = 22
            }
            "Streaming Services" -> {
                icon = 23
            }
            "Fun Money" -> {
                icon = 24
            }
            "Investment" -> {
                icon = 25
            }
            "Debt Collection" -> {
                icon = 26
            }
            "Debt" -> {
                icon = 27
            }
            "Loan" -> {
                icon = 28
            }
            "Repayment" -> {
                icon = 29
            }
            "Pay Interest" -> {
                icon = 30
            }
            "Collect Interest" -> {
                icon = 31
            }
            "Salary" -> {
                icon = 32
            }
            "Other Income" -> {
                icon = 33
            }
            else -> {
                icon = 34
            }
        }
        return icon
    }

    override fun onClick(p0: View?) {
        var icon = 0
        var cat = ""

        when (p0?.id) {
            R.id.foodBtn -> {
                icon = 0
                cat = "Food & Beverages"
            }
            R.id.transportationBills -> {
                icon = 1
                cat = "Transportation"
            }
            R.id.rentals -> {
                icon = 2
                cat = "Rentals"
            }
            R.id.waterBills -> {
                icon = 3
                cat = "Water Bill"
            }
            R.id.phoneBills -> {
                icon = 4
                cat = "Phone Bill"
            }
            R.id.electricitybill -> {
                icon = 5
                cat = "Electricity Bill"
            }
            R.id.gasBill -> {
                icon = 6
                cat = "Gas Bill"
            }
            R.id.telivisionBill -> {
                icon = 7
                cat = "Television Bill"
            }
            R.id.internetBill -> {
                icon = 8
                cat = "Internet Bill"
            }
            R.id.otherUtilityBill -> {
                icon = 9
                cat = "Other Utility Bills"
            }
            R.id.homeMaintainenceBill -> {
                icon = 10
                cat = "Home Maintenance"
            }
            R.id.vehicleMaintainenceBill -> {
                icon = 11
                cat = "Vehicle Maintenance"
            }
            R.id.medialCheckupBill -> {
                icon = 12
                cat = "Medical Check-up"
            }
            R.id.insuranceBill -> {
                icon = 13
                cat = "Insurance"
            }
            R.id.educationBills -> {
                icon = 14
                cat = "Education"
            }
            R.id.houseWareBill -> {
                icon = 15
                cat = "Houseware"
            }
            R.id.personalItemsbills -> {
                icon = 16
                cat = "Personal Items"
            }
            R.id.petsBills -> {
                icon = 17
                cat = "Pets"
            }
            R.id.homeServiceBills -> {
                icon = 18
                cat = "Home Service"
            }
            R.id.otherExpenseBills -> {
                icon = 19
                cat = "Other Expense"
            }
            R.id.fitnessBills -> {
                icon = 20
                cat = "Fitness"
            }
            R.id.makeupBills -> {
                icon = 21
                cat = "Makeup"
            }
            R.id.giftAndDonationBills -> {
                icon = 22
                cat = "Gifts & Donations"
            }
            R.id.stremingServicesBills -> {
                icon = 23
                cat = "Streaming Services"
            }
            R.id.funZoneBills -> {
                icon = 24
                cat = "Fun Money"
            }
            R.id.investmentBills -> {
                icon = 25
                cat = "Investment"
            }
            R.id.debtCollectBills -> {
                icon = 26
                cat = "Debt Collection"
            }
            R.id.debtBills -> {
                icon = 27
                cat = "Debt"
            }
            R.id.loanbills -> {
                icon = 28
                cat = "Loan"
            }
            R.id.repaymentBills -> {
                icon = 29
                cat = "Repayment"
            }
            R.id.payInterestBills -> {
                icon = 30
                cat = "Pay Interest"
            }
            R.id.collectInterestBills -> {
                icon = 31
                cat = "Collect Interest"
            }
            R.id.salaryBills -> {
                icon = 32
                cat = "Salary"
            }
            R.id.otherIncomeBills -> {
                icon = 33
                cat = "Other Income"
            }
        }

        viewModel.setIcon(icon)
        viewModel.setCategory(cat)
        dialog.show()
    }

    override fun onItemClick(category: String?) {
        val dialog = CustomDialogSetBudget2(this, this, category!!, this)
        dialog.show()
    }

    override fun onSet(category: String?, amount: String) {
        arrayLists.add(Item(category!!, amount))
        viewModel.setArrayList(arrayLists)
    }
}