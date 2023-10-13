package com.example.budgetbuddy.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.budgetbuddy.ui.budget.IncomeSpent

@Dao
interface IncomeSpentDao {

    @Query("INSERT INTO hisab (id,income) VALUES (1,:income)")
    suspend fun insertIncome(income : Int)

    @Query("INSERT INTO hisab (id,spent) VALUES (1,:spent)")
    suspend fun insertSpent(spent : Int)

    @Query("UPDATE hisab SET income = :income ")
    suspend fun updateIncome(income : Int)

    @Query("UPDATE hisab SET income = income+:income ")
    suspend fun updateIncomeByTransaction(income : Int)
    @Query("UPDATE hisab SET income = income-:income ")
    suspend fun deleteIncomeByTransaction(income : Int)

    @Query("UPDATE hisab SET spent = :spent ")
    suspend fun updateSpent(spent : Int)

    @Query("SELECT * FROM hisab")
    fun gets() : LiveData<List<IncomeSpent>>

    @Query("UPDATE hisab SET spent = spent-:spent ")
    fun deleteSpentByTransaction(spent : Int)
}