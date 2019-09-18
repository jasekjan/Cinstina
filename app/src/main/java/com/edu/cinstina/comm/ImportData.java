package com.edu.cinstina.comm;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.cinstina.R;
import com.edu.cinstina.db.Category;
import com.edu.cinstina.db.CategoryOpenHelper;
import com.edu.cinstina.db.Words;
import com.edu.cinstina.db.WordsOpenHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Honza on 22. 4. 2018.
 */

public class ImportData extends AsyncTask<String, Integer, Integer> {
    private static String FILEURI = "/chineseData/listOfWords.csv";
    private String path;
    private File file;
    private Context context;
    private int totalLines = 0;
    private int currentLines = 0;
    private Activity activity;;
    private TextView mTextView ;
    private ProgressBar mProgressBar;
    private TextView mTextViewResult;
    private int countInserted;
    private String category_parts[];
    private Long dateCreated;

    private String id, myLang, myReading, myForeign, category;
    private String[] tokens;

    public ImportData(Context context, Activity activity) {
        this.context = context;
        this.file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + FILEURI);
        this.activity = activity;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            WordsOpenHelper db = new WordsOpenHelper(context);
            Words findChar;
            CategoryOpenHelper coh = new CategoryOpenHelper(context);
            Category categoryFind;

            while ((line = br.readLine()) != null) {
                totalLines++;
            }

            br = new BufferedReader(new FileReader(file));

            mTextViewResult = (TextView) activity.findViewById(R.id.tv_count);
            mTextView = (TextView) activity.findViewById(R.id.tv_load);
            mProgressBar = (ProgressBar) activity.findViewById(R.id.pb_load);


            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

            while ((line = br.readLine()) != null) {
                currentLines++;
                tokens = line.split(",");
                id = tokens[0];
                myLang = tokens[1];
                myReading = tokens[2];
                myForeign = tokens[3];
                category = tokens[4];

                publishProgress( (int)(currentLines * 100) / totalLines);



                if (!id.equals("id")) {

                    category_parts = category.split("\\|");
                    for (int i=0; i < category_parts.length; i++) {
                        categoryFind = coh.findCategoryByName(category_parts[i]);

                        if (categoryFind == null) {
                            categoryFind = new Category(category_parts[i]);
                            coh.addCategory(categoryFind);
                        }
                    }


                    Words words = new Words(myLang, myReading, myForeign, category,  System.currentTimeMillis(), 0, System.currentTimeMillis());
                    findChar = db.findCharactersByChinese(myForeign);
                    if (findChar == null && myLang != null) {
                        if (findChar == null || !findChar.equals(words)) {
                            db.addCharacters(words);
                            countInserted++;
                        }
                    }
                }
            }
            br.close();
            db.close();
            coh.close();
        } catch (IOException e) {
            //You'll need to add proper error handling here
        }
        return countInserted;
    }


    // After each task done
    protected void onProgressUpdate(Integer... progress){
        // Display the progress on text view
        mTextView.setText("Nahráno : "+progress[0] + " %");
        // Update the progress bar
        mProgressBar.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(Integer i) {
        super.onPostExecute(i);
        if (mTextViewResult == null) {
            mTextViewResult = (TextView) activity.findViewById(R.id.tv_count);
        }
        mTextViewResult.setText("Uloženo " + i + " nových slovíček");
    }
}
