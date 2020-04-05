package com.example.sisfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class RekapNilai extends AppCompatActivity implements View.OnClickListener{
    private TextView nama, nim;
    private Button buttonHome;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap_nilai);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        nama = findViewById(R.id.nama);
        nim = findViewById(R.id.nim);
        buttonHome = findViewById(R.id.buttonHome);

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mNama = user.get(sessionManager.NAMA);
        String mNim = user.get(sessionManager.NIM);

        nama.setText(mNama);
        nim.setText(mNim);

        buttonHome.setOnClickListener(this);
    }

    @Override
    public void  onClick(View v) {
        if (v == buttonHome) {
            Intent intent = new Intent(RekapNilai.this, Home.class);
            startActivity(intent);
        }
    }
}
