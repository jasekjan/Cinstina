package com.edu.cinstina;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.cinstina.db.CategoryWordAdapter;
import com.edu.cinstina.db.Words;
import com.edu.cinstina.db.WordsOpenHelper;

import java.util.ArrayList;

public class CategoriesWordsActivity extends AppCompatActivity {
    RecyclerView wordInCategory;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String category;
    WordsOpenHelper db;
    ArrayList<Words> al_in;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_words);

        category = getIntent().getStringExtra("category");

        wordInCategory = (RecyclerView) findViewById(R.id.rv_category_words_in);

        layoutManager = new LinearLayoutManager(this);
        wordInCategory.setLayoutManager(layoutManager);

        db = new WordsOpenHelper(this);
        al_in = db.getWordsInCategoryState(category, true);

        mAdapter = new CategoryWordAdapter(al_in);
        wordInCategory.setAdapter(mAdapter);

     }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
