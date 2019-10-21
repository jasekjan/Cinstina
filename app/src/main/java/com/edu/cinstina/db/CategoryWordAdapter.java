package com.edu.cinstina.db;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.cinstina.R;

import java.util.ArrayList;

public class CategoryWordAdapter extends RecyclerView.Adapter<CategoryWordAdapter.ViewHolder> {
    private static ArrayList<Words> words;
    Context context;
    private static SparseBooleanArray itemStateArray= new SparseBooleanArray();

    public CategoryWordAdapter(ArrayList<Words> words) {
        this.words= words;
        for (int i=0; i<words.size(); i++) {
            itemStateArray.put(i, words.get(i).getInfo().equals("1"));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView tv_word;
        public final TextView tv_word_id;
        public final TextView tv_word_stav;
        public final TextView tv_word_category;
        public final CheckBox cb_in;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_word = (TextView)itemView.findViewById(R.id.tv_row_category_word);
            tv_word_id = (TextView)itemView.findViewById(R.id.tv_row_category_word_id);
            tv_word_stav = (TextView)itemView.findViewById(R.id.tv_row_category_stav);
            tv_word_category = (TextView)itemView.findViewById(R.id.tv_row_category_category);
            cb_in = (CheckBox)itemView.findViewById(R.id.cb_in_category);

        }

        void bind(int position) {
            // use the sparse boolean array to check
            if (!itemStateArray.get(position, false)) {
                cb_in.setChecked(false);
            } else {
                cb_in.setChecked(true);
            }
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (!itemStateArray.get(adapterPosition, false)) {
                cb_in.setChecked(!cb_in.isChecked());
                itemStateArray.put(adapterPosition, true);
            } else  {
                cb_in.setChecked(false);
                itemStateArray.put(adapterPosition, false);
            }

            WordsOpenHelper woh = new WordsOpenHelper(view.getContext());
            woh.updateCategoryWord(words.get(adapterPosition).getId(), words.get(adapterPosition).getCategory(), cb_in.isChecked());
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Words w = words.get(position);

        holder.tv_word.setText(w.getMyLang());
        holder.tv_word_id.setText(String.valueOf(w.getId()));
        holder.tv_word_category.setText(w.getCategory());

        holder.bind(position);
        /*if (w.getInfo().equals("1")) {
            itemStateArray.put(position, true);
        } else {
            itemStateArray.put(position, false);
        }*/
    }

    @Override
    public int getItemCount() {
        int r;

        if (words != null) {
            r = words.size();
        } else {
            r = 0;
        }

        return r;
    }
}
