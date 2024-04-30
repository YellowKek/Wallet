package com.example.rmp1

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.rmp1.database.AppDatabase
import com.example.rmp1.database.entity.Category
import com.example.rmp1.database.entity.Field
import com.example.rmp1.database.entity.Item
import com.example.rmp1.database.entity.Value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val db =
        Room.databaseBuilder(app.applicationContext, AppDatabase::class.java, "RMP").build()

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

    private var itemsSelector: Job? = null
    private var fieldsSelector: Job? = null
    private var valuesSelector: Job? = null

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryDao.getAll().collect { categoriesList ->
                withContext(Dispatchers.Main) {
                    categories = categoriesList
                }
            }
        }
    }

    private fun getCategoryItems() {
        selectedCategory?.let {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    itemsSelector?.cancelAndJoin()
                } catch (_: Throwable) {

                }

                itemsSelector = launch {
                    itemDao.getByCategory(it.id).collect { dbItems ->
                        items = dbItems
                    }
                }
            }
        }
    }

    fun selectCategory(category: Category) {
        selectedCategory = category
        getCategoryFields()
        getCategoryItems()
    }

    fun selectItem(item: Item) {
        selectedItem = item
        viewModelScope.launch(Dispatchers.IO) {
            getItemValues()
        }
    }

    fun addCategory(newCategory: String, fields: List<String>): Boolean {
        if (categories.any { cat -> cat.name == newCategory }) {
            return false
        }
        viewModelScope.launch(Dispatchers.IO) {
            val newID = categoryDao.insert(newCategory)
            val newCategoryFields = Array(fields.size) { i -> Field(0, newID, fields[i]) }

            newCategoryFields.forEach { field -> fieldDao.insert(field.categoryId, field.name) }
            categoryDao.getAll().collect { categoryList ->
                categories = categoryList
            }
        }

        return true
    }

    fun addItem(newItem: String, values: Array<String>): Boolean {
        if (items.any { item -> item.name == newItem }) {
            return false
        }

        viewModelScope.launch(Dispatchers.IO) {
            selectedCategory?.let { cat ->
                val itemId = itemDao.insert(Item(1, cat.id, newItem))
                val itemValues = Array(values.size) { i ->
                    Value(1, itemId, selectedCategoryFields[i].id, values[i])
                }

                valueDao.insertAll(*itemValues)
                itemDao.getByCategory(cat.id).collect { dbItems ->
                    items = dbItems
                }
            }
        }

        return true
    }


    private fun getCategoryFields() {
        selectedCategory?.let {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    fieldsSelector?.cancelAndJoin()
                } catch (_: Throwable) {}
                fieldsSelector = launch {
                    fieldDao.getByCategory(it.id).collect { selectedCategoryFields = it }
                }
            }
        }
    }


    private fun getItemValues() {
        viewModelScope.launch(Dispatchers.IO) {
            selectedItem?.let { item ->
                try {
                    valuesSelector?.cancelAndJoin()
                } catch (_:Throwable) {}

                valuesSelector = launch {
                    valueDao.getByItem(item.id).collect { selectedItemValues = it }
                }
            }
        }
    }


    fun deleteCategory() {
        val selectedCategoryToDelete = selectedCategory

        viewModelScope.launch(Dispatchers.IO) {
            selectedCategoryToDelete?.let { category ->
                categoryDao.delete(category)
                categoryDao.getAll().collect { categoryList ->
                    categories = categoryList
                }
            }
            selectedCategory = null
        }
    }

    fun deleteItem() {
        selectedCategory?.let { cat ->
            selectedItem?.let { item ->
                viewModelScope.launch(Dispatchers.IO) {
                    itemDao.delete(item)
                    itemDao.getByCategory(cat.id)
                        .collect { dbItems -> items = dbItems }
                }
                selectedItem = null
            }
        }
    }

    fun saveItemValues(values: List<Value>) {
        viewModelScope.launch(Dispatchers.IO) {
            valueDao.insertAll(*values.toTypedArray())
              getItemValues()
        }
    }
}