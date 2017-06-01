package com.natodriod.timecontrol.model;

/**
 * Created by natiqmustafa on 04.04.2017.
 */

public class SettingsMenu {
    private String settingText;
    private String settingDesc;
    private String settingTag;
    private boolean isGroup;

    public SettingsMenu(String settingText, String settingDesc, String settingTag, boolean isGroup) {
        this.settingText = settingText;
        this.settingDesc = settingDesc;
        this.settingTag = settingTag;
        this.isGroup = isGroup;
    }

    public SettingsMenu() {
    }

    public String getSettingText() {
        return settingText;
    }

    public void setSettingText(String settingText) {
        this.settingText = settingText;
    }

    public String getSettingDesc() {
        return settingDesc;
    }

    public void setSettingDesc(String settingDesc) {
        this.settingDesc = settingDesc;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getSettingTag() {
        return settingTag;
    }

    public void setSettingTag(String settingTag) {
        this.settingTag = settingTag;
    }

    @Override
    public String toString() {
        return "SettingsMenu{" +
                "isGroup=" + isGroup +
                ", settingTag='" + settingTag + '\'' +
                ", settingDesc='" + settingDesc + '\'' +
                ", settingText='" + settingText + '\'' +
                '}';
    }
}