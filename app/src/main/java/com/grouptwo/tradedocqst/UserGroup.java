package com.grouptwo.tradedocqst;

public class UserGroup {
    int id;
    String userGroup;

    public UserGroup(int id, String userGroup) {
        this.id = id;
        this.userGroup = userGroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}
