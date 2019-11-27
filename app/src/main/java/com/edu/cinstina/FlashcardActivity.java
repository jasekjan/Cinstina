package com.edu.cinstina;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.cinstina.db.CategoryOpenHelper;
import com.edu.cinstina.db.FlashcardRead;

import java.util.ArrayList;

public class FlashcardActivity extends AppCompatActivity {
    private static final boolean AUTO_HIDE = false;
    Spinner mySpinner;
    ArrayList<String> categories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_init);

        FlashcardRead fr = new FlashcardRead();
        fr.testTextToSpeech(getApplicationContext(), "");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        populateSpinner();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void showSwipeActivityCategory(View view) {
        String category;
        Intent i = new Intent(FlashcardActivity.this, SwipeActivity.class);

        Spinner sp = (Spinner) findViewById(R.id.spinner_category);
        Spinner spPoradi = (Spinner) findViewById(R.id.spinner_poradi);
        Spinner spFlashcard = (Spinner) findViewById(R.id.spinner_flashcards);
        i.putExtra("poradi", spPoradi.getSelectedItem().toString());
        i.putExtra("category", sp.getSelectedItem().toString());
        i.putExtra("flashcard", spFlashcard.getSelectedItem().toString());
        startActivity(i);
    }

    private void populateSpinner() {
        categories = getCategories();
        Spinner spinner = (Spinner) findViewById(R.id.spinner_category);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item, categories);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        ArrayList<String> poradi = new ArrayList<>();
        poradi.add("Od nejnovějších");
        poradi.add("Abecedně");
        //poradi.add("Abecedně po kategoriích");
        poradi.add("Náhodně vše");
        poradi.add("Náhodně 100");
        poradi.add("Náhodně 50");
        Spinner spinnerPoradi = (Spinner)findViewById(R.id.spinner_poradi);
        ArrayAdapter adapterPoradi = new ArrayAdapter(this, R.layout.spinner_item, poradi);
        adapterPoradi.setDropDownViewResource(R.layout.spinner_item);
        spinnerPoradi.setAdapter(adapterPoradi);

        ArrayList<String> flashcard = new ArrayList<>();
        flashcard.add("Znaky");
        flashcard.add("Čeština");
        flashcard.add("Pinyin");
        Spinner spinnerFlashcard = (Spinner)findViewById(R.id.spinner_flashcards);
        ArrayAdapter adapterFlashcard = new ArrayAdapter(this, R.layout.spinner_item, flashcard);
        adapterFlashcard.setDropDownViewResource(R.layout.spinner_item);
        spinnerFlashcard.setAdapter(adapterFlashcard);
    }

    private ArrayList<String> getCategories() {
        CategoryOpenHelper db = new CategoryOpenHelper(this);
        return db.getCategoriesAsText();
    }
}
