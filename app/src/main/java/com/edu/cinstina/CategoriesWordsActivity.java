package com.edu.cinstina;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoriesWordsActivity extends AppCompatActivity {
    ListView listView;
    static final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);

        listView = (ListView) findViewById(R.id.lv_categories_words);

        //db = new CategoryOpenHelper(this);
        //categories = db.getCategories();

        final SimpleAdapter simpleAdapter = new SimpleAdapter(
                this, list,
                R.layout.row_categories_layout,
                new String[]{"name", "id"},
                new int[]{R.id.tv_cat_name, R.id.tv_cat_id}
        );
    }
}
