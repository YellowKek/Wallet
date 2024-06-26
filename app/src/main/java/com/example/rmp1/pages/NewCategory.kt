package com.example.rmp1.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavHostController
import com.example.rmp1.R

@Composable
fun NewCategory(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onAddCategory: (String, List<String>) -> Boolean,
) {
    val openDialog = remember { mutableStateOf("") }

    var newCategory by remember { mutableStateOf("") }
    var newFieldName by remember { mutableStateOf("") }
    var newCategoryFields by remember { mutableStateOf(listOf<String>()) }

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
                            onValueChange = { newCategory = it },
                            placeholder = { Text("Название") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp, 0.dp, 0.dp, 20.dp)
                        )
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Поля",
                            fontSize = 9.em,
                            modifier = Modifier
                                .padding(0.dp, 10.dp)
                        )
                    }
                    LazyColumn(modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)) {
                        items(newCategoryFields) { field ->
                            Text(
                                text = "- $field",
                                fontSize = 8.em,
                                modifier = Modifier
                                    .padding(0.dp, 10.dp)
                            )
                        }
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                    ) {
                        OutlinedTextField(
                            value = newFieldName,
                            onValueChange = { newFieldName = it },
                            placeholder = { Text("Поле") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp, 0.dp, 0.dp, 15.dp)
                        )
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(40.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            if (newFieldName.isNotEmpty()) {
                                if (newCategoryFields.contains(newFieldName)) {
                                    openDialog.value = "Поле с таким названием уже есть"
                                } else {
                                    newCategoryFields += newFieldName
                                    newFieldName = ""
                                }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                            contentDescription = null
                        )
                    }
                    if (openDialog.value.isNotEmpty()) {
                        AlertDialog(
                            onDismissRequest = { openDialog.value = "" },
                            title = { Text(text = "Ошибка") },
                            text = { Text(openDialog.value) },
                            confirmButton = {
                                Button({ openDialog.value = "" }) {
                                    Text("OK", fontSize = 5.em)
                                }
                            }
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(60.dp)
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        if (newCategory.isEmpty()) {
                            openDialog.value = "Пустое название категории!"
                        } else {
                            val ok = onAddCategory(newCategory, newCategoryFields)
                            if (!ok) {
                                openDialog.value = "Категория с таким названием уже есть!"
                            } else {
                                navController.popBackStack()
                            }
                        }
                    }
                ) {
                    Text("Добавить", fontSize = 7.em)
                }
                if (openDialog.value.isNotEmpty()) {
                    AlertDialog(
                        onDismissRequest = { openDialog.value = "" },
                        title = { Text(text = "Ошибка") },
                        text = { Text(openDialog.value) },
                        confirmButton = {
                            Button({ openDialog.value = "" }) {
                                Text("OK", fontSize = 5.em)
                            }
                        }
                    )
                }
            }
        }
    }
}
