package com.example.rmp1.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rmp1.database.entity.Field
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldDao {
    @Insert
    fun insert(field: Field)


    @Insert
    fun insertAll(vararg fields: Field)

    @Query("select * from fields where category_id = :id")
    fun getByCategory(id: Long): Flow<List<Field>>
}