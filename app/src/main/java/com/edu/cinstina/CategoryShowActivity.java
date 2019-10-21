package com.edu.cinstina;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.cinstina.db.Category;
import com.edu.cinstina.db.CategoryOpenHelper;
import com.edu.cinstina.db.WordsOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CategoryShowActivity extends AppCompatActivity {
    Integer itemId;
    Category category;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CategoryOpenHelper db = new CategoryOpenHelper(this);
        itemId = Integer.valueOf(getIntent().getStringExtra("id"));

        setContentView(R.layout.activity_category_show);
        EditText id = (EditText) findViewById(R.id.et_show_category_id);
        id.setText(String.valueOf(itemId));

        if (itemId > 0) {
            category = db.findCategoryById(itemId);

            EditText nazev = (EditText) findViewById(R.id.et_show_category_name);

            nazev.setText(category.getName());
        }
    }



    public void saveCategory(View view) {
        Category category = new Category();
        CategoryOpenHelper db = new CategoryOpenHelper(this);

        EditText name = (EditText) findViewById(R.id.et_show_category_name);
        EditText id = (EditText) findViewById(R.id.et_show_category_id);

        if (String.valueOf(name.getText()).length() < 4) {
            Toast.makeText(this, "Název kategorie musí mít alespoň 4 znaky", Toast.LENGTH_SHORT).show();
        } else {
            category.setName(String.valueOf(name.getText()));

            db.addCategory(category);

            if (getParent() == null) {
                setResult(Activity.RESULT_OK, getIntent());
            } else {
                getParent().setResult(Activity.RESULT_OK, getIntent());
            }
            finish();
        }
    }

}
