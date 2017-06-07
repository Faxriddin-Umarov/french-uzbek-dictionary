package com.example.faxriddin.dictionary.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.faxriddin.dictionary.MainActivity;
import com.example.faxriddin.dictionary.model.DictionaryItem;

import java.util.List;

/**
 * Created by Faxriddin on 5/19/2017.
 */

public class ReadWordListTask extends AsyncTask<String, Void, List<DictionaryItem>> {

    private static final String TAG = "ReadWordListTask";
    private ListView mListView;
    private DBUtil mDBUtil;
    private Context mContext;

    public ReadWordListTask(ListView listView, DBUtil dbUtil, Context context) {
        super();
        this.mListView = listView;
        this.mDBUtil = dbUtil;
        this.mContext = context;
    }

    @Override
    protected List<DictionaryItem> doInBackground(String... strings) {
        List<DictionaryItem> wordList = mDBUtil.getWords(MainActivity.mIsFrench, strings[0]);
        return wordList;
    }

    @Override
    protected void onPostExecute(List<DictionaryItem> strings) {
        super.onPostExecute(strings);

        mListView.setAdapter(new SearchResultListAdapter(mContext, strings));
    }
}
