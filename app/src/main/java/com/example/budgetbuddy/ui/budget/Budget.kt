package com.example.budgetbuddy.ui.budget

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class Budget(

    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val image : Int,
    val category : String,
    val limit : Int,
    val spent : Int,
)