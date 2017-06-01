package com.natodriod.timecontrol.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by natiqmustafa on 04.04.2017.
 */

public class DoingList implements Serializable {
    private int doingListId;
    private String text;
    private float remaing;


    public DoingList(float remaing, String text) {
        this.text = text;
        this.remaing = remaing;
    }

    public DoingList(int doingListId, String text, float remaing) {
        this.doingListId = doingListId;
        this.text = text;
        this.remaing = remaing;
    }

    public DoingList() {
    }

    public int getDoingListId() {
        return doingListId;
    }

    public void setDoingListId(int doingListId) {
        this.doingListId = doingListId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getRemaing() {
        return remaing;
    }

    public void setRemaing(float remaing) {
        this.remaing = remaing;
    }

    public HashMap<String,Object> getHashMap(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("remaing", this.remaing);
        hashMap.put("text", this.text);
//        hashMap.put("userId", this.userId);
        return hashMap;
    }

    @Override
    public String toString() {
        return "DoingList{" +
                "doingListId=" + doingListId +
                ", text='" + text + '\'' +
                ", remaing=" + remaing +
                '}';
    }
}
