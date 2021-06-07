package com.k1d.wave.ui.main;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import org.json.JSONArray;

public class MainViewModel extends ViewModel {

    private JSONArray songs;

    public JSONArray getSongs() {
        return songs;
    }

    public void setSongs(JSONArray songs) {
        this.songs = songs;
        Log.i("mytag", "mainViewModel songs are set to " + songs + ", length " + songs.length());
    }
}