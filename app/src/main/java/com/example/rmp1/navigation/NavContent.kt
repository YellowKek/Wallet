package com.example.rmp1.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rmp1.database.Category
import com.example.rmp1.database.Item
import com.example.rmp1.pages.Main
import com.example.rmp1.pages.NewCategory

@Composable
fun NavContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    categories: List<Category>,
    canAdd: Boolean = false,
    onSelectCategory: (Category) -> Unit = {},
    items: List<Item>,
    newCategory: String,
    newItem: String,
    onCategoryChange: (String) -> Unit = {},
    onAddCategory: () -> Unit = {},
    onAddItem: () -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = Page.MAIN.route,
        modifier = modifier,
    ) {
        composable(Page.MAIN.route) {
            Main(
                Modifier.fillMaxSize(),
                navController,
                categories,
                onSelectCategory,
                items,
            )
        }
        composable(Page.NEW_CATEGORY.route) {
            NewCategory(
                Modifier.fillMaxSize(),
                navController,
                newCategory,
                onCategoryChange,
                onAddCategory
            )
        }
//        composable(Page.ITEM.route) {
//            Item(navController, Modifier.fillMaxSize())
//        }
    }
}