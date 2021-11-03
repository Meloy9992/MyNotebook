package com.example.mynotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mynotebook.dataBase.Manager;

public class EditActivity extends AppCompatActivity {
    private EditText editTitle, editDescription;
    private Manager manager;

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
        manager = new Manager(this);
    }

    public void onClickSave(View view) {
        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();
        if (title.equals("") || description.equals("")) {
            Toast.makeText(this, R.string.EmptyTitleOrDescription, Toast.LENGTH_SHORT).show();
        } else {
            manager.insertToDb(title, description);
            Toast.makeText(this, R.string.Saved, Toast.LENGTH_SHORT).show();
            finish(); //Закрытие активити
            manager.closeDb();
        }
    }
}
