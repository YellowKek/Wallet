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
        const val TBL_ITEMS = "items"
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
                                "category_id INTEGER REFERENCES $TBL_CATEGORY (id) NOT NULL," +
                                "field_name  TEXT    NOT NULL );"

                    )
                    db.execSQL(
                        "CREATE TABLE $TBL_ITEMS (" +
                                "id          INTEGER PRIMARY KEY UNIQUE NOT NULL," +
                                "category_id INTEGER REFERENCES $TBL_CATEGORY (id) NOT NULL," +
                                "item_name        INTEGER NOT NULL );"
                    )
                    db.execSQL(
                        "CREATE TABLE $TBL_VALUES (" +
                                "id        INTEGER PRIMARY KEY UNIQUE NOT NULL," +
                                " object_id INTEGER REFERENCES $TBL_ITEMS (id) NOT NULL," +
                                "field_id  INTEGER REFERENCES $TBL_FIELDS (id) NOT NULL," +
                                "value     TEXT    NOT NULL );"
                    )
                    db.setTransactionSuccessful()
                } finally {
                    db.endTransaction()
                }
            } catch (e: Throwable) {
                Log.e("DB_ERROR", e.message.toString())
            }
        }
    }

    fun addItem(itemName: String, categoryId: Long, database: SQLiteDatabase? = null) {
        with(database ?: writableDatabase) {
            beginTransaction()
            val values = ContentValues()
            values.put("category_id", categoryId)
            values.put("item_name", itemName)
            try {
                insert(
                    TBL_ITEMS, "", values
                )
                setTransactionSuccessful()
            } catch (_: Throwable) {
            } finally {
                endTransaction()
            }
        }
    }


    fun addCategory(categoryName: String) {
        with(writableDatabase) {
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

    fun getItemsByCategory(categoryId: Long): List<Item> {
        val items = mutableListOf<Item>()
        with(readableDatabase) {
            beginTransaction()
            try {
                query(
                    TBL_ITEMS,
                    arrayOf("id", "category_id", "object_name"),
                    "category_id = ?",
                    arrayOf(categoryId.toString()),
                    null,
                    null,
                    null
                ).apply {
                    while (moveToNext()) {
                        items.add(
                            Item(
                                getLong(0),
                                getLong(1),
                                getString(2)
                            )
                        )
                    }
                    close()
                }
                setTransactionSuccessful()
                return items
            } catch (_: Throwable) {
                items.clear()
                return items
            } finally {
                endTransaction()
            }
        }
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}