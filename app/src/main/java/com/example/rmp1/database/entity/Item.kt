package com.example.rmp1.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "items",
    indices = [Index("id")],
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["category_id"]
    )]
)
data class Item(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "category_id") var categoryId: Long,
    @ColumnInfo(name = "item_name") var name: String,
)
