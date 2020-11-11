package com.example.validator

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.number.IntegerWidth
import android.util.Log.d
import android.widget.Toast


val DATABASE_NAME ="MyDB"
val TABLE_NAME="Users"
val TABLEIN_NAME="Inbox"
val COL_NAME = "name"
val COL_AGE = "age"
val COL_ID = "id"
val COL_URL = "imageurl"

val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
        COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
        COL_NAME + " VARCHAR(256)," +
        COL_AGE + " INTEGER," +
        COL_URL + " VARCHAR(256))"

val dropTable = "DROP TABLE IF EXISTS " + TABLE_NAME

val createTableIn = "CREATE TABLE " + TABLEIN_NAME + " (" +
        COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
        COL_NAME + " VARCHAR(256)," +
        COL_AGE + " INTEGER," +
        COL_URL + " VARCHAR(256))"

val dropTableIn = "DROP TABLE IF EXISTS " + TABLEIN_NAME

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,4) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTableIn)
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?,oldVersion: Int,newVersion: Int) {
        db?.execSQL(dropTable)
        db?.execSQL(dropTableIn)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insertData(user : User){ // add row into the outbox row
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, user.name)
        cv.put(COL_AGE, user.age)
        cv.put(COL_URL, user.imageurl)
        val result = db.insert(TABLE_NAME,null,cv)
        if(result == -1.toLong())
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
    }


    fun insertInData(user : User){  // add row into the inbox rows
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, user.name)
        cv.put(COL_AGE, user.age)
        cv.put(COL_URL, user.imageurl)
        val result = db.insert(TABLEIN_NAME,null,cv)
        if(result == -1.toLong())
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
    }

    fun readData() : MutableList<User>{
        val list : MutableList<User> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                val user = User()
                user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.name = result.getString(result.getColumnIndex(COL_NAME))
                user.age = result.getString(result.getColumnIndex(COL_AGE)).toInt()
                user.imageurl = result.getString(result.getColumnIndex(COL_URL))
                list.add(user)
            }while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }


    fun readInData() : MutableList<User>{
        val list : MutableList<User> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLEIN_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                val user = User()
                user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.name = result.getString(result.getColumnIndex(COL_NAME))
                user.age = result.getString(result.getColumnIndex(COL_AGE)).toInt()
                user.imageurl = result.getString(result.getColumnIndex(COL_URL))
                list.add(user)
            }while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }


    fun deleteData(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME,null,null)
        db.delete(TABLEIN_NAME,null,null)
        db.close()
    }


    fun updateData() {
        val db = this.writableDatabase
        db?.execSQL(dropTable)
        db?.execSQL(dropTableIn)
        onCreate(db)
    }

    fun deleteInRow(id: String) {
        val db = this.writableDatabase
        d("db111", "I am in")
        db.delete(TABLEIN_NAME, "$COL_ID=?", arrayOf(id))
    }

}