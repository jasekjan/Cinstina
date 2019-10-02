package com.edu.cinstina;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.cinstina.db.Settings;
import com.edu.cinstina.db.SettingsOpenHelper;

/**
 * An edu full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity_zaloha extends AppCompatActivity {
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_settings:
                showSettings(mContentView);
                break;
            case R.id.action_sync :
                syncWords(mContentView);
                break;
            case R.id.action_search :
                showWordsCategory(mContentView);
                break;
            case R.id.action_new :
                addWord(mContentView);
                break;
            default:
                break;
        }

        return true;
    }



    public void showWordsCategory(View view) {
        Intent i = new Intent(MainActivity_zaloha.this, WordsListActivity.class);
        Spinner sp = (Spinner) findViewById(R.id.spinner_category);
        Spinner spPoradi = (Spinner) findViewById(R.id.spinner_poradi);
        i.putExtra("poradi", spPoradi.getSelectedItem().toString());
        i.putExtra("category", sp.getSelectedItem().toString());
        startActivity(i);
    }

    public void showWords(View view) {
        Intent i = new Intent(MainActivity_zaloha.this, WordsListActivity.class);
        Spinner sp = (Spinner) findViewById(R.id.spinner_category);
        Spinner spPoradi = (Spinner) findViewById(R.id.spinner_poradi);
        i.putExtra("poradi", "Dle abecedy");
        i.putExtra("category", "all");
        startActivity(i);
    }

    public  void showSettings(View view) {
        Intent i = new Intent(MainActivity_zaloha.this, SettingsActivity.class);
        startActivity(i);
    }

    public void addWord(View view) {
        Intent i = new Intent(MainActivity_zaloha.this, WordShowActivity.class);
        i.putExtra("id", "0");
        startActivityForResult(i, WORD_SAVED);
    }




    public void syncWords(View view) {
        String category;
        Intent i = new Intent(MainActivity_zaloha.this, SyncActivity.class);
        SettingsOpenHelper db = new SettingsOpenHelper(MainActivity_zaloha.this);
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
        Intent i = new Intent(MainActivity_zaloha.this, FlashcardActivity.class);
        startActivity(i);
    }

    public void showCategories(View view) {
        Intent i = new Intent(MainActivity_zaloha.this, CategoriesActivity.class);
        startActivity(i);
    }
}
