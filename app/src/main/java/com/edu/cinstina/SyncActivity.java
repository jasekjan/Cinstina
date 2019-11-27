package com.edu.cinstina;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.edu.cinstina.R;
import com.edu.cinstina.comm.DownloadFile;
import com.edu.cinstina.comm.ImportData;
import com.edu.cinstina.db.CategoryOpenHelper;
import com.edu.cinstina.db.WordsOpenHelper;

import java.io.File;

public class SyncActivity extends AppCompatActivity {
    String fileUrl;
    Integer ACTIVITY_CHOOSE_FILE = 1;
    static AsyncTask<String, Integer, Integer> df;
    static AsyncTask<String, Integer, Integer> id;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE =1;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //download requested file
                    df = new DownloadFile(this).execute(fileUrl);
                    Toast.makeText(getApplicationContext(), "Hotovo", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(this, "Bez přidělení oprávnění zápisu není možné synchronizovat slovíčka.", Toast.LENGTH_LONG).show();
                }
                return;
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            // other 'case' lines to check for other
            // permissions this app might request.
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        fileUrl = getIntent().getStringExtra("fileurl");

    }

    public void loadVocabulary(View view) {
        try {
            if (isNetworkConnected()) {

                // Here, thisActivity is the current activity
                /*if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.

                } else {
                    //download requested file
               */     df = new DownloadFile(this).execute(fileUrl);
/*
                }*/

            } else {
                Toast.makeText(getApplicationContext(), "Nejste připojeni k internetu", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void importVocabulary(View view) {
        id = new ImportData(this, this).execute();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        String path     = "";
        if(requestCode == ACTIVITY_CHOOSE_FILE) {
            Uri uri = data.getData();
            String FilePath = getRealPathFromURI(uri); // should the path be here in this string
            System.out.print("Cesta  = " + FilePath);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
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
