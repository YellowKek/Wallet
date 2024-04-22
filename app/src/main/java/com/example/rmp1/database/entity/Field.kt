package com.example.rmp1.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "fields",
    indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Field(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "category_id") var categoryId: Long,
    @ColumnInfo(name = "field_name") var fieldName: String,
)
