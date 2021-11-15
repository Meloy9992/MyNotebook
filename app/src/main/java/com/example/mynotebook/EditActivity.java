package com.example.mynotebook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mynotebook.adapter.ListNote;
import com.example.mynotebook.dataBase.Constants;
import com.example.mynotebook.dataBase.Manager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class EditActivity extends AppCompatActivity {
    final static int PICK_IMAGE_CODE = 1; // КОД ЗАПРОСА ИЗОБРАЖЕНИЯ

    private EditText editTitle, editDescription;
    private Manager manager;
    private ImageView newImage;
    private ConstraintLayout addNewImage;
    private FloatingActionButton fbAddImage;
    private String tempUri = "empty";
    private boolean isEditState = true;
    //private ImageButton imEditImage, imDeleteImage;

    // ПРИ СОЗДАНИИ АКТИВНОСТИ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        getMyIntent();
    }

    // ПРИ ВОЗВРАЩЕНИИ В АКТИВНОСТЬ
    @Override
    public void onResume() {
        super.onResume();
        manager.openDb();
    }

    // ОСНОВНЫЕ КОМАНДЫ
    private void init() {
        editTitle = findViewById(R.id.Title);
        editDescription = findViewById(R.id.Description);
        newImage = findViewById(R.id.newImage);
        fbAddImage = findViewById(R.id.AddImage);
        addNewImage = findViewById(R.id.conteinerAddImage);
        manager = new Manager(this);
    }

    // ПОЛУЧЕНИЕ НАМЕРЕНИЯ
    private void getMyIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            ListNote item = (ListNote) intent.getSerializableExtra(Constants.LIST_ITEM_INTENT);
            isEditState = intent.getBooleanExtra(Constants.EDIT_STATE, true);

            if (!isEditState){
                editTitle.setText(item.getTitle()); // ЗАДАТЬ ЗАГОЛОВОК
                editDescription.setText(item.getDescription()); // ЗАДАТЬ ОПИСАНИЕ
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // ПОЛУЧЕНИЕ ССЫЛКИ НА КАРТИНКУ И УСТАНОВКА ССЫЛКИ В ПЕРЕМЕННУЮ
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE && data != null) {
            tempUri = data.getData().toString();
            newImage.setImageURI(data.getData());
        }
    }

    // ДЕЙСТВИЯ ПРИ НАЖАТИИ НА СОХРАНЕНИЕ
    public void onClickSave(View view) {
        String title = editTitle.getText().toString(); //ПОЛУЧЕНИЕ ТЕКСТА ИЗ ЗАГОЛОВКА
        String description = editDescription.getText().toString(); // ПОЛУЧЕНИЕ ТЕКСТА ИЗ ОПИСАНИЯ
        if (title.equals("") || description.equals("")) { // ПРОВЕРКА НА ПУСТЫЕ СТРОКИ
            Toast.makeText(this, R.string.EmptyTitleOrDescription, Toast.LENGTH_SHORT).show(); // УВЕДОМЛЕНИЕ НА ЭКРАНЕ
        } else {
            manager.insertToDb(title, description, tempUri); // ВСТАВЛЯЕМ В БАЗУ ДАННЫХ ЗАГОЛОВОК И ОПИСАНИЕ
            Toast.makeText(this, R.string.Saved, Toast.LENGTH_SHORT).show(); // УВЕДОМЛЕНИЕ НА ЭКРАНЕ
            finish(); //ЗАКРЫТИЕ АКТИВНОСТИ
            manager.closeDb(); // ЗАКРЫТИЕ БАЗЫ ДАННЫХ
        }
    }

    // ДЕЙСТВИЯ ПРИ НАЖАТИИ НА УДАЛЕНИЕ ИЗОБРАЖЕНИЯ
    public void onClickDeleteImage(View view) {
        newImage.setImageResource(R.drawable.ic_image_default); // УСТАНОВКА ФОНОВОЙ КАРТИНКИ
        tempUri = "empty"; // УСТАНОВКА ССЫЛКИ НА КАРТИНКУ ПУСТО
        addNewImage.setVisibility(View.GONE); // УСТАНОВКА ПОКАЗА СПРЯТАН
        fbAddImage.setVisibility(View.VISIBLE); // УСТАНОВКА ПОКАЗА ПОКАЗАНО
    }

    // ДЕЙСТВИЯ ПРИ НАЖАТИИ НА ДОБАВЛЕНИЕ НОВОЙ КАРТИНКИ
    public void onClickAddNewImage(View view) {
        addNewImage.setVisibility(View.VISIBLE); // ПОКАЗАТЬ ИЗОБРАЖЕНИЕ
        view.setVisibility(View.GONE); // СКРЫТЬ ИЗОБРАЖЕНИЕ
    }

    // ДЕЙСТВИЯ ПРИ НАЖАТИИ НА ВЫБОР КАРТИНКИ
    public void onClickChooseImage(View view) {
        Intent intentChooseImage = new Intent(Intent.ACTION_PICK);
        intentChooseImage.setType("image/*"); // ТИП ФАЙЛА ТОЛЬКО КАРТИНКИ
        startActivityForResult(intentChooseImage, PICK_IMAGE_CODE);
    }

}
