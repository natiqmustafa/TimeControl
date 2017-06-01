package com.natodriod.timecontrol.data;

import android.content.Context;

import com.natodriod.timecontrol.R;
import com.natodriod.timecontrol.model.SettingsMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natiqmustafa on 04.04.2017.
 */

public class SettingsMenuData {
    private static final String TAG = "SettingsMenuData";
    private List<SettingsMenu> settingsMenuList;
    public SettingsMenuData(Context context) {

        String[] arrSettingText  = context.getResources().getStringArray(R.array.arr_settings);
        String[] arrSettingDesc  = context.getResources().getStringArray(R.array.arr_settings_desc);
        String[] arrSettingTag  = context.getResources().getStringArray(R.array.arr_settings_tag);
        int lng = (arrSettingText.length > arrSettingDesc.length) ? arrSettingText.length : arrSettingDesc.length;
        settingsMenuList = new ArrayList<>();
        for (int i = 0; i< lng; i++){
//            if (arrSettingTag[i].equals("LANG")) {
//                settingsMenuList.add(new SettingsMenu(arrSettingText[i], arrSettingDesc[i], lang, arrSettingTag[i], false));
//            }else
                settingsMenuList.add(new SettingsMenu(arrSettingText[i], arrSettingDesc[i], arrSettingTag[i], false));
        }
    }

    public List<SettingsMenu> getMenuSettings(){
        return settingsMenuList;
    }
}
