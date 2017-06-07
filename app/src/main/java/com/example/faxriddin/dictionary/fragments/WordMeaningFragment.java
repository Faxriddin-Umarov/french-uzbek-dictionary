package com.example.faxriddin.dictionary.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faxriddin.dictionary.MainActivity;
import com.example.faxriddin.dictionary.R;
import com.example.faxriddin.dictionary.model.DictionaryItem;
import com.example.faxriddin.dictionary.util.DBUtil;

/**
 * Created by Faxriddin on 5/19/2017.
 */

public class WordMeaningFragment extends Fragment {

    private DictionaryItem mDictionaryItem;
    private Menu mOptionsMenu;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();

        View view = inflater.inflate(R.layout.word_meaning_fragment, container, false);

        if (args != null) {
            TextView textView1 = (TextView) view.findViewById(R.id.text_view_1);
            TextView textView2 = (TextView) view.findViewById(R.id.text_view_2);
            TextView textView3 = (TextView) view.findViewById(R.id.text_view_3);

            DictionaryItem selectedWord = (DictionaryItem) args.getSerializable(SearchViewFragment.SELECTED_WORD);
            mDictionaryItem = selectedWord;
            if (MainActivity.mIsFrench) {
                textView1.setText(selectedWord.getFrench());
                textView2.setText(selectedWord.getFrenchNormal());
                textView3.setText(selectedWord.getUzbek());
            } else {
                textView1.setText(selectedWord.getUzbek());
                textView2.setText(selectedWord.getFrench());
                textView3.setText(selectedWord.getFrenchNormal());
            }
        }
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mDictionaryItem.getIsFavorite() != 0)
            menu.getItem(0).setIcon(R.drawable.ic_star_border_yellow_24dp);
        mOptionsMenu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_fovorite: {
                UpdateTask updateTask = new UpdateTask();
                updateTask.execute(mDictionaryItem.getId());
            }
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class UpdateTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            DBUtil dbUtil = new DBUtil(getContext());
            dbUtil.setFavorite(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

        }
    }
}
