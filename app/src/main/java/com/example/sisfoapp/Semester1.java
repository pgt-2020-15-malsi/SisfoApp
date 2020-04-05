package com.example.sisfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class Semester1 extends AppCompatActivity implements View.OnClickListener {
    private TextView nama, nim;
    private Button buttonHome,buttonKHS;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester1);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        nama = findViewById(R.id.nama);
        nim = findViewById(R.id.nim);
        buttonKHS = findViewById(R.id.buttonKHS);
        buttonHome = findViewById(R.id.buttonHome);

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mNama = user.get(sessionManager.NAMA);
        String mNim = user.get(sessionManager.NIM);

        nama.setText(mNama);
        nim.setText(mNim);

        buttonKHS.setOnClickListener(this);
        buttonHome.setOnClickListener(this);
    }

    @Override
    public void  onClick(View v) {
        if (v == buttonKHS) {
            Intent intent = new Intent(Semester1.this, KHS.class);
            startActivity(intent);
        }
        if (v == buttonHome) {
            Intent intent = new Intent(Semester1.this, Home.class);
            startActivity(intent);
        }
    }
}
