package com.example.rmp1.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rmp1.database.entity.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface CategoryDao {

    @Query("select * from categories")
    fun getAll(): Flow<List<Category>>

    @Query("select * from categories where name = :name limit 1")
    fun getByName(name: String): Flow<Category>

    @Query("insert into categories (name) values (:name)")
    fun insert(name: String): Long

    @Delete
    fun delete(category: Category)

    @Update
    fun update(category: Category)

    @Query("update categories set name = :newName where id = :categoryId")
    fun rename(categoryId: Long, newName: String)

    @Query("delete from fields where category_id = :categoryId and field_name = :fieldName")
    fun deleteField(categoryId: Long, fieldName: String)
}