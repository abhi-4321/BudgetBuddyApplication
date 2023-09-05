package com.example.budgetbuddy.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.budgetbuddy.ui.profile.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user : User)

    @Delete
    suspend fun delete(user : User)

    @Update
    suspend fun update(user : User)

    @Query("SELECT * FROM user")
    fun getUsers() : LiveData<List<User>>

}
