package com.natodriod.timecontrol.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.natodriod.timecontrol.model.ImDoing;

/**
 * Created by natiqmustafa on 04.04.2017.
 */

public class PrefManager {

    private static final String PREF_NAME = "kcontrol_pref";
    private static final int PRIVATE_MODE = 0;

    private static final String PREF_IMDOING_OBJ = "im_doing_obj";
    private static final String PREF_TRANSACTION = "transaction";

    private SharedPreferences preferences;

    public PrefManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }



    public ImDoing getImDoing() {
        String obj = preferences.getString(PREF_IMDOING_OBJ, "");
        if (obj.equals(""))
            return null;

        Gson gson = new Gson();
        return gson.fromJson(obj, ImDoing.class);
    }

    public void setImDoing(ImDoing imDoing) {
        Gson gson = new Gson();
        String json  = gson.toJson(imDoing);
        preferences.edit().putString(PREF_IMDOING_OBJ,json).apply();
    }

    public void clearImDoing(){
        preferences.edit().remove(PREF_IMDOING_OBJ).apply();
    }

    public void setStartTransaction(boolean b){
        preferences.edit().putBoolean(PREF_TRANSACTION, b).apply();
    }

    public boolean isStartTransaction(){
        return preferences.getBoolean(PREF_TRANSACTION, false);
    }
}
