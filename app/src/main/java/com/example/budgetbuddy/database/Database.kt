package com.example.budgetbuddy.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.budgetbuddy.ui.budget.Budget
import com.example.budgetbuddy.ui.profile.User
import com.example.budgetbuddy.util.addTransaction.Transaction
import com.example.budgetbuddy.util.category.Category

@androidx.room.Database(version = 1, entities = [User::class, Category::class, Transaction::class, Budget::class], exportSchema = false)
abstract class Database : RoomDatabase(){

    val migration_1_2 = object : Migration(1,2) {
        override fun migrate(database: SupportSQLiteDatabase) {
        }

    }
    abstract fun userDao() : UserDao
    abstract fun categoryDao() : CategoryDao
    abstract fun transactionDao() : TransactionDao
    abstract fun budgetDao() : BudgetDao

    abstract fun createBudgetDao() : CreateBudgetDao

    companion object{

        @Volatile
        private var INSTANCE : Database? = null

        fun getInstance(context: Context) : Database {
            if (INSTANCE==null)
            {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        Database::class.java,
                        "midata").build()
                }
            }
            return INSTANCE!!
        }

    }

}