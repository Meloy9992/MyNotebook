package com.example.mynotebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mynotebook.dataBase.Manager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditActivity extends AppCompatActivity {
    private EditText editTitle, editDescription;
    private Manager manager;
    private ImageView newImage;
    private ConstraintLayout addNewImage;
    private FloatingActionButton fbAddImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        manager.openDb();
    }

    private void init() {
        editTitle = findViewById(R.id.Title);
        editDescription = findViewById(R.id.Description);
        fbAddImage = findViewById(R.id.AddImage);
        addNewImage = findViewById(R.id.conteinerAddImage);
        manager = new Manager(this);
    }

    // ДЕЙСТВИЯ ПРИ НАЖАТИИ НА СОХРАНЕНИЕ
    public void onClickSave(View view) {
        String title = editTitle.getText().toString(); //ПОЛУЧЕНИЕ ТЕКСТА ИЗ ЗАГОЛОВКА
        String description = editDescription.getText().toString(); // ПОЛУЧЕНИЕ ТЕКСТА ИЗ ОПИСАНИЯ
        if (title.equals("") || description.equals("")) { // ПРОВЕРКА НА ПУСТЫЕ СТРОКИ
            Toast.makeText(this, R.string.EmptyTitleOrDescription, Toast.LENGTH_SHORT).show();
        } else {
            manager.insertToDb(title, description); // ВСТАВЛЯЕМ В БАЗУ ДАННЫХ ЗАГОЛОВОК И ОПИСАНИЕ
            Toast.makeText(this, R.string.Saved, Toast.LENGTH_SHORT).show();
            finish(); //ЗАКРЫТИЕ АКТИВНОСТИ
            manager.closeDb(); // ЗАКРЫТИЕ БАЗЫ ДАННЫХ
        }
    }

    // ДЕЙСТВИЯ ПРИ НАЖАТИИ НА УДАЛЕНИЕ ИЗОБРАЖЕНИЯ
    public void onClickDeleteImage(View view) {
        addNewImage.setVisibility(View.GONE);
        fbAddImage.setVisibility(View.VISIBLE);
    }

    // ДЕЙСТВИЯ ПРИ НАЖАТИИ НА ДОБАВЛЕНИЕ НОВОЙ КАРТИНКИ
    public void onClickAddNewImage(View view) {
        addNewImage.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
    }
}
