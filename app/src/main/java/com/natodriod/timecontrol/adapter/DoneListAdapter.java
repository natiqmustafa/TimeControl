package com.natodriod.timecontrol.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.natodriod.timecontrol.R;
import com.natodriod.timecontrol.common.DATE;
import com.natodriod.timecontrol.helper.DbHelper;

/**
 * Created by natiqmustafa on 10.04.2017.
 */

public class DoneListAdapter extends CursorAdapter {
    public DoneListAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.custom_done_list, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvDoingText = (TextView) view.findViewById(R.id.tv_doing_text);
        TextView tvDuration = (TextView) view.findViewById(R.id.tv_duration);
        TextView tvStartDate = (TextView) view.findViewById(R.id.tv_start_date);

        try {
            if (cursor != null) {
                String doingText = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.FIELD_DOING_TEXT));
                String startDatetime = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.FIELD_START_DATETIME));
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.FIELD_STOP_TIME_MILLIS)) -
                        cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.FIELD_START_TIME_MILLIS));

                tvDoingText.setText(doingText);
                tvStartDate.setText(startDatetime);

                if(duration <= 0)
                    tvDuration.setText(context.getString(R.string.inprocess));
                else
                    tvDuration.setText(DATE.toData(duration));


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
