package com.example.budgetbuddy.ui.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    val name : String,
    @PrimaryKey
    val id : String,
    val phone : String,
)
