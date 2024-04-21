package com.example.rmp1.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, 1) {
    companion object {
        const val DB_NAME = "RMP"
        const val TBL_CATEGORY = "category"
        const val TBL_FIELDS = "fields"
        const val TBL_OBJECT = "objects"
        const val TBL_VALUES = "tbl_values"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let { db ->
            db.beginTransaction()
            try {
                try {
                    db.execSQL(
                        "CREATE TABLE $TBL_CATEGORY (" +
                                "id   INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, " +
                                "category_name TEXT    NOT NULL );"

                    )
                    db.execSQL(
                        "CREATE TABLE $TBL_FIELDS (" +
                                "id          INTEGER PRIMARY KEY UNIQUE NOT NULL," +
                                "category_id INTEGER REFERENCES category (id) NOT NULL," +
                                "field_name  TEXT    NOT NULL );"

                    )
                    db.execSQL(
                        "CREATE TABLE $TBL_OBJECT (" +
                                "id          INTEGER PRIMARY KEY UNIQUE NOT NULL," +
                                "category_id INTEGER REFERENCES category (id) NOT NULL," +
                                "object_name        INTEGER NOT NULL );"
                    )
                    db.execSQL(
                        "CREATE TABLE $TBL_VALUES (" +
                                "id        INTEGER PRIMARY KEY UNIQUE NOT NULL," +
                                " object_id INTEGER REFERENCES object (id) NOT NULL," +
                                "field_id  INTEGER REFERENCES category_fields (id) NOT NULL," +
                                "value     TEXT    NOT NULL );"
                    )
                    addCategory("TEST", db)
                    db.setTransactionSuccessful()
                } finally {
                    db.endTransaction()
                }
                addCategory("Рубль", db)
            } catch (e: Throwable) {
                Log.e("DB_ERROR", e.message.toString())
            }
        }
    }


    fun addCategory(categoryName: String, database: SQLiteDatabase? = null) {
        with(database ?: writableDatabase) {
            beginTransaction()
            val values = ContentValues()
            values.put("category_name", categoryName)
            try {
                insert(TBL_CATEGORY, "", values)
                setTransactionSuccessful()
            } catch (_: Throwable) {

            } finally {
                endTransaction()
            }
        }
    }

    fun getAllCategories(): List<Category> {
        val categories = mutableListOf<Category>()
        with(readableDatabase) {
            beginTransaction()
            try {
                query(
                    TBL_CATEGORY,
                    arrayOf("id", "category_name"),
                    null,
                    null,
                    null,
                    null,
                    null
                ).apply {
                    while (moveToNext()) {
                        categories.add(
                            Category(
                                getLong(0),
                                getString(1)
                            )
                        )
                    }
                    close()
                }
                setTransactionSuccessful()
                return categories
            } catch (_: Throwable) {
                categories.clear()
                return categories
            } finally {
                endTransaction()
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}