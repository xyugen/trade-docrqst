package com.grouptwo.tradedocqst;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(UserGroup userGroup){
        //save session of user whenever user is logged in
        int id = userGroup.getId();
        String group = userGroup.getUserGroup();

        editor.putInt(SESSION_KEY, id).commit();
        editor.putString("group", group).commit();
    }

    public int getSession(){
        //return user group id whose session is saved
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public String getGroup(){
        return sharedPreferences.getString("group", "No group defined.");
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY, -1).commit();
    }
}
