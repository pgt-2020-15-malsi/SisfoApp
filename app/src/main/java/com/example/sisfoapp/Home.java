package com.example.sisfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class Home extends AppCompatActivity implements View.OnClickListener{
    private TextView nama, nim;
    private Button btn_logout;
    private ImageView imgProfil, imgKhs,imgPoin, imgKonsul, imgRekap, imgDosen;


    SessionManager sessionManager;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        nama = findViewById(R.id.nama);
        nim = findViewById(R.id.nim);
        btn_logout = findViewById(R.id.btn_logout);
        imgProfil = findViewById(R.id.imgProfil);
        imgKhs = findViewById(R.id.imgKHS);
        imgPoin = findViewById(R.id.imgPoin);
        imgRekap = findViewById(R.id.imgRekap);
        imgDosen = findViewById(R.id.imgDosen);
        imgKonsul = findViewById(R.id.imgKonsul);


        HashMap<String, String> user = sessionManager.getUserDetail();
        String mNama = user.get(sessionManager.NAMA);
        String mNim = user.get(sessionManager.NIM);

        nama.setText(mNama);
        nim.setText(mNim);

        btn_logout.setOnClickListener(this);
        imgProfil.setOnClickListener(this);
        imgKhs.setOnClickListener(this);
        imgPoin.setOnClickListener(this);
        imgRekap.setOnClickListener(this);
        imgDosen.setOnClickListener(this);
        imgKonsul.setOnClickListener(this);


    }
    @Override
    public void  onClick(View v) {
        if (v == btn_logout) {
            sessionManager.logout();
        }
        if (v == imgProfil) {
            Intent intent = new Intent(Home.this, Profil.class);
            HashMap<String, String> user = sessionManager.getUserDetail();
            String mNim = user.get(sessionManager.NIM);
            intent.putExtra(konfigurasi.EMP_NIM, mNim);
            startActivity(intent);
        }
        if (v == imgKhs) {
            Intent intent = new Intent(Home.this, KHS.class);
            HashMap<String, String> user = sessionManager.getUserDetail();
            String mNim = user.get(sessionManager.NIM);
            intent.putExtra(konfigurasi.EMP_NIM, mNim);
            startActivity(intent);
        }
        if (v == imgPoin) {
            Intent intent = new Intent(Home.this, Poin.class);
            HashMap<String, String> user = sessionManager.getUserDetail();
            String mNim = user.get(sessionManager.NIM);
            intent.putExtra(konfigurasi.EMP_NIM, mNim);
            startActivity(intent);
        }
        if (v == imgRekap) {
            Intent intent = new Intent(Home.this, RekapNilai.class);
            HashMap<String, String> user = sessionManager.getUserDetail();
            String mNim = user.get(sessionManager.NIM);
            intent.putExtra(konfigurasi.EMP_NIM, mNim);
            startActivity(intent);
        }
        if (v == imgDosen) {
            Intent intent = new Intent(Home.this, EvaluasiDosen.class);
            HashMap<String, String> user = sessionManager.getUserDetail();
            String mNim = user.get(sessionManager.NIM);
            intent.putExtra(konfigurasi.EMP_NIM, mNim);
            startActivity(intent);
        }
        if (v == imgKonsul) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto: pembimbing@poltek-gt.ac.id" ));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Perihal Konsultasi");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Yang terhormat Bpk/Ibu pembimbing");
            startActivity(Intent.createChooser(emailIntent, "KONSULTASI"));
        }

    }
}
