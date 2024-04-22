package com.example.rmp1

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.example.rmp1.database.AppDatabase
import com.example.rmp1.database.entity.Category
import com.example.rmp1.database.entity.Item

class MainViewModel(app: Application) : AndroidViewModel(app) {
//    private val dbHelper = DbHelper(getApplication())

    private val db =
        Room.databaseBuilder(app.applicationContext, AppDatabase::class.java, "RMP").build()
    private val categoryDao = db.getCategoryDao()
    private val fieldDao = db.getFieldDao()
    private val itemDao = db.getItemDao()
    private val valueDao = db.getValueDao()

    var newItem by mutableStateOf("")

    var categories by mutableStateOf(listOf<Category>())
        private set

    var items by mutableStateOf(listOf<Item>())
        private set
    var selectedCategory by mutableStateOf<Category?>(null)

    init {

    }

    fun selectCategory(category: Category) {
        selectedCategory = category
//        items = dbHelper.getItemsByCategory(category.categoryName)
    }

    fun addItem() {
        selectedCategory?.let {
//            dbHelper.addItem(newItem, it.id)
//            items = dbHelper.getItemsByCategory(it.categoryName)
        }
    }
}