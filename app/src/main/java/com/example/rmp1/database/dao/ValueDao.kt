package com.example.rmp1.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.example.rmp1.database.entity.Value

@Dao
interface ValueDao {

    @Insert
    fun insert(value: Value)

    @Update
    fun update(value: Value)
}