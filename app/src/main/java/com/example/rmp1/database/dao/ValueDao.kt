package com.example.rmp1.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rmp1.database.entity.Value
import kotlinx.coroutines.flow.Flow

@Dao
interface ValueDao {

    @Insert
    fun insert(value: Value)

    @Insert
    fun insertAll(vararg values: Value)

    @Query("select * from `values` where item_id = :itemId")
    fun getByItem(itemId: Long): Flow<Value>

    @Update
    fun update(value: Value)
}