package com.edu.cinstina;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CategoriesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
    }

    public void showCategoriesList(View view) {
        Intent i = new Intent(CategoriesActivity.this, CategoriesListActivity.class);
        startActivity(i);
    }

    public void assignWordToCategory(View view) {
        Intent i = new Intent(CategoriesActivity.this, CategoriesWordsActivity.class);
        Bundle bundle = new Bundle();

        //TODO
        i.putExtra("category", "Město");
        startActivity(i);
    }
}
