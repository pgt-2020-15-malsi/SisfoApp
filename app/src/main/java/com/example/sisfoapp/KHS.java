package com.example.sisfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.badge.BadgeUtils;

import java.util.HashMap;

public class KHS extends AppCompatActivity implements View.OnClickListener{
    private TextView nama, nim;

    private Button buttonSmt1, buttonSmt2, buttonSmt3, buttonSmt4, buttonSmt5;
    private Button buttonHome;

    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_h_s);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        nama = findViewById(R.id.nama);
        nim = findViewById(R.id.nim);

        buttonSmt1 = findViewById(R.id.buttonSmt1);
        buttonSmt2 = findViewById(R.id.buttonSmt2);
        buttonSmt3 = findViewById(R.id.buttonSmt3);
        buttonSmt4 = findViewById(R.id.buttonSmt4);
        buttonSmt5 = findViewById(R.id.buttonSmt5);
        buttonHome = findViewById(R.id.buttonHome);

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mNama = user.get(sessionManager.NAMA);
        String mNim = user.get(sessionManager.NIM);

        nama.setText(mNama);
        nim.setText(mNim);

        buttonSmt1.setOnClickListener(this);
        buttonSmt2.setOnClickListener(this);
        buttonSmt3.setOnClickListener(this);
        buttonHome.setOnClickListener(this);

    }

    @Override
    public void  onClick(View v) {
        if (v == buttonSmt1) {
            Intent intent = new Intent(KHS.this, Semester1.class);
            HashMap<String, String> user = sessionManager.getUserDetail();
            String mNim = user.get(sessionManager.NIM);
            intent.putExtra(konfigurasi.EMP_NIM, mNim);
            startActivity(intent);
        }

        if (v == buttonSmt2) {
            Intent intent = new Intent(KHS.this, Semester2.class);
            HashMap<String, String> user = sessionManager.getUserDetail();
            String mNim = user.get(sessionManager.NIM);
            intent.putExtra(konfigurasi.EMP_NIM, mNim);
            startActivity(intent);
        }

        if (v == buttonSmt3) {
            Intent intent = new Intent(KHS.this, Semester3.class);
            HashMap<String, String> user = sessionManager.getUserDetail();
            String mNim = user.get(sessionManager.NIM);
            intent.putExtra(konfigurasi.EMP_NIM, mNim);
            startActivity(intent);
        }

        if (v == buttonHome) {
            Intent intent = new Intent(KHS.this, Home.class);
            startActivity(intent);
        }
    }
}
