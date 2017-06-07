package com.example.faxriddin.dictionary.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.faxriddin.dictionary.MainActivity;
import com.example.faxriddin.dictionary.R;

/**
 * Created by Faxriddin on 5/30/2017.
 */

public class SelectFirstActionFragment extends Fragment {
    private String app;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().hide();
        View v = inflater.inflate(R.layout.splash_screen, container, false);

        Button frenchToUzbekButton = (Button) v.findViewById(R.id.french_ouzbek_button);
        Button uzbekToFrenchButton = (Button) v.findViewById(R.id.ouzbek_french_button);

        frenchToUzbekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mIsFrench = true;
                activity.setSearchFragment();
            }
        });

        uzbekToFrenchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mIsFrench = false;
                activity.setSearchFragment();
            }
        });
        return v;
    }
}
