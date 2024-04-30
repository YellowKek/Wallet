package com.example.rmp1.database.dao

import com.example.rmp1.database.entity.Item
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("select * from items where category_id = :categoryId")
    fun getByCategory(categoryId: Long): Flow<List<Item>>

    @Query("insert into items (category_id, item_name) values (:categoryId, :name)")
    fun insert(categoryId: Long, name: String): Long

    @Delete
    fun delete(item: Item)

    @Update
    fun update(item: Item)
}