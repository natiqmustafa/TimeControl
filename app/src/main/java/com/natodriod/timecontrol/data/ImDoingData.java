package com.natodriod.timecontrol.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.natodriod.timecontrol.helper.DbHelper;
import com.natodriod.timecontrol.model.ImDoing;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by natiqmustafa on 10.04.2017.
 */

public class ImDoingData {
    private static final String TAG = "ImDoingData";
    private Context context;
    private DbHelper dbHelper;

    public ImDoingData(Context context) {
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

    public int addNewImDoing(ImDoing doing) {

        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            return 0;
        String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("doing_list_id", doing.getDoingListId());
            contentValues.put("is_finish", doing.isFinish());
            contentValues.put("start_datetime", doing.getStartDateTime());
            contentValues.put("stat_time_millis", doing.getStartTimeMillis());
            contentValues.put("fb_uuid", uuid);
            return (int) db.insert(DbHelper.TABLE_IM_DOING, null, contentValues);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (db != null)
                db.close();
        }
        return 0;
    }

    public int updateImDoing(ImDoing doing) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            return -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("is_finish", doing.isFinish());
            contentValues.put("stop_datetime", doing.getStopDateTime());
            contentValues.put("stop_time_millis", doing.getStopTimeMillis());
            return db.update(DbHelper.TABLE_IM_DOING, contentValues, "doing_list_id=?", new String[]{String.valueOf(doing.getDoingListId())});
            //Log.d(TAG, "saveDoingList: insetId = " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        finally {
            if (db != null)
                db.close();
        }
    }

    public int deleteAllData(){
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            return -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            return db.delete(DbHelper.TABLE_IM_DOING, "fb_uuid=?", new String[]{uuid});
        }catch (Exception e){
            e.printStackTrace();
            return -2;
        }
        finally {
            if (db != null)
                db.close();
        }
    }



    public Cursor getDoneList(String date){
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            return null;
        String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String sql = "select im_doing_id _id, doing_text, stop_time_millis, stat_time_millis," +
                " start_datetime\n" +
                " from im_doing imd \n" +
                " inner join doing_list dl on imd.doing_list_id = dl.doing_list_id\n" +
                " where imd.fb_uuid = ? and imd.start_datetime like ?";

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{

            return db.rawQuery(sql, new String[]{uuid, date+"%"});
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
//        finally {
//            if (db != null)
//                db.close();
//        }
    }


    public Map<String, Long> getMap4Pie(String date){
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            return null;
        String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String sql = "select doing_text, stop_time_millis, stat_time_millis" +
                " from im_doing imd \n" +
                " inner join doing_list dl on imd.doing_list_id = dl.doing_list_id\n" +
                " where imd.fb_uuid = ? and imd.start_datetime like ?";

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            Cursor c = db.rawQuery(sql, new String[]{uuid, date+"%"});

            if (c.moveToFirst()){
                Map<String, Long> map = new HashMap();
                do{
                    String text = c.getString(c.getColumnIndexOrThrow("doing_text"));
                    long duration = c.getLong(c.getColumnIndexOrThrow("stop_time_millis")) -
                            c.getLong(c.getColumnIndexOrThrow("stat_time_millis"));

                    if(duration < 0)
                        duration = 0;

                    Log.d(TAG, "getMap4Pie: " + duration);
                    if (map.containsKey(text)){
                        long d = map.get(text);
                        map.put(text, d + duration);
                    }else {
                        map.put(text, duration);
                    }
                }while (c.moveToNext());
                return map;
            }
            return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        finally {
            if (db != null)
                db.close();
        }
    }
}
