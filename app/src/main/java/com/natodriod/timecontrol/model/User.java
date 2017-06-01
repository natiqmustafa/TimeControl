package com.natodriod.timecontrol.model;

import java.util.HashMap;

/**
 * Created by natiqmustafa on 18.03.2017.
 */

public class User {

    private String fullname;
    private String fbUUID;
    private String email;
    private String createAt;
    private String lastLogin;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getFbUUID() {
        return fbUUID;
    }

    public void setFbUUID(String fbUUID) {
        this.fbUUID = fbUUID;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public HashMap<String, Object> getHashMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("fullname", this.fullname);
        map.put("fbUUID", this.fbUUID);
        map.put("email", this.email);
        map.put("createAt", this.createAt);
        map.put("lastLogin", this.lastLogin);
        return map;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullname='" + fullname + '\'' +
                ", fbUUID='" + fbUUID + '\'' +
                ", email='" + email + '\'' +
                ", createAt='" + createAt + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                '}';
    }
}
