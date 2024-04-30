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
import com.example.rmp1.database.entity.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

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

    init {
        // Собираем StateFlow в фоновом потоке
        coroutineScope.launch {
            categoryDao.getAll().collect { categoriesList ->
                // Переключаемся на основной поток для обновления состояния
                withContext(Dispatchers.Main) {
                    categories = categoriesList
                }
            }
        }
    }

    fun selectCategory(category: Category) {
        selectedCategory = category
        coroutineScope.launch {
            selectedCategoryFields = getCategoryFields(selectedCategory)
            itemDao.getByCategory(category.id).collect { dbItems -> items = dbItems }
        }
    }

    fun selectItem(item: Item) {
        selectedItem = item
        coroutineScope.launch {
            selectedCategoryFields = getCategoryFields(selectedCategory)
            selectedItemValues = getItemValues(selectedItem)
        }
    }

    fun addCategory(newCategory: String, fields: List<String>): Boolean {
        if (categories.any { cat -> cat.name == newCategory }) {
            return false
        }
        Log.d("newcat", "new fields str: $fields")

        coroutineScope.launch {
            val newID = categoryDao.insert(Category(0, newCategory))
            val newCategoryFields = Array(fields.size) { i -> Field(0, newID, fields[i]) }

            fieldDao.insertAll(*newCategoryFields)
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

        coroutineScope.launch {
            selectedCategory?.let { cat ->
                val itemId = itemDao.insert(Item(0, cat.id, newItem))
                val itemValues = Array(values.size) { i ->
                    Value(0, itemId, selectedCategoryFields[i].id, values[i])
                }

                valueDao.insertAll(*itemValues)
                itemDao.getByCategory(cat.id).collect { dbItems ->
                    items = dbItems
                }
            }
        }

        return true
    }


    private suspend fun getCategoryFields(category: Category?): List<Field> {
        var fields = listOf<Field>()

        category?.let {
            fieldDao.getByCategory(it.id).collect { dbFields ->
                fields = dbFields
            }
        }

        Log.d("newcat", fields.toString())
        return fields
    }


    private suspend fun getItemValues(item: Item?): List<Value> {
        return item?.let {
            valueDao.getByItem(it.id).firstOrNull() ?: emptyList()
        } ?: emptyList()
    }


    fun deleteCategory() {
        val selectedCategoryToDelete = selectedCategory

        coroutineScope.launch {
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
                coroutineScope.launch {
                    itemDao.delete(item)
                    itemDao.getByCategory(cat.id)
                        .collect { dbItems -> items = dbItems }
                }
                selectedItem = null
            }
        }
    }

    fun saveItemValues(values: List<Value>) {
        coroutineScope.launch {
            valueDao.insertAll(*values.toTypedArray())
            selectedItemValues = getItemValues(selectedItem)
        }
    }
}