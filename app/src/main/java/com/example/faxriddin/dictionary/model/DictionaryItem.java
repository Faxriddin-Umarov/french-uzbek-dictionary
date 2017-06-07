package com.example.faxriddin.dictionary.model;

import java.io.Serializable;

/**
 * Created by Faxriddin on 5/19/2017.
 */

public class DictionaryItem implements Serializable {

    private String mFrench;
    private String mFrenchNormal;
    private String mUzbek;
    private Integer mId;

    public void setId(Integer id) {
        mId = id;
    }

    public void setIsFavorite(Integer isFavorite) {
        mIsFavorite = isFavorite;
    }

    private Integer mIsFavorite;

    public Integer getId() {
        return mId;
    }

    public Integer getIsFavorite() {
        return mIsFavorite;
    }

    public DictionaryItem(String french, String frenchNormal, String uzbek, Integer id, Integer isFavorite) {
        mFrench = french;
        mFrenchNormal = frenchNormal;
        mUzbek = uzbek;
        mId = id;
        mIsFavorite = isFavorite;
    }

    public String getFrench() {
        return mFrench;
    }

    public void setFrench(String french) {
        mFrench = french;
    }

    public String getFrenchNormal() {
        return mFrenchNormal;
    }

    public void setFrenchNormal(String frenchNormal) {
        mFrenchNormal = frenchNormal;
    }

    public String getUzbek() {
        return mUzbek;
    }

    public void setUzbek(String uzbek) {
        mUzbek = uzbek;
    }
}
