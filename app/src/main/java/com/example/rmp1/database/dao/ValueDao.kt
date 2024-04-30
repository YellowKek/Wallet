package com.example.rmp1.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rmp1.database.entity.Value
import kotlinx.coroutines.flow.Flow

@Dao
interface ValueDao {

    @Query("insert into `values` (item_id, field_id, value) values (:itemId, :fieldId, :value)")
    fun insert(itemId: Long, fieldId: Long, value: String)

    @Query("select * from `values` where item_id = :itemId")
    fun getByItem(itemId: Long): Flow<List<Value>>

    @Update
    fun update(value: Value)
}