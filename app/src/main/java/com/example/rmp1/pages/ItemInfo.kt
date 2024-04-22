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
import androidx.compose.ui.Modifier
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
    itemFields: List<Field>,
    itemValues: List<Value>,
    onAppendItemValue: (Long, Long, String) -> Unit,
    onSaveItemValues: () -> Unit = {},
    onDeleteItem: () -> Unit = {}
) {
    item.let {
        Card(modifier = modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = it!!.name)
            }
            LazyColumn {
                items(itemFields) { field ->
                    Row {
                        val valIndex = itemValues.indexOfFirst { it.fieldId == field.id }
                        if (valIndex != -1) {
                            Text("${field.name}:")
                            OutlinedTextField(
                                value = itemValues[valIndex].value,
                                onValueChange = {
                                    onAppendItemValue(itemValues[valIndex].id, field.id, it)
                                }
                            )
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
                    onSaveItemValues()
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
