package com.example.rmp1

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.example.rmp1.database.AppDatabase
import com.example.rmp1.database.entity.Category
import com.example.rmp1.database.entity.Field
import com.example.rmp1.database.entity.Item
import com.example.rmp1.database.entity.Value

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val db =
        Room.databaseBuilder(app.applicationContext, AppDatabase::class.java, "RMP")
            .allowMainThreadQueries().build()

    private val categoryDao = db.getCategoryDao()
    private val fieldDao = db.getFieldDao()
    private val itemDao = db.getItemDao()
    private val valueDao = db.getValueDao()

    var selectedCategoryFields by mutableStateOf(listOf<Field>())
    var selectedItemValues by mutableStateOf(listOf<Value>())

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
        selectedCategoryFields = getCategoryFields(selectedCategory)
        items = itemDao.getByCategory(category.id)
    }

    fun selectItem(item: Item) {
        selectedItem = item
        selectedItemValues = getItemValues(selectedItem)
    }

    fun addCategory(newCategory: String, fields: List<String>): Boolean {
        if (categories.any { cat -> cat.name == newCategory }) {
            return false
        }

        categoryDao.insert(Category(0, newCategory))
        val category = categoryDao.getByName(newCategory)

        val newCategoryFields = Array(fields.size) { Field(0, category.id, "") }
        for (i in newCategoryFields.indices) {
            newCategoryFields[i].name = fields[i]
        }

        fieldDao.insertAll(*newCategoryFields)
        categories = categoryDao.getAll()
        return true
    }

    fun addItem(newItem: String, values: Array<String>): Boolean {
        selectedCategory?.let { cat ->
            if (items.any { item -> item.name == newItem }) {
                return false
            }

            val itemId = itemDao.insert(Item(0, cat.id, newItem))
            val itemValues = Array(values.size) { Value(0, itemId, 0, "") }
            for (i in itemValues.indices) {
                itemValues[i].fieldId = selectedCategoryFields[i].id
                itemValues[i].value = values[i]
            }

            valueDao.insertAll(*itemValues)
            items = itemDao.getByCategory(cat.id)
        }

        return true
    }

    fun getCategoryFields(category: Category?): List<Field> {
        category?.let { return fieldDao.getByCategory(it.id) }
        return emptyList()
    }

    fun getItemValues(item: Item?): List<Value> {
        item?.let { return valueDao.getByItem(it.id) }
        return emptyList()
    }

    fun deleteCategory() {
        selectedCategory?.let { categoryDao.delete(it) }
        categories = categoryDao.getAll()
        selectedCategory = null
    }

    fun deleteItem() {
        selectedItem?.let { itemDao.delete(it) }
        items = itemDao.getByCategory(selectedCategory!!.id)
        selectedItem = null
    }

    fun saveItemValues(values: List<Value>) {
        for (value in values) {
            valueDao.update(value)
        }
        selectedItemValues = getItemValues(selectedItem)
    }
}