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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import com.example.rmp1.database.entity.Category
import com.example.rmp1.database.entity.Item
import com.example.rmp1.navigation.Page
//import kotlin.reflect.KFunction1

@Composable
fun CategoryInfo(
    modifier: Modifier = Modifier,
    navController: NavController,
    category: Category?,
    items: List<Item>,
    onDeleteCategory: () -> Unit = {},
    onSelectItem: (Item) -> Unit = {},
    onAddItem: (String) -> Unit = {},
) {
    var newItem by remember { mutableStateOf("") }

    category?.let {
        Column {
            Column(modifier = Modifier.fillMaxHeight(0.9f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(0.dp, 20.dp),
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(50.dp),
                        onClick = { navController.navigate(Page.NEW_ITEM.route) }
                    ) {
                        Text("Добавить")
                    }
                }
//                Button(
//                    modifier = Modifier
//                        .fillMaxWidth(0.6f)
//                        .height(60.dp)
//                        .align(Alignment.CenterHorizontally),
//                    onClick = {
//                        onAddItem(newItem)
//                        newItem = ""
//                    }
//                ) {
//                    Text("Добавить")
//                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(60.dp),
                    onClick = {
                        onDeleteCategory()
                        navController.popBackStack()
                    }
                ) {
                    Text("Удалить категорию")
                }
            }
        }
    }
}

@Composable
fun ItemCard(
    item: Item,
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
            Text(text = item.name)
        }
    }
}

