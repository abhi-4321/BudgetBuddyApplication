package com.example.budgetbuddy.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.budgetbuddy.ui.budget.MTransactions
import com.example.budgetbuddy.util.addTransaction.Transaction

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT * FROM transacts")
    fun getTransacts() : LiveData<List<Transaction>>

    @Query("SELECT * FROM " +
            "(SELECT month,category,SUM(amount) AS 'total' " +
            "FROM transacts GROUP BY month,category) WHERE month = :month")
    fun getMonthlyTransacts(month : String) : LiveData<List<MTransactions>>

    @Query("SELECT total FROM " +
            "(SELECT month,SUM(amount) AS 'total' " +
            "FROM transacts GROUP BY month)" +
            "WHERE month = :month")
    fun totalSpent(month : String) : LiveData<Int>

}
