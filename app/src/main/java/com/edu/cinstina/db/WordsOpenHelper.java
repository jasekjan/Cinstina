package com.edu.cinstina.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Honza on 7. 3. 2018.
 */

public class WordsOpenHelper extends SQLiteOpenHelper {
    //private static final int DATABASE_VERSION=1; //verze zmenena na 2 dne 2018-03-18
    private static final int DATABASE_VERSION=1;
    private static final String WORDS_TABLE_NAME ="words";
    private static final String WORDS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+ WORDS_TABLE_NAME +" (id integer primary key autoincrement, myLang text not null, myReading text not null, myForeign text not null, category text not null, dateCreated long not null, state integer not null, stateChangeDate long not null, myLangAscii text)";

    //úprava tabulky o přidání nových sloupců pro učení
    private static final String WORDS_TABLE_ALTER1 =
            "ALTER TABLE "+ WORDS_TABLE_NAME +" ADD COLUMN dateCreated long not null";
    private static final String WORDS_TABLE_ALTER2 =
            "ALTER TABLE "+ WORDS_TABLE_NAME +" ADD COLUMN state integer not null";


    public WordsOpenHelper(Context context) {
        super(context, WORDS_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WORDS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addCharacters(Words words) {
        ContentValues cv = new ContentValues();
        cv.put("myLang", words.getMyLang());
        cv.put("myReading", words.getMyReading());
        cv.put("myForeign", words.getMyForeign());
        cv.put("category", words.getCategory());
        cv.put("dateCreated", words.getDateCreated());
        cv.put("state", words.getState());
        cv.put("stateChangeDate", words.getStateChangeDate());
        cv.put("myLangAscii", Normalizer.normalize(words.getMyLang(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(WORDS_TABLE_NAME, null, cv);
        db.close();
    }

    public Words findCharactersById(long id) {
        String query = "select id, myLang, myReading, myForeign, category, dateCreated, state, stateChangeDate from " + WORDS_TABLE_NAME + " where id = " + id;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Words words = new Words();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            words.setId(Integer.parseInt(cursor.getString(0)));
            words.setMyLang(cursor.getString(1));
            words.setMyReading(cursor.getString(2));
            words.setMyForeign(cursor.getString(3));
            words.setCategory(cursor.getString(4));
            words.setDateCreated(cursor.getLong(5));
            words.setState(cursor.getInt(6));
            words.setStateChangeDate(cursor.getLong(7));
            cursor.close();
        } else {
            words = null;
        }
        db.close();
        return words;
    }

    public Words findCharactersByChinese(String chinese) {
        String query = "select id, myLang, myReading, myForeign, category, dateCreated, state, stateChangeDate from " + WORDS_TABLE_NAME + " where myForeign = '" + chinese + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Words words = new Words();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            words.setId(Integer.parseInt(cursor.getString(0)));
            words.setMyLang(cursor.getString(1));
            words.setMyReading(cursor.getString(2));
            words.setMyForeign(cursor.getString(3));
            words.setCategory(cursor.getString(4));
            words.setDateCreated(cursor.getLong(5));
            words.setState(cursor.getInt(6));
            words.setStateChangeDate(cursor.getLong(7));
            cursor.close();
        } else {
            words = null;
        }
        db.close();
        return words;
    }

    public boolean deleteRecord(long id) {
        boolean result = false;
        int deletedRows = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        deletedRows = db.delete(WORDS_TABLE_NAME, "id = ?", new String[] {String.valueOf(id)});
        if (deletedRows > 0) {
            result = true;
        }
        db.close();
        return result;
    }

    public boolean deleteAll() {
        boolean result = false;

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WORDS_TABLE_NAME, "id > ?", new String[] {"0"});
        result = true;
        db.close();
        return result;
    }

    public ArrayList<Words> getAllLCharacters(String category, String poradi, String flashCards) {
        ArrayList<Words> wordsArrayList = new ArrayList<Words>();

        String orderByString;
        String whereString;

        if (category.equals("all")) {
            whereString = "";
        } else {
            whereString = " where category like  '%" + category + "%'";
        }

        switch (poradi) {
            //case "Náhodně" : orderByString = " order by random()";break;
            case "Dle abecedy" :orderByString = " order by category asc, myLang asc";break;
            case "Od nejnovějších" : orderByString = " order by dateCreated desc";break;
            default: orderByString = "";
        }

        String selectQuery = "select id, myLang, myReading, myForeign, dateCreated, state, stateChangeDate from "+ WORDS_TABLE_NAME + whereString + orderByString;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Words words = new Words();
                words.setId(Integer.parseInt(cursor.getString(0)));

                switch (flashCards) {
                    case "Znaky":
                        words.setMyLang(cursor.getString(1));
                        words.setMyReading(cursor.getString(2));
                        words.setMyForeign(cursor.getString(3));
                        break;
                    case "Pinyin":
                        words.setMyLang(cursor.getString(1));
                        words.setMyReading(cursor.getString(3));
                        words.setMyForeign(cursor.getString(2));
                        break;
                    case "Čeština":
                        words.setMyLang(cursor.getString(2));
                        words.setMyReading(cursor.getString(3));
                        words.setMyForeign(cursor.getString(1));
                        break;
                }
                // Adding Translate to list
                words.setDateCreated(cursor.getLong(4));
                words.setState(cursor.getInt(5));
                words.setStateChangeDate(cursor.getLong(6));
                wordsArrayList.add(words);
            } while (cursor.moveToNext());
        }

        if (poradi == "Náhodně") {
            Collections.shuffle(wordsArrayList);
        }

        return wordsArrayList;
    }

