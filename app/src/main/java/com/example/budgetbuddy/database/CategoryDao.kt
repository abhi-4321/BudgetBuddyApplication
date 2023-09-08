package com.example.budgetbuddy.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.budgetbuddy.util.category.Category

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(category : Category)

    @Delete
    suspend fun delete(category : Category)

    @Query("SELECT * FROM category")
    fun getCategory() : LiveData<List<Category>>

}