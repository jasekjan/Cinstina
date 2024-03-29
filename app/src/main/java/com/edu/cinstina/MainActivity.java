package com.edu.cinstina;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.cinstina.db.Category;
import com.edu.cinstina.db.CategoryOpenHelper;
import com.edu.cinstina.db.WordsOpenHelper;
import com.edu.cinstina.db.FlashcardRead;
import com.edu.cinstina.db.Settings;
import com.edu.cinstina.db.SettingsOpenHelper;

import java.util.ArrayList;

/**
 * An edu full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {
    private static int WORD_SAVED = 0;

    private View mContentView;
    public static final int progress_bar_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        WordsOpenHelper db = new WordsOpenHelper(getApplicationContext());
        db.deleteAll();
        */
        setContentView(R.layout.activity_main);
    }



    public void showWordsCategory(View view) {
        Intent i = new Intent(MainActivity.this, WordsListActivity.class);
        Spinner sp = (Spinner) findViewById(R.id.spinner_category);
        Spinner spPoradi = (Spinner) findViewById(R.id.spinner_poradi);
        i.putExtra("poradi", spPoradi.getSelectedItem().toString());
        i.putExtra("category", sp.getSelectedItem().toString());
        startActivity(i);
    }

    public void showWords(View view) {
        Intent i = new Intent(MainActivity.this, WordsListActivity.class);
        Spinner sp = (Spinner) findViewById(R.id.spinner_category);
        Spinner spPoradi = (Spinner) findViewById(R.id.spinner_poradi);
        i.putExtra("poradi", "Dle abecedy");
        i.putExtra("category", "all");
        startActivity(i);
    }

    public  void showSettings(View view) {
        Intent i = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(i);
    }

    public void addWord(View view) {
        Intent i = new Intent(MainActivity.this, WordShowActivity.class);
        i.putExtra("id", "0");
        startActivityForResult(i, WORD_SAVED);
    }




    public void syncWords(View view) {
        String category;
        Intent i = new Intent(MainActivity.this, SyncActivity.class);
        SettingsOpenHelper db = new SettingsOpenHelper(MainActivity.this);
        Settings settings = db.findSettingsById(1);

        if (settings == null || settings.getPath() == null || settings.getPath().equals("")) {
            Toast.makeText(getApplicationContext(), "Nejdřív proveďte nastavení", Toast.LENGTH_LONG).show();
        } else {
            i.putExtra("fileurl", settings.getPath());
            startActivity(i);
        }
        //populateSpinner();
    }

    public void startFlashcards(View view) {
        Intent i = new Intent(MainActivity.this, FlashcardActivity.class);
        startActivity(i);
    }

    public void showCategories(View view) {
       // Intent i = new Intent(MainActivity.this, CategoriesActivity.class);
        Intent i = new Intent(MainActivity.this, CategoriesListActivity.class);
        startActivity(i);
    }
}
