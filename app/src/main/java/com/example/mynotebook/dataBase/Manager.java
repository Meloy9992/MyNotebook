package com.example.mynotebook.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private Context context;
    private Helper helper;
    private SQLiteDatabase database;

    public Manager(Context context) {
        this.context = context;
        helper = new Helper(context);
    }

    public void openDb() {
        database = helper.getReadableDatabase();
    }

    public void insertToDb(String title, String description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TITLE, title);
        contentValues.put(Constants.DESCRIPTION, description);
        database.insert(Constants.TABLE_NAME, null, contentValues);
    }

    public List<String> getFromDbTitle() {
        List<String> listTitle = new ArrayList<>();
        Cursor cursor = database.query(Constants.TABLE_NAME, null, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(Constants.TITLE));
            listTitle.add(title);
        }
        cursor.close();
        return listTitle;
    }

    public void closeDb() {
        helper.close();
    }
}
