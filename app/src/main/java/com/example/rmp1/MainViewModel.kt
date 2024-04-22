package com.example.rmp1

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.example.rmp1.database.AppDatabase
import com.example.rmp1.database.entity.Category
import com.example.rmp1.database.entity.Field
import com.example.rmp1.database.entity.Item

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val db =
        Room.databaseBuilder(app.applicationContext, AppDatabase::class.java, "RMP")
            .allowMainThreadQueries().build()

    private val categoryDao = db.getCategoryDao()
    private val fieldDao = db.getFieldDao()
    private val itemDao = db.getItemDao()
    private val valueDao = db.getValueDao()

    var newItem by mutableStateOf("")
    var newCategory by mutableStateOf("")
    var newFieldName by mutableStateOf("")
    var newCategoryFields by mutableStateOf(listOf<Field>())

    var categories by mutableStateOf(listOf<Category>())
        private set
    var items by mutableStateOf(listOf<Item>())
        private set

    var selectedCategory by mutableStateOf<Category?>(null)
    var selectedItem by mutableStateOf<Item?>(null)

    init {
        categories = categoryDao.getAll()
    }

    fun selectCategory(category: Category) {
        selectedCategory = category
        items = itemDao.getByCategory(category.id)
    }

    fun selectItem(item: Item) {
        selectedItem = item
    }

    fun addCategory() {
        categoryDao.insert(Category(0, newCategory))
        val category = categoryDao.getByName(newCategory)

        newCategoryFields.forEach { it.categoryId = category.id }

        fieldDao.insertAll(*newCategoryFields.toTypedArray())
        categories = categoryDao.getAll()
    }

    fun addItem() {
        selectedCategory?.let { itemDao.insert(Item(0, selectedCategory!!.id, newItem)) }
        items = itemDao.getByCategory(selectedCategory!!.id)
    }

    fun appendField() {
        newCategoryFields += Field(0, 0, newFieldName)
    }

    fun deleteCategory() {
        selectedCategory?.let { categoryDao.delete(it) }
    }
}