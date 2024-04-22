package com.example.rmp1.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.rmp1.R
import com.example.rmp1.database.entity.Category
import com.example.rmp1.database.entity.Item
import com.example.rmp1.navigation.Page

@Composable
fun Main(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isCategorySelected: Boolean,
    newItem: String,
    categories: List<Category>,
    onSelectCategory: (Category) -> Unit = {},
    onSelectItem: (Item) -> Unit = {},
    onItemChange: (String) -> Unit = {},
    onAddItem: () -> Unit = {},
    onDeleteCategory: () -> Unit = {},
    items: List<Item>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(modifier = modifier) {
            Column(modifier = Modifier.padding(it)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 20.dp)
                ) {
                    LazyRow {
                        items(categories) {
                            CategoryInfo(it) { onSelectCategory(it) }
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(50.dp)
                            .padding(0.dp, 0.dp, 0.dp, 10.dp),
                        onClick = { navController.navigate(Page.NEW_CATEGORY.route) }
                    ) {
                        Text("Добавить")
                    }
                    if (isCategorySelected) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(0.dp, 0.dp, 0.dp, 10.dp),
                            onClick = onDeleteCategory
                        ) {
                            Text("Удалить")
                        }
                    }
                }

                LazyColumn {
                    items(items) {
                        Button(onClick = {
                            onSelectItem(it)
                            navController.navigate(Page.ITEM.route)
                        }) {
                            Text(text = it.name)
                        }
                    }
                }
                if (isCategorySelected) {
                    Row(
                        Modifier.fillMaxWidth(),
                    ) {
                        OutlinedTextField(
                            value = newItem,
                            onValueChange = onItemChange,
                            placeholder = { Text("Объект") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp, 0.dp, 0.dp, 15.dp)
                        )
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.15f)
                            .fillMaxHeight(0.08f)
                            .align(Alignment.CenterHorizontally),
                        onClick = onAddItem
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryInfo(
    category: Category,
    modifier: Modifier = Modifier,
    onSelect: () -> Unit = {},
) {
    Card(
        modifier = modifier,
    ) {
        Column(modifier = Modifier
            .padding(8.dp)
            .clickable {
                onSelect()
            }) {
            Text(text = category.name)
        }
    }
}
