package com.example.budgetbuddy.repository

import androidx.lifecycle.LiveData
import com.example.budgetbuddy.database.UserDao
import com.example.budgetbuddy.ui.profile.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insert(user : User) {
        userDao.insert(user)
    }

    suspend fun delete(user : User) {
        userDao.delete(user)
    }

    suspend fun update(user : User) {
        userDao.update(user)
    }

    fun getUsers() : LiveData<List<User>> {
        return userDao.getUsers()
    }

}