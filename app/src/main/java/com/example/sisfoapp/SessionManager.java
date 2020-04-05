package com.example.sisfoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;

public class SessionManager {
    public static final String NAMA = "NAMA";
    public static final String NIM = "NIM";
    private  static  final String PREF_NAME = "LOGIN";
    private  static  final String LOGIN = "IS_LOGIN";

    SharedPreferences sharedPreferences;
    public  SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    public  SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }
    public void  createSession(String nama, String nim){
        editor.putBoolean(LOGIN, true);
        editor.putString(NAMA, nama);
        editor.putString(NIM, nim);
        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void  checkLogin(){
        if (!this.isLoggin()){
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((Home) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(NAMA, sharedPreferences.getString(NAMA, null));
        user.put(NIM, sharedPreferences.getString(NIM, null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
        ((Home) context).finish();
    }
}
