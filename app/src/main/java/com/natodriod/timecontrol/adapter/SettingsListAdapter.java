package com.natodriod.timecontrol.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.natodriod.timecontrol.R;
import com.natodriod.timecontrol.model.SettingsMenu;

import java.util.List;

/**
 * Created by natiqmustafa on 04.04.2017.
 */

public class SettingsListAdapter extends ArrayAdapter<SettingsMenu> {
    private static final String TAG = "SettingsListAdapter";
    public SettingsListAdapter(Context context, int resource, List<SettingsMenu> items) {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_settings_listview, null);
        }
        SettingsMenu settingsMenu = getItem(position);
        TextView tvSettingsText = (TextView) v.findViewById(R.id.settings_item_text);
        TextView tvSettingsDesc = (TextView) v.findViewById(R.id.settings_item_desc);
        tvSettingsText.setText(settingsMenu.getSettingText());
        tvSettingsDesc.setText(settingsMenu.getSettingDesc());
        return v;
    }

    @Nullable
    @Override
    public SettingsMenu getItem(int position) {
        return super.getItem(position);
    }
}
