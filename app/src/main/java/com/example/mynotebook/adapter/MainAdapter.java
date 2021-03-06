package com.example.mynotebook.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotebook.EditActivity;
import com.example.mynotebook.R;
import com.example.mynotebook.dataBase.Constants;
import com.example.mynotebook.dataBase.Manager;

import java.util.ArrayList;
import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context context;
    private List<ListNote> mainArray;

    public MainAdapter(Context context) {
        this.context = context;
        mainArray = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view, context, mainArray);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(mainArray.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mainArray.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewTitle;
        private Context context;
        private List<ListNote> mainArray;

        public MyViewHolder(@NonNull View itemView, Context context, List<ListNote> mainArray) {
            super(itemView);
            this.context = context;
            this.mainArray = mainArray;
            textViewTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(this);
        }
        // УСТАНОВКА ТЕКСТА ЗАГОЛОВКА НА ЭКРАНЕ
        public void setData(String title) {
            textViewTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, EditActivity.class); // НОВОЕ НАМЕРЕНИЕ
            intent.putExtra(Constants.LIST_ITEM_INTENT, mainArray.get(getAdapterPosition())); // ПОЛОЖИТЬ ДАННЫЕ В ЭЛЕМЕНТ СПИСКА
            intent.putExtra(Constants.EDIT_STATE, false); //
            context.startActivity(intent); // ЗАПУСК АКТИВНОСТИ
        }
    }

    // ОБНОВЛЕНИЕ СПИСКА
    public void updateAdapter(List<ListNote> newList) {
        mainArray.clear(); // УДАЛЕНИЕ ВСЕХ ЭЛЕМЕНТОВ В СПИСКЕ
        mainArray.addAll(newList); // ДОБАВЛЕНИЕ НОВЫХ ЭЛЕМЕНТОВ В СПИСОК
        notifyDataSetChanged(); // ОБНОВЛЕНИЕ
    }

    // УДАЛЕНИЕ ЭЛЕМЕНТА
    public void removeItem(int position, Manager dbManager){
        dbManager.deleteFromDb(mainArray.get(position).getId()); //УДАЛИТЬ ИЗ БАЗЫ ДАНННЫХ ПО ID
        mainArray.remove(position); // УДАЛИТЬ ИЗ МАССИВА
        notifyItemRangeChanged(0, mainArray.size()); // ОБНОВЛЕНИЕ АДАПТЕРА И ПЕРЕЗАГРУЗКА recyclerView
        notifyItemRemoved(position); // УДАЛЕНИЕ ЭЛЕМЕНТА ПО ПОЗИЦИИ
    }
}
