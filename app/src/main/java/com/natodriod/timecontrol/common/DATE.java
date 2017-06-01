package com.natodriod.timecontrol.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by natiqmustafa on 13.03.2017.
 */

public class DATE {
    public static SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    public static String toData(long millis){
        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)) % 24;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second);
    }

    public static String now(){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Date today = new Date();
        return formatter.format(today);
    }

    public static String today(){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date today = new Date();
        return formatter.format(today);
    }

}
