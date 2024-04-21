package com.example.rmp1

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.rmp1.database.Category
import com.example.rmp1.database.DbHelper

class MainViewModel(app: Application) : AndroidViewModel(app){
    private val dbHelper = DbHelper(getApplication())

    var categories by mutableStateOf(listOf<Category>())
        private set

    init {
        categories = dbHelper.getAllCategories()
    }
}