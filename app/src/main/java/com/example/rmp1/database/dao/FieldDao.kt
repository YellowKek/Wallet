package com.example.rmp1.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.rmp1.database.entity.Field

@Dao
interface FieldDao {
    @Insert
    fun insertAll(vararg fields: Field)
}