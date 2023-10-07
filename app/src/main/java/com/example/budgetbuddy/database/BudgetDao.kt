package com.example.budgetbuddy.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.budgetbuddy.ui.budget.Budget

@Dao
interface BudgetDao {

    @Insert
    suspend fun insert(budget : Budget)
    @Delete
    suspend fun delete(budget : Budget)

    @Query("SELECT * FROM budget")
    fun getBudgets() : LiveData<List<Budget>>

    @Update
    suspend fun update(budget: Budget)

    @Query("UPDATE budget " +
            "SET spent = :spent " +
            "WHERE category = :category ")
    suspend fun updateSpent(spent : Int , category : String)

    @Query("UPDATE budget " +
            "SET 'limit' = :limit " +
            "WHERE category = :category")
    suspend fun updateLimit(limit : Int, category : String)
}