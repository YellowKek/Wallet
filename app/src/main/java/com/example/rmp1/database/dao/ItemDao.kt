package com.example.rmp1.database.dao

import com.example.rmp1.database.entity.Item
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDao {

    @Query("select * from items")
    fun getAll(): List<Item>

    @Query("select * from items where category_id = :categoryId")
    fun getByCategory(categoryId: Long): List<Item>

    @Insert
    fun insert(item: Item): Long

    @Delete
    fun delete(item: Item)

    @Update
    fun update(item: Item)
}