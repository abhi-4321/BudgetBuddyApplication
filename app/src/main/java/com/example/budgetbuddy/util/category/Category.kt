package com.example.budgetbuddy.util.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(

    @PrimaryKey(autoGenerate = true)
    var sid : Int=0,
    var category : String?=""
)
