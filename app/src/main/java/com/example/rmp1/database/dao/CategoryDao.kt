package com.example.rmp1.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rmp1.database.entity.Category

@Dao
interface CategoryDao {

    @Query("select * from categories")
    fun getAll(): List<Category>

    @Query("select * from categories where categoryName = :name limit 1")
    fun getByName(name: String): Category

    @Insert
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)

    @Update
    fun update(category: Category)
}