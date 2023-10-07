package com.example.budgetbuddy.ui.budget

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hisab")
data class IncomeSpent(
    @PrimaryKey()
    val id : Int,
    val income : Int?,
    val spent : Int?
)
