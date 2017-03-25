package bl.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import bl.entities.UserInfo;

/**
 * Created by Daniel_m on 25/03/2017.
 */

public class SharedPreferencesHandler {

    public static final String DB_NAME = "DATA";
    public static final String DB_TABLE = "EVENTS";
    public static final String DB_INFORMATION = "INFO";
    public static final String DB_LAST_ID = "GENERAL_LAST_ID";
    public static final String DB_USER_INFO = "USER_INFO";

    public static void saveData(Context context, UserEvents userEvents){
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences(DB_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userEvents);
        editor.putString(DB_TABLE, json);
        editor.apply();
    }


    public static UserEvents getData(Context context){
        UserEvents ue;
        try{
            SharedPreferences scoresDB = context.getApplicationContext().getSharedPreferences(DB_NAME, 0);
            String json = scoresDB.getString(DB_TABLE,null);
            if(json==null){
                ue = new UserEvents();
                saveData(context,ue);
            }
            else{
                Gson gson = new Gson();
                ue = gson.fromJson(json, UserEvents.class);
                return ue;
            }
        }
        catch(Exception e){
            ue = new UserEvents();
            saveData(context,ue);
        }
        return ue;
    }

    public static long getNextGeneralID(Context context){
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences(DB_INFORMATION, 0);
        Long lastId = sharedPref.getLong(DB_LAST_ID,0);
        lastId++;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(DB_LAST_ID, lastId);
        return  lastId;
    }

    public static void saveUserInfo(Context context, UserInfo info){
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences(DB_INFORMATION, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(info);
        editor.putString(DB_USER_INFO, json);
        editor.apply();
    }

    public static UserInfo getUserInfo(Context context){
        UserInfo ui;
        try{
            SharedPreferences scoresDB = context.getApplicationContext().getSharedPreferences(DB_INFORMATION, 0);
            String json = scoresDB.getString(DB_USER_INFO,null);
            if(json==null){
                ui = new UserInfo();
                saveUserInfo(context,ui);
            }
            else{
                Gson gson = new Gson();
                ui = gson.fromJson(json, UserInfo.class);
            }
        }
        catch(Exception e){
            ui = new UserInfo();
            saveUserInfo(context,ui);
        }
        return ui;
    }
}
