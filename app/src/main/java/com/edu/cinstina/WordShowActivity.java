package com.edu.cinstina;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.edu.cinstina.db.Words;
import com.edu.cinstina.db.WordsOpenHelper;
import com.edu.cinstina.db.FlashcardRead;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Created by Honza on 19. 3. 2018.
 */

public class WordShowActivity extends Activity {
    Integer itemId;
    Words words;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WordsOpenHelper db = new WordsOpenHelper(this);
        itemId = Integer.valueOf(getIntent().getStringExtra("id"));

        setContentView(R.layout.activity_word_show);
        EditText id = (EditText) findViewById(R.id.et_show_id);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_detail_play);
        id.setText(String.valueOf(itemId));

        if (itemId > 0) {
            words = db.findCharactersById(itemId);

            EditText myLang = (EditText) findViewById(R.id.et_show_myLang);
            final EditText myForeign = (EditText) findViewById(R.id.et_show_myForeign);
            EditText myReading = (EditText) findViewById(R.id.et_show_myReading);
            EditText category = (EditText) findViewById(R.id.et_show_category);

            myLang.setText(words.getMyLang());
            myForeign.setText(words.getMyForeign());
            myReading.setText(words.getMyReading());
            category.setText(words.getCategory());

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FlashcardRead fr = new FlashcardRead();
                    fr.testTextToSpeech(getApplicationContext(), myForeign.getText().toString());
                }
            });
        }
    }



    public void saveWord(View view) {
        Words wordsToSave = new Words();
        WordsOpenHelper db = new WordsOpenHelper(this);

        EditText czech = (EditText) findViewById(R.id.et_show_myLang);
        EditText chinese = (EditText) findViewById(R.id.et_show_myForeign);
        EditText pinyin = (EditText) findViewById(R.id.et_show_myReading);
        EditText category = (EditText) findViewById(R.id.et_show_category);
        EditText id = (EditText) findViewById(R.id.et_show_id);

        wordsToSave.setMyForeign(String.valueOf(chinese.getText()));
        wordsToSave.setMyLang(String.valueOf(czech.getText()));
        wordsToSave.setMyReading(String.valueOf(pinyin.getText()));
        wordsToSave.setCategory(String.valueOf(category.getText()));

        if (Integer.valueOf(String.valueOf(id.getText())) > 0) {
            wordsToSave.setId(Integer.valueOf(String.valueOf(id.getText())));
            db.updateCharacter(wordsToSave);
        } else {
            db.addCharacters(wordsToSave);
        }

        if (getParent() == null) {
            setResult(Activity.RESULT_OK, getIntent());
        } else {
            getParent().setResult(Activity.RESULT_OK, getIntent());
        }
        finish();
    }

}
