package com.example.noteappsqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class databasehelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notedata.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "notedata"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_NOTE = "note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_NOTE TEXT
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addNote(title: String, note: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_NOTE, note)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    fun deleteNote(id: Int): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    fun getNotes(): List<notedata> {
        val notesList = mutableListOf<notedata>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, "$COLUMN_ID DESC")

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID))
                val title = it.getString(it.getColumnIndexOrThrow(COLUMN_TITLE))
                val note = it.getString(it.getColumnIndexOrThrow(COLUMN_NOTE))
                notesList.add(notedata(id, title, note))
            }
        }
        db.close()
        return notesList
    }

    fun updateNote(id: Int, title: String, note: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_NOTE, note)
        }
        val result = db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }
}
