package com.example.faxriddin.dictionary.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.faxriddin.dictionary.R;
import com.example.faxriddin.dictionary.util.DBUtil;

/**
 * Created by Faxriddin on 5/30/2017.
 */

public class AddWordFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_word_fragment, container, false);

        final EditText editTextFrench = (EditText) v.findViewById(R.id.editTextFrench);
        final EditText editTextUzbek = (EditText) v.findViewById(R.id.editTextUzbek);

        FloatingActionButton addNewWordActionButton = (FloatingActionButton) v.findViewById(R.id.add_new_word_fab);
        addNewWordActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextFrench.getText().length() == 0 || editTextUzbek.getText().length() == 0) {
                    Toast.makeText(getActivity(), "Avval so'zlarni kiriting", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });

        return v;
    }

    private class AddWordTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            DBUtil util = new DBUtil(getContext());
            util.addNewWord(params[0], params[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_SHORT).show();
        }
    }

}
