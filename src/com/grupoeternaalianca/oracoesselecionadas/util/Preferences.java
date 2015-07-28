package com.grupoeternaalianca.oracoesselecionadas.util;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by cleiton.dantas on 28/07/2015.
 */
public class Preferences {
    private String preferenceName = "Preferences";
    SharedPreferences settings;
    public Preferences(Context context){
         settings = context.getSharedPreferences(preferenceName, 0);
    }


    public SharedPreferences sharedPreferences(){
      return settings;
    }

    public int getFontSize(String key,int defalt){
        return settings.getInt(key,defalt);
    }
}
