package com.example.rmp1.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavHostController
import com.example.rmp1.R
import com.example.rmp1.database.Category
import com.example.rmp1.database.Field
import com.example.rmp1.database.Item

@Composable
fun NewCategory(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    newCategory: String,
    onCategoryChange: (String) -> Unit = {},
    onAddCategory: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(modifier = modifier) {
            Column(
                modifier = Modifier.padding(it),
            ) {
                Column(modifier = Modifier.fillMaxHeight(0.9f)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Новая категория",
                            fontSize = 11.em,
                            modifier = Modifier
                                .padding(0.dp, 15.dp)
                        )
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                    ) {
                        OutlinedTextField(
                            value = newCategory,
                            onValueChange = onCategoryChange,
                            placeholder = { Text("Название") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp, 0.dp)
                        )
                    }
//                LazyRow {
//                    items(fields) { // TODO fields
////                        CategoryInfo(it) { onSelectCategory(it) }
//                    }
//                }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(60.dp)
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        onAddCategory()
                        navController.popBackStack()
                    }
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
