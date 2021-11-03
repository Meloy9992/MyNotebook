package com.example.mynotebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mynotebook.adapter.MainAdapter;
import com.example.mynotebook.dataBase.Manager;

public class MainActivity extends AppCompatActivity {

    private Manager manager;
    private EditText editTitle, editDescription;
    private TextView textView;
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        manager = new Manager(this);
        editTitle = findViewById(R.id.Title);
        editDescription = findViewById(R.id.Description);
        textView = findViewById(R.id.tvTitle);
        recyclerView = findViewById(R.id.rcView);
        mainAdapter = new MainAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        manager.openDb();
        mainAdapter.updateAdapter(manager.getFromDbTitle());
    }

    public void OnClickAdd(View view) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class); // переход на другой экран
        startActivity(intent); //запуск перехода
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.closeDb();
    }
}