    public ArrayList<Words> getAllLCharactersByAny(String any, String category) {
        ArrayList<Words> wordsArrayList = new ArrayList<Words>();
        String categorySql;
        String whereSql;
        String selectQuery;

        if (category.equals("all")) {
            categorySql = ""; /*" or category like '%" + any + "%'";*/
        } else {
            categorySql = "category = '" + category + "'";
        }

        if (categorySql.equals("")) {
            whereSql = "(myLang like '%" + any + "%' or myLangAscii like '%" + any + "%' or myForeign like '%" + any + "%' or myReading like '%" + any + "%')";
        } else {
            whereSql = "(myLang like '%" + any + "%' or myLangAscii like '%" + any + "%' or myForeign like '%" + any + "%' or myReading like '%" + any + "%' ) and " + categorySql;
        }

        selectQuery = "select id, myLang, myReading, myForeign, dateCreated, state, stateChangeDate from "+ WORDS_TABLE_NAME + " where " + whereSql;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Words words = new Words();
                words.setId(Integer.parseInt(cursor.getString(0)));
                words.setMyLang(cursor.getString(1));
                words.setMyReading(cursor.getString(2));
                words.setMyForeign(cursor.getString(3));
                words.setDateCreated(cursor.getLong(4));
                words.setState(cursor.getInt(5));
                words.setStateChangeDate(cursor.getLong(6));
                // Adding Translate to list
                wordsArrayList.add(words);
            } while (cursor.moveToNext());
        }

        return wordsArrayList;
    }

    public void updateCharacter(Words words) {
        String[] whereArgs = new String[1];
        ContentValues cv = new ContentValues();
        cv.put("myLang", words.getMyLang());
        cv.put("myReading", words.getMyReading());
        cv.put("myForeign", words.getMyForeign());
        cv.put("category", words.getCategory());
        cv.put("dateCreated", words.getDateCreated());
        cv.put("state", words.getState());
        cv.put("stateChangeDate", words.getStateChangeDate());
        cv.put("myLangAscii", Normalizer.normalize(words.getMyLang(), Normalizer.Form.NFD));

        SQLiteDatabase db = this.getWritableDatabase();

        //whereArgs[0] = String.valueOf(words.getId());
        db.update(WORDS_TABLE_NAME, cv, "id=" + String.valueOf(words.getId()), null);
        db.close();
    }

    public void updateState(Words words) {
        String[] whereArgs = new String[1];
        ContentValues cv = new ContentValues();
        cv.put("state", words.getState());
        cv.put("stateChangeDate", System.currentTimeMillis());

        SQLiteDatabase db = this.getWritableDatabase();

        //whereArgs[0] = String.valueOf(words.getId());
        db.update(WORDS_TABLE_NAME, cv, "id=" + String.valueOf(words.getId()), null);
        db.close();
    }

    public int getStateById(int id) {
        int stav, dny;
        Long zmena;
        String query = "select state, stateChangeDate from " + WORDS_TABLE_NAME + " where id = " + id;
        //String query = "select id, myLang, myReading, myForeign, category, dateCreated, state from " + WORDS_TABLE_NAME + " where id = " + id;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        Long today = System.currentTimeMillis();

        if (cursor.moveToFirst() && 1==1) {
            cursor.moveToFirst();
            stav = cursor.getInt(0);
            zmena = cursor.getLong(1);
            cursor.close();

            //odstup zobrazení slov na základě stavu
            switch (stav) {
                case 0: dny = 1; break;
                case 1: dny = 2; break;
                case 2: dny = 4; break;
                case 3: dny = 6; break;
                case 4: dny = 8; break;
                case 5: dny = 10; break;
                default: dny = 1;
            }
            if ((today - zmena)/(1000*60*60*24) < dny) {
                stav = -1;
            }

        } else {
            stav = -1;
        }
        db.close();



        return stav;
    }

}
