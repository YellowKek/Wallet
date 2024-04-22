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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavContent(
                        navController = navController,
                        categories = mvm.categories,
                        canAdd = mvm.selectedCategory != null,
                        onSelectCategory = mvm::selectCategory,
                        items = mvm.items,
                        newCategory = mvm.newCategory,
                        newItem = mvm.newItem,
                        onCategoryChange = { mvm.newCategory = it },
                        onAddCategory = mvm::addCategory,
                        onAddItem = mvm::addItem
                    )
                }
            }
        }
    }
}
