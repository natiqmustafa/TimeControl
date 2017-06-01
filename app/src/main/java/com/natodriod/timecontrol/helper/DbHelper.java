package com.natodriod.timecontrol.helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by natiqmustafa on 05.04.2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String TABLE_DOING_LIST = "doing_list";
    public static final String TABLE_IM_DOING = "im_doing";

    public static final String FIELD_DOING_TEXT = "doing_text";
    public static final String FIELD_STOP_TIME_MILLIS = "stop_time_millis";
    public static final String FIELD_START_TIME_MILLIS = "stat_time_millis";
    public static final String FIELD_START_DATETIME = "start_datetime";




    private static final String TAG = "DbHelper";
    private static String DB_NAME = "tcdb.db";
    private static String DB_PATH = "";

    private SQLiteDatabase mDatabase;

    private Context context;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        init();
    }

    private void init() {
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        File file = new File(DB_PATH + DB_NAME);
        if(file.exists())
            openDataBase(); // Add this line to fix db.insert can't insert values

    }

    private void openDataBase() {
        String myPath = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }


    private boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            File file = new File(myPath);
            if(file.exists())
                tempDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (tempDB != null)
            tempDB.close();
        return tempDB != null;
    }

    public void createDatabase() throws IOException {
        boolean isDBExists = checkDataBase();
        if (!isDBExists) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyDataBase() throws IOException {
        try {
            InputStream myInput = context.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0)
                myOutput.write(buffer, 0, length);

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
