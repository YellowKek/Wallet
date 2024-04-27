package com.example.rmp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rmp1.navigation.NavContent
import com.example.rmp1.ui.theme.RMP1Theme

class MainActivity : ComponentActivity() {
    val mvm: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            RMP1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavContent(
                        navController = navController,
                        categories = mvm.categories,
                        onSelectCategory = mvm::selectCategory,
                        onSelectItem = mvm::selectItem,
                        items = mvm.items,
                        selectedCategory = mvm.selectedCategory,
                        selectedItem = mvm.selectedItem,
                        itemFields = mvm.selectedItemFields,
                        itemValues = mvm.selectedItemValues,
                        onDeleteCategory = mvm::deleteCategory,
                        onAddCategory = mvm::addCategory,
                        onAddItem = mvm::addItem,
                        onSaveItemValues = mvm::saveItemValues,
                        onDeleteItem = mvm::deleteItem
                    )
                }
            }
        }
    }
}
