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
import com.example.rmp1.pages.ItemInfo
import com.example.rmp1.pages.Main
import com.example.rmp1.pages.NewCategory

@Composable
fun NavContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    categories: List<Category>,
    isCategorySelected: Boolean = false,
    onSelectCategory: (Category) -> Unit = {},
    onSelectItem: (Item) -> Unit = {},
    items: List<Item>,
    newCategoryFields: List<Field>,
    selectedItem: Item?,
    newCategory: String,
    newItem: String,
    newFieldName: String,
    itemFields: List<Field>,
    itemValues: List<Value>,
    onItemChange: (String) -> Unit = {},
    onCategoryChange: (String) -> Unit = {},
    onFieldChange: (String) -> Unit = {},
    onDeleteCategory: () -> Unit = {},
    onAddCategory: () -> Unit = {},
    onAddItem: () -> Unit = {},
    onAppendField: () -> Unit = {},
    onSaveItemValues: (List<Value>) -> Unit = {},
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
                isCategorySelected,
                newItem,
                categories,
                onSelectCategory,
                onSelectItem,
                onItemChange,
                onAddItem,
                onDeleteCategory,
                items,
            )
        }
        composable(Page.NEW_CATEGORY.route) {
            NewCategory(
                Modifier.fillMaxSize(),
                navController,
                newCategory,
                newFieldName,
                newCategoryFields,
                onCategoryChange,
                onFieldChange,
                onAddCategory,
                onAppendField
            )
        }
        composable(Page.ITEM.route) {
            ItemInfo(
                Modifier.fillMaxSize(),
                navController,
                selectedItem,
                itemFields,
                itemValues,
                onSaveItemValues,
                onDeleteItem
            )
        }
    }
}