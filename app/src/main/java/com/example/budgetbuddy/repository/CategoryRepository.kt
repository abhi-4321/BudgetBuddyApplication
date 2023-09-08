package com.example.budgetbuddy.repository

import androidx.lifecycle.LiveData
import com.example.budgetbuddy.database.CategoryDao
import com.example.budgetbuddy.util.category.Category

class CategoryRepository(private val categoryDao: CategoryDao) {

    suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }

    suspend fun delete(category: Category) {
        categoryDao.delete(category)
    }

    fun getCategories() : LiveData<List<Category>> {
        return categoryDao.getCategory()
    }

}