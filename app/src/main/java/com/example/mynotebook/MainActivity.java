package com.example.mynotebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.mynotebook.adapter.MainAdapter;
import com.example.mynotebook.dataBase.Manager;

public class MainActivity extends AppCompatActivity {

    private Manager manager; // МЕНЕДЖЕР БАЗЫ ДАННЫХ
    private EditText editTitle, editDescription; // ФОРМЫ ВВОДА ЗАГОЛОВКА И ТЕКСТА
    private TextView textView; // ФОРМА ДЛЯ ОТОБРАЖЕНИЯ ЗАГОЛОВКОВ
    private RecyclerView recyclerView; // СПИСОК ЗАГОЛОВКОВ
    private MainAdapter mainAdapter; // АДАПТОР

    // ВЫПОЛНИТЬ ДЕЙСТВИЯ ПРИ ОТКРЫТИИ ПРИЛОЖЕНИЯ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.id_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainAdapter.updateAdapter(manager.getFromDbTitle(newText));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        manager = new Manager(this);
        editTitle = findViewById(R.id.Title);
        editDescription = findViewById(R.id.Description);
        textView = findViewById(R.id.tvTitle);
        recyclerView = findViewById(R.id.rcView);
        mainAdapter = new MainAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getItemTouchHelper().attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mainAdapter);
    }

    // ДЕЙСТВИЯ ПРИ ВОЗВРАТЕ В ПРОГРАММУ
    @Override
    public void onResume() {
        super.onResume();
        manager.openDb();
        mainAdapter.updateAdapter(manager.getFromDbTitle(""));
    }

    // ДЕЙСТВИЯ КНОПКИ СОЗДАНИЯ НОВОЙ ЗАПИСИ
    public void OnClickAdd(View view) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class); // переход на другой экран
        startActivity(intent); //запуск перехода
    }

    // ДЕЙСТВИЯ ПРИ ЗАКРЫТИИ ПРИЛОЖЕНИЯ
    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.closeDb(); // ЗАКРЫТЬ БАЗУ ДАННЫХ
    }

    private ItemTouchHelper getItemTouchHelper(){
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mainAdapter.removeItem(viewHolder.getAdapterPosition(), manager);
            }
        });
    }

}