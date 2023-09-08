package com.example.budgetbuddy.util.category

import android.content.Context
import androidx.lifecycle.*
import com.example.budgetbuddy.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {

    fun delete(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.delete(category)
        }
    }

    fun insert(category: Category){
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.insert(category)
        }
    }

    fun getCategories() : LiveData<List<Category>> {
        return categoryRepository.getCategories()
    }
}