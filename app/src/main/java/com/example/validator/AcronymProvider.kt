package com.example.validator

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class AcronymProvider : ContentProvider() {

    companion object{
        val PROVIDER_NAME = "com.example.collector/AcronymProvider"
        val URL = "content://$PROVIDER_NAME/CPInbox"
        val CONTENT_URI = Uri.parse(URL)

        val TABLEIN_NAME="CPInbox"
        val COL_NAME = "name"
        val COL_TASK_ID = "task_id"
        val COL_TASK_DESCRIPTION = "task_description"
        val COL_URL = "profileurl"
        val COL_STAGE = "current_stage"
        val COL_AGE = "age"
        val COL_PROFILE = "profileimg"
        val COL_IMAGE_BIT = "picturetaken"
        val COL_MODEL = "inf_model"
        val COL_ITEM = "item_of_interest"
        val COL_COUNT = "item_count"
        val COL_TEXT = "item_text"
    }
    lateinit var db : SQLiteDatabase
    var helper: DataBaseHandler? = null

    override fun onCreate(): Boolean {
        helper = getContext()?.let { DataBaseHandler(it) }
        if (helper != null) {
            db = helper!!.writableDatabase
        }
        return db != null
    }


    override fun insert(uri: Uri, cv: ContentValues?): Uri? {
        db.insert(TABLEIN_NAME, null, cv)
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }


    override fun update(uri: Uri, cv: ContentValues?, condition: String?, condition_val: Array<out String>?): Int {
        var count = db.update(TABLEIN_NAME, cv, condition, condition_val)
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }


    override fun delete(uri: Uri, condition: String?, condition_val: Array<out String>?): Int {
        var count = db.delete(TABLEIN_NAME, condition, condition_val)
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }


    override fun query(uri: Uri, cols: Array<out String>?, condition: String?, condition_val: Array<out String>?, order: String?): Cursor? {
        return db.query(TABLEIN_NAME, cols, condition, condition_val, null, null, order)
    }


    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.dir/vnd.example.inbox"
    }

    fun resetDatabase() {
        helper?.close()
        helper = context?.let { DataBaseHandler(it) }
    }

    fun clearTable() {
        helper!!.getWritableDatabase().delete(TABLEIN_NAME, null, null)
    }

}
