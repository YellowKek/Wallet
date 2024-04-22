package com.example.rmp1.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "values",
    indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = Item::class,
            parentColumns = ["id"],
            childColumns = ["item_id"]
        ),
        ForeignKey(
            entity = Field::class,
            parentColumns = ["id"],
            childColumns = ["field_id"]
        )
    ]
)
data class Value(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "item_id") var itemId: Long,
    @ColumnInfo(name = "field_id") var fieldId: Long,
    @ColumnInfo(name = "value") var value: String,
)
