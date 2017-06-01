package com.natodriod.timecontrol.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.natodriod.timecontrol.helper.DbHelper;
import com.natodriod.timecontrol.model.DoingList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by natiqmustafa on 05.04.2017.
 */

public class DoingListData {
    private static final String TAG = "DoingListData";
    private Context context;
    private DbHelper dbHelper;

    public DoingListData(Context context) {
        this.context = context;
        connectDb();
    }

    private void connectDb() {
        dbHelper = new DbHelper(this.context);
        try {
            dbHelper.createDatabase();
        } catch (IOException e) {
            Log.e(TAG, "connectDb: " + e.getMessage());
        }
    }

    public List<DoingList> getDoingList(int limit) {
        List<DoingList> doingLists = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = null;
        try {

            String sql = String.format(Locale.getDefault(), "select doing_list_id, doing_text, " +
                    " remaing from doing_list", limit);

            c = db.rawQuery(sql, null);
            if (c == null)
                return null;

            if (c.moveToFirst()) {
                do {
                    DoingList item = new DoingList();
                    item.setDoingListId(c.getInt(0));
                    item.setText(c.getString(1));
                    item.setRemaing(c.getFloat(2));
                    Log.d(TAG, "getDoingList: " + item);
                    doingLists.add(item);

                } while (c.moveToNext());
            }
            return doingLists;

        } catch (Exception e) {
            Log.e(TAG, "getCountryList: " + e.getMessage());
            return null;
        } finally {
            if (c != null)
                c.close();
            if (db != null)
                db.close();
        }
    }

    public void saveDoingList(DoingList list) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            return;
        String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("doing_text", list.getText());
            contentValues.put("remaing", list.getRemaing());
            contentValues.put("fb_uuid", uuid);
            long id = db.insert(DbHelper.TABLE_DOING_LIST, null, contentValues);
            Log.d(TAG, "saveDoingList: insetId = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (db != null)
                db.close();
        }
    }

    public void updateDoingList(DoingList list){
        Log.d(TAG, "updateDoingList: " + list);
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            return;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("doing_text", list.getText());
            contentValues.put("remaing", list.getRemaing());
            long id = db.update(DbHelper.TABLE_DOING_LIST, contentValues, "doing_list_id = ?",
                    new String[]{String.valueOf(list.getDoingListId())});
            Log.d(TAG, "saveDoingList: insetId = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (db != null)
                db.close();
        }
    }

    public void deleteDoingList(String doingListId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            long id = db.delete(DbHelper.TABLE_DOING_LIST,  "doing_list_id = ?",
                    new String[]{doingListId});
            Log.d(TAG, "saveDoingList: insetId = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (db != null)
                db.close();
        }
    }
}
