package com.edu.cinstina;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.cinstina.db.Category;
import com.edu.cinstina.db.CategoryOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoriesListActivity extends AppCompatActivity {

    static int CATEGORY_SAVED = 0;
    static int CATEGORY_ADDED = 0;
    ListView listView;
    static final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    private EditText editText;
    private ArrayList<Category> categories;
    private CategoryOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);

        registerForContextMenu((ListView)findViewById(R.id.lv_categories_list));
        listView = (ListView) findViewById(R.id.lv_categories_list);

        db = new CategoryOpenHelper(this);
        categories = db.getCategories();

        final SimpleAdapter simpleAdapter = new SimpleAdapter(
                this, list,
                R.layout.row_categories_layout,
                new String[]{"name", "id"},
                new int[]{R.id.tv_cat_name, R.id.tv_cat_id}
        );

        /*      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap o = (HashMap) listView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(), CategoryShowActivity.class);
                intent.putExtra("id", String.valueOf(o.get("id")));
                startActivityForResult(intent, WORD_ADDED);
            }
        });
*/
        editText = (EditText) findViewById(R.id.et_search);

        populateList(categories);
        listView.setAdapter(simpleAdapter);

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
        inflater.inflate(R.menu.menu_item, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo ami = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        HashMap<String, String> itemInfo = new HashMap<>();
        itemInfo = (HashMap)listView.getItemAtPosition(ami.position);
        switch (item.getItemId()) {
            case R.id.menu_item_del:
                deleteCategory( Integer.valueOf(String.valueOf(itemInfo.get("id"))));
                break;
            case R.id.menu_tem_add_word :
                Intent i = new Intent(this, CategoriesWordsActivity.class);
                i.putExtra("category", String.valueOf(itemInfo.get("name")));
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
                startActivity(getIntent());
            }
        }

        if (requestCode == CATEGORY_ADDED) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                finish();
                startActivity(getIntent());
            }
        }
    }

    protected boolean deleteCategory(Integer id) {
        boolean ret;
        CategoryOpenHelper db = new CategoryOpenHelper(getApplicationContext());
        ret = db.deleteById(id);
        finish();
        startActivity(getIntent());
        return ret;
    }

    public void addCategory(View view) {
        Intent i = new Intent(view.getContext(), CategoryShowActivity.class);
        i.putExtra("id", "0");
        startActivityForResult(i, CATEGORY_SAVED);
    }

}
