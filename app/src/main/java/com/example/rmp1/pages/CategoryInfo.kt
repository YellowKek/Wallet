package com.example.rmp1.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import com.example.rmp1.database.entity.Category
import com.example.rmp1.database.entity.Item
import com.example.rmp1.navigation.Page

@Composable
fun CategoryInfo(
    modifier: Modifier = Modifier,
    navController: NavController,
    category: Category?,
    items: List<Item>,
    onDeleteCategory: () -> Unit = {},
    onSelectItem: (Item) -> Unit = {},
) {
    category?.let {
        Scaffold(
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 20.dp),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Button(modifier = Modifier
                        .fillMaxWidth(0.47f)
                        .height(50.dp)
                        .padding(15.dp, 0.dp, 0.dp, 0.dp),
                        onClick = { navController.navigate(Page.NEW_ITEM.route) }
                    ) {
                        Text("Добавить объект")
                    }
                    Button(modifier = Modifier
                        .fillMaxWidth(0.89f)
                        .height(50.dp)
                        .padding(0.dp, 0.dp, 15.dp, 0.dp),
                        onClick = {
                            onDeleteCategory()
                            navController.popBackStack()
                        }) {
                        Text("Удалить категорию")
                    }
                }
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                        fontSize = 10.em,
                        text = category.name,
                    )
                }
                LazyColumn {
                    items(items) {
                        ItemCard(it) {
                            onSelectItem(it)
                            navController.navigate(Page.ITEM.route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCard(
    item: Item,
    onSelect: () -> Unit = {},
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(0.dp, 0.dp, 0.dp, 5.dp)
        .clickable {
            onSelect()
        }) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                fontSize = 8.em,
                text = item.name,
            )
        }
    }
}

