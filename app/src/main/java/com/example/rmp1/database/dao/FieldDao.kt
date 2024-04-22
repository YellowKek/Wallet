package com.example.rmp1.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rmp1.database.entity.Field

@Dao
interface FieldDao {
    @Insert
    fun insert(field: Field)

    @Query("update fields set field_name = :newName where id = :fieldId")
    fun rename(fieldId: Long, newName: String)

    @Insert
    fun insertAll(vararg fields: Field)
}