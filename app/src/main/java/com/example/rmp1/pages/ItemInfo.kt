package com.example.rmp1.pages

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.rmp1.database.entity.Field
import com.example.rmp1.database.entity.Item
import com.example.rmp1.database.entity.Value

@Composable
fun ItemInfo(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    item: Item?,
    categoryFields: List<Field>,
    itemValues: List<Value>,
    onSaveItemValues: (List<Value>) -> Unit = {},
    onDeleteItem: () -> Unit = {}
) {
    item.let {
        Card(modifier = modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = it!!.name)
            }

            val modifiedValues = remember { mutableStateListOf<Value>() }

            LazyColumn {
                items(categoryFields) { field ->
                    Row {
                        val valIndex = itemValues.indexOfFirst { it.fieldId == field.id }
                        if (valIndex != -1) {
                            val v = itemValues[valIndex]
                            Text("${field.name}:")
                            ValueTextField(v) {
                                modifiedValues += Value(v.id, v.itemId, v.fieldId, it)
                            }
                        }
                    }
                }
            }
            Button(
                modifier = Modifier
                    .width(120.dp)
                    .height(60.dp)
                    .padding(0.dp, 10.dp),
                onClick = {
                    onSaveItemValues(modifiedValues)
                    navController.popBackStack()
                }
            ) {
                Text("Сохранить")
            }
            Button(
                modifier = Modifier
                    .width(120.dp)
                    .height(60.dp)
                    .padding(0.dp, 10.dp),
                onClick = {
                    onDeleteItem()
                    navController.popBackStack()
                }
            ) {
                Text("Удалить")
            }
        }
    }
}

@Composable
fun ValueTextField(value: Value, onValueChange: (String) -> Unit) {
    var newValueState by remember { mutableStateOf(value.value) }

    OutlinedTextField(
        value = newValueState,
        onValueChange = {
            newValueState = it
            onValueChange(newValueState)
        },
    )
}
