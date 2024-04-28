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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavHostController
import com.example.rmp1.R
import com.example.rmp1.database.entity.Category
import com.example.rmp1.database.entity.Item
import com.example.rmp1.navigation.Page

@Composable
fun Main(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    categories: List<Category>,
    onSelectCategory: (Category) -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(0.dp, 10.dp),
                fontSize = 10.em,
                text = "Категории",
            )
        }
        Scaffold(modifier = modifier) {
            Column(modifier = Modifier.padding(it)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 20.dp)
                ) {
                    LazyColumn {
                        items(categories) {
                            CategoryCard(it) {
                                onSelectCategory(it)
                                navController.navigate(Page.CATEGORY.route)
                            }
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
                        onClick = { navController.navigate(Page.NEW_CATEGORY.route) }
                    ) {
                        Text("Добавить")
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: Category,
    modifier: Modifier = Modifier,
    onSelect: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                onSelect()
            }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                fontSize = 10.em,
                text = category.name
            )
        }
    }
}
