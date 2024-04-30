package com.example.rmp1.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavHostController
import com.example.rmp1.database.entity.Field
import com.example.rmp1.database.entity.Item
import com.example.rmp1.database.entity.Value
import com.example.rmp1.navigation.Page

@Composable
fun ItemInfo(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    item: Item?,
    categoryFields: List<Field>,
    itemValues: List<Value>,
    onSaveItemValues: (List<Value>) -> Unit,
    onDeleteItem: () -> Unit = {}
) {
    val modifiedValues = remember { mutableStateListOf<Value>() }

    item?.let {
        Scaffold(modifier = modifier,
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
                        onClick = {
                            onSaveItemValues(modifiedValues)
                            navController.popBackStack()
                        }
                    ) {
                        Text("Сохранить")
                    }
                    Button(modifier = Modifier
                        .fillMaxWidth(0.89f)
                        .height(50.dp)
                        .padding(0.dp, 0.dp, 15.dp, 0.dp),
                        onClick = {
                            onDeleteItem()
                            navController.popBackStack()
                        }
                    ) {
                        Text("Удалить")
                    }
                }
            }) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.name,
                        fontSize = 11.em,
                    )
                }

                LazyColumn {
                    items(categoryFields) { field ->
                        Row(
                            modifier = Modifier.padding(bottom = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Absolute.Right
                        ) {
                            val valIndex = itemValues.indexOfFirst { it.fieldId == field.id }
                            if (valIndex != -1) {
                                val v = itemValues[valIndex]
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
                                ValueTextField(v) {
                                    modifiedValues += Value(v.id, v.itemId, v.fieldId, it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ValueTextField(value: Value, onValueChange: (String) -> Unit) {
    var newValueState by remember { mutableStateOf(value.value) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 15.dp),
        horizontalAlignment = AbsoluteAlignment.Right
    ) {
        OutlinedTextField(
            value = newValueState,
            onValueChange = {
                newValueState = it
                onValueChange(newValueState)
            },
            textStyle = TextStyle(fontSize = 5.em),
            placeholder = { Text("Значение") },
            modifier = Modifier

        )
    }
}
