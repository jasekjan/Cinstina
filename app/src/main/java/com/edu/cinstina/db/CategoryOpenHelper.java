package com.edu.cinstina.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CategoryOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String CATEGORY_TABLE_NAME ="category";
    private static final String CATEGORY_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+ CATEGORY_TABLE_NAME +" (id integer primary key autoincrement, name text not null)";

    public CategoryOpenHelper(Context context) {
        super(context, CATEGORY_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CATEGORY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionOld, int versionNew) {

    }

    public void addCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put("name", category.getName());
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(CATEGORY_TABLE_NAME, null, cv);
        db.close();
    }

    public Category findCategoryById(long id) {
        String query = "select id, name from " + CATEGORY_TABLE_NAME + " where id = " + id;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Category category = new Category();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            category.setId(Integer.parseInt(cursor.getString(0)));
            category.setName(cursor.getString(1));
            cursor.close();
        } else {
            category = null;
        }
        db.close();
        return category;
    }

    public boolean deleteAll() {
        boolean result = false;

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CATEGORY_TABLE_NAME, "id > ?", new String[] {"0"});
        result = true;
        db.close();
        return result;
    }

    public boolean deleteById(int id) {
        boolean result = false;

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CATEGORY_TABLE_NAME, "id = ?", new String[] {String.valueOf(id)});
        result = true;
        db.close();
        return result;
    }

    public ArrayList<String> getCategoriesAsText() {
        ArrayList<String> categories = new ArrayList<>();
        String query = "select distinct name from "+ CATEGORY_TABLE_NAME +" order by name asc";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        categories.add(0,"vše");
        return categories;
    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        String query = "select distinct id, name from "+ CATEGORY_TABLE_NAME +" order by name asc";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Category c = new Category();
                c.setId(cursor.getInt(0));
                c.setName(cursor.getString(1));
                categories.add(c);
            } while (cursor.moveToNext());
        }

        return categories;
    }

    public Category findCategoryByName(String name) {
        String query = "select id, name from " + CATEGORY_TABLE_NAME + " where name = '" + name + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Category category = new Category();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            category.setId(Integer.parseInt(cursor.getString(0)));
            category.setName(cursor.getString(1));
            cursor.close();
        } else {
            category = null;
        }
        db.close();
        return category;
    }

}
