package com.example.budgetbuddy.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.budgetbuddy.ui.profile.User

@androidx.room.Database(version = 1, entities = [User::class], exportSchema = false)
abstract class Database : RoomDatabase(){

    abstract fun userDao() : UserDao

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