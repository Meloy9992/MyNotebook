package com.example.mynotebook.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Helper extends SQLiteOpenHelper {
    public Helper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
