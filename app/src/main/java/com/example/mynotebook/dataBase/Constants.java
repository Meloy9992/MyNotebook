package com.example.mynotebook.dataBase;

import android.provider.BaseColumns;

public class Constants {
    public static final String TABLE_NAME = "my_table";
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String DB_NAME = "notebook.db";
    public static final int DB_VERSION = 1;
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +" (" + _ID + " INTEGER PRIMARY KEY," + TITLE +
    " TEXT," + DESCRIPTION + " TEXT)";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}
