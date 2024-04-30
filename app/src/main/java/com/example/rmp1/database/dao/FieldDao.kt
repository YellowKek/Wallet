package com.example.rmp1.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rmp1.database.entity.Field
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Dao
interface FieldDao {
    @Insert
    fun insert(field: Field)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg fields: Field)

    @Query("insert into fields (category_id, field_name) values (:categoryId, :name)")
    fun insert(categoryId: Long, name: String): Long

    @Query("select * from fields where category_id = :id")
    fun getByCategory(id: Long): Flow<Field>
}