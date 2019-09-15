package com.edu.cinstina;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.cinstina.R;
import com.edu.cinstina.comm.DownloadFile;
import com.edu.cinstina.comm.ImportData;
import com.edu.cinstina.db.CategoryOpenHelper;
import com.edu.cinstina.db.WordsOpenHelper;

public class SyncActivity extends AppCompatActivity {
    String fileUrl;
    Integer ACTIVITY_CHOOSE_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        fileUrl = getIntent().getStringExtra("fileurl");

    }

    public void loadVocabulary(View view) {
        try {
            if (isNetworkConnected()) {

                new DownloadFile(this).execute(fileUrl);

                new ImportData(this, this).execute();

                Toast.makeText(getApplicationContext(), "Hotovo", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Nejste připojeni k internetu", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        String path     = "";
        if(requestCode == ACTIVITY_CHOOSE_FILE)
        {
            Uri uri = data.getData();
            String FilePath = getRealPathFromURI(uri); // should the path be here in this string
            System.out.print("Cesta  = " + FilePath);
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String [] proj      = {MediaStore.Images.Media.DATA};
        Cursor cursor       = getContentResolver().query( contentUri, proj, null, null,null);
        if (cursor == null) return null;
        int column_index    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void deleteEverything(View view){
        WordsOpenHelper woh = new WordsOpenHelper(this);
        CategoryOpenHelper coh = new CategoryOpenHelper(this);

        woh.deleteAll();
        coh.deleteAll();

        woh.close();
        coh.close();
    }
}
