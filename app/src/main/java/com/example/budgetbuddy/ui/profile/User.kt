package com.example.budgetbuddy.ui.profile

import androidx.room.Entity

@Entity(tableName = "user")
data class User(
    val name : String,
    val id : String,
    val phone : String,
)
