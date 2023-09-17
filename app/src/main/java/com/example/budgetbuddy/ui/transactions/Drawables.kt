package com.example.budgetbuddy.ui.transactions

import androidx.annotation.StringRes
import com.example.budgetbuddy.R

enum class Drawables (@StringRes val resId: Int, val stored: Int) {

        Food(R.drawable.food, 0),
        Transport(R.drawable.transportation, 1),
        Rental(R.drawable.rental, 2),
        Water(R.drawable.waterbill, 3),
        Phone(R.drawable.phonebill, 4),
        Electricity(R.drawable.electricitybill, 5),
        Gas(R.drawable.gasbill, 6),
        Tv(R.drawable.tvbill, 7),
        Internet(R.drawable.wifibill, 8),
        OtherBill(R.drawable.bill, 9),
        HomeMain(R.drawable.homemaintenance, 10),
        VehicleMain(R.drawable.vehiclemaintenance, 11),
        MedicalCheck(R.drawable.medicalcheckup, 12),
        Insurance(R.drawable.insurance, 13),
        Education(R.drawable.education, 14),
        Houseware(R.drawable.houseware, 15),
        Personal(R.drawable.personalitems, 16),
        Pets(R.drawable.pets, 17),
        HomeService(R.drawable.homeservices, 18),
        OtherExpense(R.drawable.otherexpense, 19),
        Fitness(R.drawable.fitness, 20),
        Makeup(R.drawable.makeup, 21),
        Gifts(R.drawable.gifts, 22),
        Streaming(R.drawable.streaming, 23),
        Fungames(R.drawable.fungames, 24),
        Investment(R.drawable.investment, 25),
        DebCollect(R.drawable.debtcollect, 26),
        Debt(R.drawable.debtcollect, 27),
        Loan(R.drawable.loan, 28),
        Repayment(R.drawable.repaymeny, 29),
        PayInterest(R.drawable.payintererst, 30),
        CollectInterest(R.drawable.collectinterest, 31),
        Salary(R.drawable.salary, 32),
        Income(R.drawable.income, 33);

        companion object {
            @StringRes
            fun asRes(stored: Int): Int = values().first{ it.stored == stored}.resId
        }
}