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

    //ВСТАВКА В БАЗУ ДАННЫХ ЗНАЧЕНИЙ
    public void insertToDb(String title, String description, String uri) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TITLE, title);
        contentValues.put(Constants.DESCRIPTION, description);
        contentValues.put(Constants.URI, uri);
        database.insert(Constants.TABLE_NAME, null, contentValues);
    }

    public void deleteFromDb(int id){
        String selection = Constants._ID + "=" + id; // СОЗДАНИЕ ЗАПРОСА НА УДАЛЕНИЕ
        database.delete(Constants.TABLE_NAME, selection, null); // УДАЛЕНИЕ ИЗ БАЗЫ ПО ID
    }

    public List<ListNote> getFromDbTitle(String searchText) {
        List<ListNote> listTitle = new ArrayList<>();
        String selection = Constants.TITLE + " like ?"; // КОМАНДА ДЛЯ БД
        Cursor cursor = database.query(Constants.TABLE_NAME, null, selection,
                new String[]{"%" + searchText + "%"}, null, null, null); // ПОИСК ПО ЗАГОЛОВКУ
        while (cursor.moveToNext()) {
            ListNote note = new ListNote();
            int _id = cursor.getInt(cursor.getColumnIndex(Constants._ID));// ПОЛУЧЕНИЯ id
            String title = cursor.getString(cursor.getColumnIndex(Constants.TITLE));// ПОЛУЧЕНИЕ ЗАГОЛОВКА
            String desc = cursor.getString(cursor.getColumnIndex(Constants.DESCRIPTION));// ПОЛУЧЕНИЕ ОПИСАНИЯ
            String uri = cursor.getString(cursor.getColumnIndex(Constants.URI));// ПОЛУЧЕНИЯ ССЫЛКИ КАРТИНКИ
            note.setTitle(title); // ЗАДАЕМ ЗАГОЛОВОК
            note.setDescription(desc); // ЗАДАЕМ ОПИСАНИЕ
            note.setUri(uri); // ЗАДАЕМ ССЫЛКУ НА КАРТИНКУ
            note.setId(_id); // ЗАДАЕМ ID
            listTitle.add(note); // ДОБАВЛЯЕМ ВСЕ ДАННЫЕ В СПИСОК ВСЕХ ДАННЫХ
        }
        cursor.close(); // ЗАКРЫВАЕМ ДОСТУП К БД
        return listTitle;
    }

    public void closeDb() {
        helper.close();
    }
}
