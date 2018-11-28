package com.example.omar.moviedirectory.Utils;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by OMAR on 10/24/2018.
 */

public class Prefs {
    SharedPreferences sharedPreferences;

    public Prefs(Activity activity) {
        sharedPreferences = activity.getPreferences(activity.MODE_PRIVATE);
    }
    public void setSearch(String search){
        sharedPreferences.edit().putString("Search" ,search).commit();
    }
    public String getSearch(){
        return sharedPreferences.getString("Search" , "Batman");
    }
}
