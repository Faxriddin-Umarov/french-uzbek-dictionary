package com.example.faxriddin.dictionary;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.faxriddin.dictionary.fragments.AddWordFragment;
import com.example.faxriddin.dictionary.fragments.SearchViewFragment;
import com.example.faxriddin.dictionary.fragments.SelectFirstActionFragment;

public class MainActivity extends AppCompatActivity {

    private SearchViewFragment mSearchViewFragment;
    private FragmentTransaction mFragmentTransaction;


    public static volatile boolean mIsFrench;
    private static final int REQUEST_PERMISSIONS_CODE = 0x000001;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
            return;

        SelectFirstActionFragment selectFirstActionFragment = new SelectFirstActionFragment();
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.fragment_container, selectFirstActionFragment).commit();
    }

    public void setSearchFragment() {
        if (hasPermissions()) {
            mSearchViewFragment = new SearchViewFragment();
            mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragmentTransaction.replace(R.id.fragment_container, mSearchViewFragment).commit();
        } else {
            requestPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;
        switch (requestCode) {
            case REQUEST_PERMISSIONS_CODE:
                for (int res : grantResults) {
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                allowed = false;
        }

        if (allowed) {
            mSearchViewFragment = new SearchViewFragment();
            mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragmentTransaction.add(R.id.fragment_container, mSearchViewFragment).commit();
        }
    }

    private void requestPermissions() {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, REQUEST_PERMISSIONS_CODE);
        }
    }

    private boolean hasPermissions() {
        int res;
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for (String permission : permissions) {
            res = checkCallingOrSelfPermission(permission);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                this.finish();
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_word:
                Fragment addWordFragment = new AddWordFragment();
                mFragmentTransaction = null;
                mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container, addWordFragment).addToBackStack("ADD_WORD_FRAGMENT").commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof SearchViewFragment) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }
}
