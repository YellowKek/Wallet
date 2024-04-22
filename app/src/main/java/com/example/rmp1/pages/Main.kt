package com.example.rmp1.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.rmp1.R
import com.example.rmp1.database.Category
import com.example.rmp1.database.Item
import com.example.rmp1.navigation.Page

@Composable
fun Main(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    categories: List<Category>,
    onSelectCategory: (Category) -> Unit = {},
    items: List<Item>,
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(modifier = modifier) {
            Column(modifier = Modifier.padding(it)) {
                LazyRow {
                    items(categories) {
                        CategoryInfo(it) { onSelectCategory(it) }
                    }
                }
                LazyColumn {
                    items(items) {
                        ItemInfo(it)
                    }
                }
                Row(Modifier.width(75.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End) {
                    Button(
                        modifier = Modifier,
                        onClick = { navController.navigate(Page.NEW_CATEGORY.route) }
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
fun ItemInfo(
    item: Item,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = item.itemName)
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
            Text(text = category.categoryName)
        }
    }
}
