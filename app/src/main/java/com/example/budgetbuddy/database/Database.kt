package com.example.budgetbuddy.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.budgetbuddy.ui.profile.User
import com.example.budgetbuddy.util.category.Category

@androidx.room.Database(version = 1, entities = [User::class, Category::class], exportSchema = false)
abstract class Database : RoomDatabase(){

    abstract fun userDao() : UserDao
    abstract fun categoryDao() : CategoryDao

    companion object{

        @Volatile
        private var INSTANCE : Database? = null

        fun getInstance(context: Context) : Database {
            if (INSTANCE==null)
            {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        Database::class.java,
                        "database").build()
                }
            }
            return INSTANCE!!
        }

    }

}