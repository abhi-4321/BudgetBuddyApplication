package com.example.budgetbuddy.ui.budget

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class Budget(
    val image : Int,
    @PrimaryKey()
    val category : String,
    val limit : Int,
    val spent : Int
)