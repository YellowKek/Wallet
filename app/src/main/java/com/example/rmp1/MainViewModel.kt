package com.example.rmp1

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.rmp1.database.Category
import com.example.rmp1.database.DbHelper
import com.example.rmp1.database.Item

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val dbHelper = DbHelper(getApplication())
    var newItem by mutableStateOf("")

    var categories by mutableStateOf(listOf<Category>())
        private set

    var items by mutableStateOf(listOf<Item>())
        private set

    var newCategory by mutableStateOf("")
    var selectedCategory by mutableStateOf<Category?>(null)

    init {
        categories = dbHelper.getAllCategories()
    }

    fun addCategory() {
        dbHelper.addCategory(newCategory)
        categories = dbHelper.getAllCategories()
    }

    fun selectCategory(category: Category) {
        selectedCategory = category
        items = dbHelper.getItemsByCategory(category.id)
    }

    fun addItem() {
        selectedCategory?.let {
            dbHelper.addItem(newItem, it.id)
            items = dbHelper.getItemsByCategory(it.id)
        }
    }
}