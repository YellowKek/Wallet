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

    @Insert
    fun insert(item: Item)

    @Delete
    fun delete(item: Item)

    @Update
    fun update(item: Item)
}