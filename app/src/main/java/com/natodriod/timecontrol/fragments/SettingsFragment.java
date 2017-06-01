package com.natodriod.timecontrol.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.natodriod.timecontrol.R;
import com.natodriod.timecontrol.activities.LoginActivity;
import com.natodriod.timecontrol.adapter.SettingsListAdapter;
import com.natodriod.timecontrol.data.ImDoingData;
import com.natodriod.timecontrol.data.SettingsMenuData;

/**
 * Created by natiqmustafa on 04.04.2017.
 */

public class SettingsFragment  extends Fragment{

    private static final String TAG = "SettingsFragment";
    private ListView listView;
    private SettingsListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        loadListView(view);
        return view;
    }

    private void loadListView(View view) {
        adapter = new SettingsListAdapter(getContext(), R.layout.custom_settings_listview, new SettingsMenuData(getContext()).getMenuSettings());
        listView = (ListView) view.findViewById(R.id.settings_list);
        setListViewListener();
        listView.setAdapter(adapter);
    }


    public void setListViewListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String settingTag = adapter.getItem(i).getSettingTag();
                switch (settingTag) {
                    case "NOTIFICATION":
                        break;
                    case "LANG":
                        break;
                    case "DELETE_ALL":
                       int ret = new ImDoingData(getContext()).deleteAllData();
                        Toast.makeText(getContext(), "Error Code&Count: " + ret, Toast.LENGTH_SHORT).show();
                        break;

                    case "SIGN_OUT":
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        break;
                }
            }
        });
    }
}
