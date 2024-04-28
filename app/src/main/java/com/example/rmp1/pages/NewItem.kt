package com.example.rmp1.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.rmp1.database.entity.Field

@Composable
fun NewItem(
    modifier: Modifier,
    navController: NavHostController,
    categoryFields: List<Field>,
    onAddItem: (String, Array<String>) -> Boolean
) {
    val openDialog = remember { mutableStateOf("") }

    var newItem by remember { mutableStateOf("") }
    val itemValues by remember { mutableStateOf(Array(categoryFields.size) { "" }) }

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
                            text = "Новый объект",
                            fontSize = 11.em,
                            modifier = Modifier
                                .padding(0.dp, 15.dp)
                        )
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                    ) {
                        OutlinedTextField(
                            value = newItem,
                            onValueChange = { newItem = it },
                            placeholder = { Text("Название") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp, 0.dp, 0.dp, 20.dp)
                        )
                    }
                    if (categoryFields.isNotEmpty()) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Значения",
                                fontSize = 9.em,
                                modifier = Modifier
                                    .padding(0.dp, 10.dp)
                            )
                        }
                    }
                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp)) {
                        val maxWidth = categoryFields.maxOf { it.name.length }

                        itemsIndexed(categoryFields) { index, field ->
                            var newValue by remember { mutableStateOf(itemValues[index]) }

                            Row(
                                modifier = Modifier.padding(bottom = 10.dp),
                                horizontalArrangement = Arrangement.Absolute.SpaceBetween
                            ) {
                                Text(
                                    text = "${field.name}:",
                                    fontSize = 6.em,
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                )
                                OutlinedTextField(
                                    value = newValue,
                                    onValueChange = {
                                        newValue = it
                                        itemValues[index] = newValue
                                    },
                                    placeholder = { Text("Значение") }
                                )
                            }
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        if (newItem.isEmpty()) {
                            openDialog.value = "Пустое название объекта!"
                        } else {
                            val ok = onAddItem(newItem, itemValues)
                            if (!ok) {
                                openDialog.value = "Объект с таким названием уже есть!"
                            } else {
                                navController.popBackStack()
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
        }
    }
}
