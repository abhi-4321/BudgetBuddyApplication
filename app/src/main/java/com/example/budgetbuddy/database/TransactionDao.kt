package com.example.budgetbuddy.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.budgetbuddy.ui.budget.MTransactions
import com.example.budgetbuddy.ui.transactions.Flow
import com.example.budgetbuddy.util.addTransaction.Transaction

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: Transaction)

    @Query("delete from transacts where id=:id")
    suspend fun delete(id : Int)

    @Query("SELECT * FROM transacts")
    fun getTransacts(): LiveData<List<Transaction>>

    @Query(
        "SELECT * FROM " +
                "(SELECT month,category,SUM(amount) AS 'total' " +
                "FROM transacts GROUP BY month,category) WHERE month = :month"
    )
    fun getMonthlyTransacts(month: String): LiveData<List<MTransactions>>

    @Query(
        "SELECT total FROM " +
                "(SELECT month,SUM(amount) AS 'total' " +
                "FROM transacts WHERE debit != 'Credit' " +
                " GROUP BY month ) " +
                " WHERE month = :month "
    )
    fun totalSpent(month: String): LiveData<Int>

    @Query(
        "select * from transacts " +
                "where month = :month " +
                "order by date desc,time desc"
    )
    fun getTransactionsByMonth(month: String): LiveData<List<Transaction>>

    @Query("select distinct month from transacts")
    fun distinctMonths(): LiveData<List<String>>

    @Query(
        "select debit,sum(amount) as 'total' from transacts " +
                "group by debit"
    )
    fun getInOutFlows(): LiveData<List<Flow>>

    @Query("select * from transacts where id=:id")
    fun getTransact(id : Int) : LiveData<Transaction>
}
