package com.edu.cinstina;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.edu.cinstina.db.FlashcardRead;
import com.edu.cinstina.db.Words;
import com.edu.cinstina.db.WordsOpenHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {
    TextView tv_myForeign, tv_myReading, tv_myLang;
    ImageButton imageButton;
    Button button_known, button_unknown;

    private int currentMyLang;
    private int currentMyReading;
    private int transparentColor = Color.TRANSPARENT;
    private int blackColor = Color.BLACK;

    private String flashcard;

    public PageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page2_layout, container, false);
        tv_myForeign = (TextView)view.findViewById(R.id.tv_fragmentDetailShow);
        tv_myReading = (TextView)view.findViewById(R.id.tv_fragmentDetailReading);
        tv_myLang = (TextView)view.findViewById(R.id.tv_fragmentDetailMyLanguage);
        imageButton = (ImageButton)view.findViewById(R.id.ib_fragment_play);
        button_known = (Button)view.findViewById(R.id.btn_known);
        button_unknown = (Button)view.findViewById(R.id.btn_unknown);

        final Bundle bundle = getArguments();
        final String myForeign = bundle.getString("myForeign");
        final String myLang = bundle.getString("myLang");
        final String myReading = bundle.getString("myReading");

        flashcard = bundle.getString("flashcard");

        tv_myForeign.setText(myForeign);
        tv_myReading.setText(myReading);
        tv_myLang.setText(myLang);

        tv_myReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMyReading = tv_myReading.getCurrentTextColor();

                if (currentMyReading == transparentColor) {
                    tv_myReading.setTextColor(blackColor);
                } else {
                    tv_myReading.setTextColor(transparentColor);
                }
            }
        });

        tv_myLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMyLang = tv_myLang.getCurrentTextColor();

                if (currentMyLang == transparentColor) {
                    tv_myLang.setTextColor(blackColor);
                } else {
                    tv_myLang.setTextColor(transparentColor);
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                FlashcardRead fr = new FlashcardRead();
                switch (flashcard.toLowerCase()) {
                    case "znaky" :
                        fr.testTextToSpeech(getContext(), myForeign);
                        break;
                    case "čeština" :
                        fr.testTextToSpeech(getContext(), myReading);
                        break;
                    case "pinyin" :
                        fr.testTextToSpeech(getContext(), myReading);
                        break;
                }
            }
        });

        button_known.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int stav;
                WordsOpenHelper woh = new WordsOpenHelper(getContext());
                Words w = new Words();

                stav = woh.getStateById((int)Integer.valueOf(bundle.getString("id")));

                if (stav <5 && stav > 0) {
                    w.setState(stav +1);
                    w.setStateChangeDate(System.currentTimeMillis());
                };
                w.setStateChangeDate(System.currentTimeMillis());
                w.setDateCreated(bundle.getLong("dateCreated"));
                w.setCategory(bundle.getString("category"));
                w.setId((int)Integer.valueOf(bundle.getString("id")));
                w.setMyForeign(bundle.getString("myForeign"));
                w.setMyLang(bundle.getString("myLang"));
                w.setMyReading(bundle.getString("myReading"));

                woh.updateState(w);

                button_known.setBackgroundColor(Color.parseColor("#659df7"));
                button_unknown.setBackgroundColor(Color.parseColor("#659df7"));
            }
        });

        button_unknown.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int stav;
                WordsOpenHelper woh = new WordsOpenHelper(getContext());
                Words w = new Words();

                stav = woh.getStateById((int)Integer.valueOf(bundle.getString("id")));

                if (stav > 0) {
                    w.setState(stav -1);
                    w.setStateChangeDate(System.currentTimeMillis());
                };
                w.setStateChangeDate(System.currentTimeMillis());
                w.setDateCreated(bundle.getLong("dateCreated"));
                w.setCategory(bundle.getString("category"));
                w.setId(bundle.getInt("id"));
                w.setMyForeign(bundle.getString("myForeigh"));
                w.setMyLang(bundle.getString("myLang"));
                w.setMyReading(bundle.getString("myReading"));

                woh.updateState(w);

                button_known.setBackgroundColor(Color.parseColor("#659df7"));
                button_unknown.setBackgroundColor(Color.parseColor("#659df7"));
            }
        });

        return view;
    }



}
