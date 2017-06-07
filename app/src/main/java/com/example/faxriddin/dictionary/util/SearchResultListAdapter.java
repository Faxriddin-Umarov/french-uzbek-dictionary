package com.example.faxriddin.dictionary.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.faxriddin.dictionary.MainActivity;
import com.example.faxriddin.dictionary.R;
import com.example.faxriddin.dictionary.model.DictionaryItem;

import java.util.List;

/**
 * Created by Faxriddin on 5/19/2017.
 */

public class SearchResultListAdapter extends BaseAdapter {

    private List<DictionaryItem> mWordList;
    private Context mContext;

    public SearchResultListAdapter(Context context, List<DictionaryItem> wordList) {
        this.mContext = context;
        this.mWordList = wordList;
    }

    @Override
    public int getCount() {
        return mWordList.size();
    }

    @Override
    public Object getItem(int i) {
        return mWordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.search_result_list_view_item, viewGroup, false);
        TextView textView = (TextView) layout.findViewById(R.id.word_text_view);

        if (MainActivity.mIsFrench)
            textView.setText(mWordList.get(position).getFrench());
        else
            textView.setText(mWordList.get(position).getUzbek());

        return layout;
    }
}
