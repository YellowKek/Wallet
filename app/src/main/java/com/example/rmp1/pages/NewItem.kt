package com.example.rmp1.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
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
        Scaffold(
            modifier = modifier,
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.Absolute.Center
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(50.dp),
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
                }
            }
        ) {
            Column(
                modifier = Modifier.padding(it),
            ) {
                Column {
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
                                .fillMaxWidth()
                                .padding(8.dp, 20.dp)
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
                                    .padding(bottom = 20.dp)
                            )
                        }
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp)
                    ) {
                        itemsIndexed(categoryFields) { index, field ->
                            var newValue by remember { mutableStateOf(itemValues[index]) }

                            Row(
                                modifier = Modifier.padding(bottom = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Absolute.Right
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(0.25f)
                                        .padding(end = 8.dp),
                                    horizontalAlignment = AbsoluteAlignment.Right
                                ) {
                                    Text(
                                        text = "${field.name}:",
                                        fontSize = 6.em,
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 15.dp),
                                    horizontalAlignment = AbsoluteAlignment.Right
                                ) {
                                    OutlinedTextField(
                                        value = newValue,
                                        onValueChange = {
                                            newValue = it
                                            itemValues[index] = newValue
                                        },
                                        textStyle = TextStyle(fontSize = 5.em),
                                        placeholder = { Text("Значение") },
                                        modifier = Modifier

                                    )
                                }
                            }
                        }
                    }
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
