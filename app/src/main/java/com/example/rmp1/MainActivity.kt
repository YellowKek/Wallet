package com.example.rmp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rmp1.navigation.NavContent
import com.example.rmp1.ui.theme.RMP1Theme

class MainActivity : ComponentActivity() {
    val mvm: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            val position by navController.currentBackStackEntryAsState()
            RMP1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavContent(
                        navController = navController,
                        categories = mvm.categories,
                        items = mvm.items,
                        newCategoryFields = mvm.newCategoryFields,
                        isCategorySelected = mvm.selectedCategory != null,
                        newCategory = mvm.newCategory,
                        newItem = mvm.newItem,
                        newFieldName = mvm.newFieldName,
                        selectedItem = mvm.selectedItem,
                        onSelectCategory = mvm::selectCategory,
                        onSelectItem = mvm::selectItem,
                        onItemChange = { mvm.newItem = it },
                        onCategoryChange = { mvm.newCategory = it },
                        onFieldChange = { mvm.newFieldName = it },
                        onDeleteCategory = mvm::deleteCategory,
                        onAddCategory = mvm::addCategory,
                        onAddItem = mvm::addItem,
                        onAppendField = mvm::appendField
                    )
                }
            }
        }
    }
}
