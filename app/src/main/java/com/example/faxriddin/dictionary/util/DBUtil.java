package com.example.faxriddin.dictionary.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.faxriddin.dictionary.model.DictionaryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faxriddin on 5/18/2017.
 */

public class DBUtil extends SQLiteOpenHelper {

    public static final String DB_NAME = "db_fr_uz.db";
    public static final String DB_LOCATION = "/data/data/com.example.faxriddin.dictionary/databases/";
    private static final String TABLE_NAME = "dict_fr_uz";
    private static final String TAG = "DBUtil";
    private static final String IS_FAVORITE = "IS_FAVORITE";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DBUtil(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DB_NAME).getPath();

        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public List<DictionaryItem> getWords(boolean isFrench, String searchString) {
        String columnName;

        if (isFrench) {
            columnName = "FRENCH";
        } else {
            columnName = "UZBEK";
        }

        List<DictionaryItem> resultWordList = new ArrayList<>();
        openDatabase();
        String queryString = String.format("SELECT * FROM %s WHERE %s.%s LIKE \'%s%%\'", TABLE_NAME, TABLE_NAME, columnName, searchString);
        Cursor cursor = mDatabase.rawQuery(queryString, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            resultWordList.add(new DictionaryItem(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3), cursor.getInt(4)));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return resultWordList;
    }

    public void setFavorite(Integer id) {
        openDatabase();
        String queryString = String.format("UPDATE %s SET IS_FAVORITE = 1 WHERE ID = %d", TABLE_NAME, id);
        Cursor cursor = mDatabase.rawQuery(queryString, null);
        cursor.moveToFirst();
        cursor.close();
        closeDatabase();
    }

    public List<DictionaryItem> getFavorites() {
        List<DictionaryItem> favoriteWords = new ArrayList<>();
        String queryString = String.format("SELECT * FROM %s WHERE %s = %d", TABLE_NAME, IS_FAVORITE, 1);
        openDatabase();
        Cursor cursor = mDatabase.rawQuery(queryString, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            favoriteWords.add(new DictionaryItem(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3), cursor.getInt(4)));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return favoriteWords;
    }

    public void addNewWord(String inFrench, String inUzbek) {
        String queryString = "";
        // TODO: 5/30/2017 Write adding new word source code
    }

    public void closeDatabase() {
        if (mDatabase != null)
            mDatabase.close();
    }
}
