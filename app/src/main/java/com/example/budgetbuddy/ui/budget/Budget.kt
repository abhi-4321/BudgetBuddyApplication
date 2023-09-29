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
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Budget

        if (image != other.image) return false
        if (category != other.category) return false
        if (spent != other.spent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = image
        result = 31 * result + category.hashCode()
        result = 31 * result + spent
        return result
    }
}