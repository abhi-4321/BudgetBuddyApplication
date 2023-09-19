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
        binding.transportationBills.setOnClickListener {
            dialog.show()
        }
        binding.rentals.setOnClickListener {
            dialog.show()
        }
        binding.waterBills.setOnClickListener {
            dialog.show()
        }
        binding.phoneBills.setOnClickListener {
            dialog.show()
        }
        binding.electricitybill.setOnClickListener {
            dialog.show()
        }
        binding.gasBill.setOnClickListener {
            dialog.show()
        }
        binding.telivisionBill.setOnClickListener {
            dialog.show()
        }
        binding.internetBill.setOnClickListener {
            dialog.show()
        }
        binding.otherUtilityBill.setOnClickListener {
            dialog.show()
        }
        binding.homeMaintainenceBill.setOnClickListener {
            dialog.show()
        }
        binding.vehicleMaintainenceBill.setOnClickListener {
            dialog.show()
        }
        binding.medialCheckupBill.setOnClickListener {
            dialog.show()
        }
        binding.insuranceBill.setOnClickListener {
            dialog.show()
        }
        binding.educationBills.setOnClickListener {
            dialog.show()
        }
        binding.houseWareBill.setOnClickListener {
            dialog.show()
        }
        binding.personalItemsbills.setOnClickListener {
            dialog.show()
        }
        binding.petsBills.setOnClickListener {
            dialog.show()
        }
        binding.homeServiceBills.setOnClickListener {
            dialog.show()
        }
        binding.otherExpenseBills.setOnClickListener {
            dialog.show()
        }
        binding.fitnessBills.setOnClickListener {
            dialog.show()
        }
        binding.makeupBills.setOnClickListener {
            dialog.show()
        }
        binding.giftAndDonationBills.setOnClickListener {
            dialog.show()
        }
        binding.stremingServicesBills.setOnClickListener {
            dialog.show()
        }
        binding.funZoneBills.setOnClickListener {
            dialog.show()
        }
        binding.investmentBills.setOnClickListener {
            dialog.show()
        }
        binding.debtCollectBills.setOnClickListener {
            dialog.show()
        }
        binding.debtBills.setOnClickListener {
            dialog.show()
        }
        binding.loanbills.setOnClickListener {
            dialog.show()
        }
        binding.repaymentBills.setOnClickListener {
            dialog.show()
        }
        binding.payInterestBills.setOnClickListener {
            dialog.show()
        }
        binding.collectInterestBills.setOnClickListener {
            dialog.show()
        }
        binding.salaryBills.setOnClickListener {
            dialog.show()
        }
        binding.otherIncomeBills.setOnClickListener {
            dialog.show()
        }
        val cancelBtn = dialog.findViewById<Button>(R.id.cancelButton)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }


    }
}