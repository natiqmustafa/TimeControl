package com.natodriod.timecontrol.model;

import java.util.HashMap;

/**
 * Created by natiqmustafa on 04.04.2017.
 */

public class ImDoing {
    private String doingText;
    private int imDoingId;
    private int doingListId;
    private String startDateTime;
    private String stopDateTime;
    private long startTimeMillis;
    private long stopTimeMillis;
    private boolean isFinish;


    private float remaining;

    public int getImDoingId() {
        return imDoingId;
    }

    public void setImDoingId(int imDoingId) {
        this.imDoingId = imDoingId;
    }

    public int getDoingListId() {
        return doingListId;
    }

    public void setDoingListId(int doingListId) {
        this.doingListId = doingListId;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public long getStopTimeMillis() {
        return stopTimeMillis;
    }

    public void setStopTimeMillis(long stopTimeMillis) {
        this.stopTimeMillis = stopTimeMillis;
    }

    public String getStopDateTime() {
        return stopDateTime;
    }

    public void setStopDateTime(String stopDateTime) {
        this.stopDateTime = stopDateTime;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public long getDuration(){
        long t = this.stopTimeMillis - this.startTimeMillis;
        return t > 0 ? t : 0;
    }

    public float getRemaining() {
        return remaining;
    }

    public void setRemaining(float remaining) {
        this.remaining = remaining;
    }

    public String getDoingText() {
        return doingText;
    }

    public void setDoingText(String doingTest) {
        this.doingText = doingTest;
    }

    public HashMap<String, Object> getHashMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("imDoingId", this.imDoingId);
        map.put("doingListId", this.doingListId);
        map.put("startDateTime", this.startDateTime);
        map.put("stopDateTime", this.stopDateTime);
        map.put("startTimeMillis", this.startTimeMillis);
        map.put("stopTimeMillis", this.stopTimeMillis);
        map.put("isFinish", this.isFinish);
//        map.put("pushKey", this.pushKey);
        return map;
    }

    @Override
    public String toString() {
        return "ImDoing{" +
                "doingText='" + doingText + '\'' +
                ", imDoingId=" + imDoingId +
                ", doingListId=" + doingListId +
                ", startDateTime='" + startDateTime + '\'' +
                ", stopDateTime='" + stopDateTime + '\'' +
                ", startTimeMillis=" + startTimeMillis +
                ", stopTimeMillis=" + stopTimeMillis +
                ", isFinish=" + isFinish +
                ", remaining=" + remaining +
                '}';
    }
}

