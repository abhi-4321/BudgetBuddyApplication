package com.example.budgetbuddy.util.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {

    fun getCategories() : LiveData<List<Category>> {
        return categoryRepository.getCategories()
    }

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

}