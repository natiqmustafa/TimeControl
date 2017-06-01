package com.natodriod.timecontrol.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.natodriod.timecontrol.R;
import com.natodriod.timecontrol.model.DoingList;

import java.util.List;

/**
 * Created by natiqmustafa on 06.04.2017.
 */

public class DoingListAdapter extends ArrayAdapter<DoingList> {

    private Context context;
    public DoingListAdapter(Context context, @LayoutRes int resource, @NonNull List<DoingList> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(android.R.layout.simple_list_item_activated_2, null);
        }
        DoingList model = getItem(position);
        TextView tvKod = (TextView) v.findViewById(android.R.id.text2);
        TextView tvText = (TextView) v.findViewById(android.R.id.text1);
        tvKod.setText(context.getString(R.string.str_remaining_time, model.getRemaing()));
        tvText.setText(model.getText());
        tvText.setTextSize(16f);

        return v;
    }
}
