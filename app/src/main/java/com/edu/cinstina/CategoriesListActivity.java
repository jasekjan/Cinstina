package com.edu.cinstina;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.cinstina.db.Category;
import com.edu.cinstina.db.CategoryAdapter;
import com.edu.cinstina.db.CategoryOpenHelper;
import com.edu.cinstina.db.Words;
import com.edu.cinstina.db.WordsOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoriesListActivity extends AppCompatActivity {

    static int CATEGORY_SAVED = 0;
    ListView listView;
    static final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    private EditText editText;
    private ArrayList<Category> categories;
    private CategoryOpenHelper db;
    private WordsOpenHelper db_word;
    private Parcelable state;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);

        registerForContextMenu((ListView)findViewById(R.id.lv_categories_list));
        listView = (ListView) findViewById(R.id.lv_categories_list);

        db = new CategoryOpenHelper(this);
        categories = db.getCategories();

        adapter = new CategoryAdapter(this, categories);

        editText = (EditText) findViewById(R.id.et_search);

        //populateList(categories);
        listView.setAdapter(adapter);
        if (state != null) {
            listView.onRestoreInstanceState(state);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        state = listView.onSaveInstanceState();
        outState.putParcelable("listState", state);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        state = savedInstanceState.getParcelable("listState");
    }


    private void populateList(ArrayList<Category> al) {
        list.clear();
        for (int i = 0; i < al.size(); i++) {
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("name", al.get(i).getName());
            temp.put("id", String.valueOf(al.get(i).getId()));
            list.add(temp);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item_catword, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo ami = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
       // HashMap<String, String> itemInfo = new HashMap<>();
        Category itemInfo;
        itemInfo = (Category) listView.getItemAtPosition(ami.position);
        int id = item.getItemId();

        if (id == R.id.menu_item_del) {
            deleteCategory(itemInfo, this);
            adapter.reloadData(db.getCategories());
        } else if (id == R.id.menu_tem_add_word) {
                Intent i = new Intent(this, CategoriesWordsActivity.class);
                i.putExtra("category", String.valueOf(itemInfo.getName()));
                startActivity(i);
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CATEGORY_SAVED) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                adapter.reloadData(db.getCategories());
            }
        }
    }

    protected boolean deleteCategory(Category c, Context context) {
        boolean ret;
        ArrayList<Words> w;
        CategoryOpenHelper db = new CategoryOpenHelper(getApplicationContext());
        state = listView.onSaveInstanceState();

        db_word = new WordsOpenHelper(context) ;
        w = db_word.getWordsInCategoryState(c.getName(), true);

        int pocet = 0;
        for (Words ww: w) {
            if (ww.getInfo().equals("1")) {pocet++;}
        }

        if (pocet == 0) {
            ret = db.deleteById(c.getId());
        } else {
            Toast.makeText(context, "Kategorie je použitá u některého slovíčka, nejdřív zrušte vazbu!", Toast.LENGTH_SHORT).show();
            ret = false;
        }

        return ret;
    }

    public void addCategory(View view) {
        Intent i = new Intent(view.getContext(), CategoryShowActivity.class);
        i.putExtra("id", "0");
        startActivityForResult(i, CATEGORY_SAVED);
    }

}
