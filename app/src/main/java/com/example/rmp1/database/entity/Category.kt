package com.example.rmp1.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    indices = [Index("id")]
)
data class Category(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "category_name") var name: String,
)
