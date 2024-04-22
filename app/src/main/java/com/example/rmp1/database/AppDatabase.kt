package com.example.rmp1.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rmp1.database.dao.CategoryDao
import com.example.rmp1.database.dao.FieldDao
import com.example.rmp1.database.dao.ItemDao
import com.example.rmp1.database.dao.ValueDao
import com.example.rmp1.database.entity.Category
import com.example.rmp1.database.entity.Field
import com.example.rmp1.database.entity.Item
import com.example.rmp1.database.entity.Value

@Database(
    version = 1,
    entities = [
        Category::class,
        Item::class,
        Field::class,
        Value::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getFieldDao(): FieldDao
    abstract fun getItemDao(): ItemDao
    abstract fun getValueDao(): ValueDao

}