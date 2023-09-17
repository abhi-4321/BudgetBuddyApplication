package com.example.budgetbuddy.util.addTransaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transacts")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val icon: Int,
    val amount: String,
    val remarks: String,
    val imageUri: String,
    val mode: String,
    val category: String,
    val debit: String,
    val date: String,
    val time: String
)

