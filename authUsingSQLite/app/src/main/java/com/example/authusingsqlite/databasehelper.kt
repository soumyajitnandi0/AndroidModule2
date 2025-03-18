package com.example.authusingsqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class databasehelper(private val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private val DATABASE_NAME = "UserDatabase.db"
        private val DATABASE_VERSION = 1
        private val TABLE_NAME = "users"
        private val COLUMN_ID = "id"
        private val COLUMN_USERNAME = "username"
        private val COLUMN_PASSWORD = "password"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_USERNAME TEXT, $COLUMN_PASSWORD TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertUser(userName:String,password:String):Long{
        val values = ContentValues().apply {
            put(COLUMN_USERNAME,userName)
            put(COLUMN_PASSWORD,password)
        }
        val db = writableDatabase
        return db.insert(TABLE_NAME,null,values)
    }

    fun readUser(userName: String,password: String):Boolean{
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(userName,password)
        val cursor = db.query(TABLE_NAME,null,selection,selectionArgs,null,null,null)
        val userExists = cursor.count > 0
        cursor.close()
        return userExists
    }
}