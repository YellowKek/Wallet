package com.example.rmp1

import android.os.Bundle
import androidx.compose.material3.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rmp1.database.entity.Category
import com.example.rmp1.database.entity.Item
import com.example.rmp1.ui.theme.RMP1Theme

class MainActivity : ComponentActivity() {
    val mvm: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RMP1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainUI(
                        categories = mvm.categories,
                        mvm.selectedCategory != null,
                        mvm::selectCategory,
                        items = mvm.items,
                        mvm.newItem,
                        mvm::addItem,
                        { mvm.newItem = it }
                    )
                }
            }
        }
    }
}

@Composable
fun MainUI(
    categories: List<Category>,
    canAdd: Boolean = false,
    onSelectCategory: (Category) -> Unit = {},
    items: List<Item>,
    newItem: String,
    onAddItem: () -> Unit = {},
    onNewStudChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier) {
        Column(modifier = Modifier.padding(it)) {
            LazyRow {
                items(categories) {
                    CategoryInfo(it) { onSelectCategory(it) }
                }
            }
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = newItem,
                    onValueChange = onNewStudChange,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp, 0.dp)
                )
                Button(
                    onClick = onAddItem,
                    modifier = Modifier.weight(0.3f),
                    enabled = canAdd
                ) {

                }
            }
            LazyColumn {
                items(items) {
                    ItemInfo(it)
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