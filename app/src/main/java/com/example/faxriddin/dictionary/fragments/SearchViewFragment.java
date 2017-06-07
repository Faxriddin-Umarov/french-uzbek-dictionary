package com.example.faxriddin.dictionary.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.faxriddin.dictionary.MainActivity;
import com.example.faxriddin.dictionary.R;
import com.example.faxriddin.dictionary.model.DictionaryItem;
import com.example.faxriddin.dictionary.util.DBUtil;
import com.example.faxriddin.dictionary.util.ReadWordListTask;
import com.example.faxriddin.dictionary.util.SearchResultListAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Faxriddin on 5/19/2017.
 */

public class SearchViewFragment extends Fragment {

    public static final String SELECTED_WORD = "com.example.faxriddin.uz_fr_dictionary.selected_word";
    public static final String TAG = "SearchViewFragment";
    private EditText mSearchableWordEditText;
    private ListView mSearchResultListView;
    private DBUtil mDBUtil;
    private ReadWordListTask mReadWordListTask;
    private AppCompatActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mActivity = (AppCompatActivity) getActivity();
        mActivity.getSupportActionBar().show();

        if (MainActivity.mIsFrench) {
            mActivity.getSupportActionBar().setTitle(R.string.fr_to_uz);
        } else {
            mActivity.getSupportActionBar().setTitle(R.string.uz_to_fr);
        }
        View view = inflater.inflate(R.layout.search_word_fragment, container, false);

        mSearchableWordEditText = (EditText) view.findViewById(R.id.searchable_word_edit_text);
        mSearchResultListView = (ListView) view.findViewById(R.id.search_result_list_view);
        mSearchableWordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // TODO: 5/19/2017
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // TODO: 5/19/2017
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() < 2) {
                    mSearchResultListView.setAdapter(null);
                    return;
                }

                if (mReadWordListTask != null)
                    mReadWordListTask.cancel(true);
                if(mDBUtil == null)
                    mDBUtil = new DBUtil(getActivity());

                mReadWordListTask = new ReadWordListTask(mSearchResultListView, mDBUtil, getContext());
                mReadWordListTask.execute(editable.toString());
            }
        });

        mSearchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // get clicked item data context
                DictionaryItem dictionaryItem = (DictionaryItem) mSearchResultListView.getItemAtPosition(i);
                Bundle args = new Bundle();
                args.putSerializable(SELECTED_WORD, dictionaryItem);
                Fragment wordMeaningFragment = new WordMeaningFragment();
                wordMeaningFragment.setArguments(args);
                mActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, wordMeaningFragment).addToBackStack(null).commit();
            }
        });

        // check exists database

        File dataBase = getContext().getDatabasePath(DBUtil.DB_NAME);
        if (!dataBase.exists()) {
            mDBUtil = new DBUtil(getContext());
            //mDBUtil.openDatabase();
            mDBUtil.getReadableDatabase();
            // copy db
            if (copyDatabase(getContext())) {
                Log.w("MainActivity", "onCreate: copy database success");
            } else {
                Log.w("MainActivity", "onCreate: copy database error");
            }
        }

        setHasOptionsMenu(true);

        return view;
    }

    private boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DBUtil.DB_NAME);
            String outFileName = DBUtil.DB_LOCATION + DBUtil.DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity", "copyDatabase: DataBase copied");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_swap_languages:
                swapLanguage();
                return true;
            case R.id.action_fovorite: {
                ReadFavoriteTask readFavoriteTask = new ReadFavoriteTask();
                if (mDBUtil == null)
                    mDBUtil = new DBUtil(getContext());
                readFavoriteTask.execute(mDBUtil);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void swapLanguage() {
        MainActivity.mIsFrench = !MainActivity.mIsFrench;
        if (MainActivity.mIsFrench) {
            mActivity.getSupportActionBar().setTitle(R.string.fr_to_uz);
        } else {
            mActivity.getSupportActionBar().setTitle(R.string.uz_to_fr);
        }
        mSearchableWordEditText.getText().clear();
        mSearchResultListView.setAdapter(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mDBUtil.closeDatabase();
    }

    private class ReadFavoriteTask extends AsyncTask<DBUtil, Void, List<DictionaryItem>>{

        @Override
        protected List<DictionaryItem> doInBackground(DBUtil... params) {
            return params[0].getFavorites();
        }

        @Override
        protected void onPostExecute(List<DictionaryItem> dictionaryItems) {
            Log.d(TAG, "onPostExecute: " + dictionaryItems.size());
            mSearchResultListView.setAdapter(new SearchResultListAdapter(getContext(), dictionaryItems));
        }
    }
}
