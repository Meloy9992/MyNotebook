package com.example.mynotebook.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mynotebook.adapter.ListNote;

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

    public void insertToDb(String title, String description, String uri) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TITLE, title);
        contentValues.put(Constants.DESCRIPTION, description);
        contentValues.put(Constants.URI, uri);
        database.insert(Constants.TABLE_NAME, null, contentValues);
    }

    public List<ListNote> getFromDbTitle() {
        List<ListNote> listTitle = new ArrayList<>();
        Cursor cursor = database.query(Constants.TABLE_NAME, null, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            ListNote note = new ListNote();
            String title = cursor.getString(cursor.getColumnIndex(Constants.TITLE));
            String desc = cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION));
            String uri = cursor.getString(cursor.getColumnIndex(Constants.URI));
            note.setTitle(title);
            note.setDescription(desc);
            note.setUri(uri);
            listTitle.add(note);
        }
        cursor.close();
        return listTitle;
    }

    public void closeDb() {
        helper.close();
    }
}
