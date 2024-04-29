package com.example.rmp1.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rmp1.database.entity.Category
import com.example.rmp1.database.entity.Field
import com.example.rmp1.database.entity.Item
import com.example.rmp1.database.entity.Value
import com.example.rmp1.pages.CategoryInfo
import com.example.rmp1.pages.ItemInfo
import com.example.rmp1.pages.Main
import com.example.rmp1.pages.NewCategory
//import kotlin.reflect.KFunction1
import com.example.rmp1.pages.NewItem

@Composable
fun NavContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    categories: List<Category>,
    onSelectCategory: (Category) -> Unit = {},
    onSelectItem: (Item) -> Unit,
    items: List<Item>,
    selectedCategory: Category?,
    selectedItem: Item?,
    categoryFields: List<Field>,
    itemValues: List<Value>,
    onDeleteCategory: () -> Unit = {},
    onAddCategory: (String, List<String>) -> Unit,
    onAddItem: (String, Array<String>) -> Boolean,
    onSaveItemValues: (List<Value>) -> Unit,
    onDeleteItem: () -> Unit = {}
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
            )
        }
        composable(Page.NEW_CATEGORY.route) {
            NewCategory(
                Modifier.fillMaxSize(),
                navController,
                onAddCategory,
            )
        }
        composable(Page.CATEGORY.route) {
            CategoryInfo(
                Modifier.fillMaxSize(),
                navController,
                selectedCategory,
                items,
                onDeleteCategory,
                onSelectItem,
            )
        }
        composable(Page.ITEM.route) {
            ItemInfo(
                Modifier.fillMaxSize(),
                navController,
                selectedItem,
                categoryFields,
                itemValues,
                onSaveItemValues,
                onDeleteItem
            )
        }
        composable(Page.NEW_ITEM.route) {
            NewItem(
                Modifier.fillMaxSize(),
                navController,
                categoryFields,
                onAddItem,
            )
        }
    }
}