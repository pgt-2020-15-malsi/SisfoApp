package com.example.sisfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Profil extends AppCompatActivity implements View.OnClickListener{
    SessionManager sessionManager;
    private TextView textViewNim;
    private TextView textViewNama;
    private TextView textViewJK;
    private TextView textViewProdi;
    private TextView textViewLahir;
    private TextView textViewTanggal;
    private TextView textViewMasuk;
    private TextView textViewPA;

    private Button buttonGPass;
    private Button buttonHome;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        
        textViewNim = findViewById(R.id.textViewNim);
        textViewNama = findViewById(R.id.textViewNama);
        textViewJK = findViewById(R.id.textViewJK);
        textViewProdi = findViewById(R.id.textViewProdi);
        textViewLahir = findViewById(R.id.textViewTempatLahir);
        textViewTanggal = findViewById(R.id.textViewTanggalLahir);
        textViewMasuk = findViewById(R.id.textViewTahunMasuk);
        textViewPA = findViewById(R.id.textViewPA);

        buttonGPass = findViewById(R.id.buttonGPass);
        buttonHome = findViewById(R.id.buttonHome);

        buttonGPass.setOnClickListener(this);
        buttonHome.setOnClickListener(this);

        getMahasiswa();
    }

    @Override
    public void  onClick(View v){
        if (v == buttonHome) {
            Intent intent = new Intent(Profil.this, Home.class);
            startActivity(intent);
        }
        if (v == buttonGPass) {
            Intent intent = new Intent(Profil.this, GantiPass.class);
            HashMap<String, String> user = sessionManager.getUserDetail();
            String mNim = user.get(sessionManager.NIM);
            intent.putExtra(konfigurasi.EMP_NIM, mNim);
            startActivity(intent);
        }
    }

    private void getMahasiswa() {
        class GetMahasiswa extends AsyncTask<Void, Void, String> {
            HashMap<String, String> user = sessionManager.getUserDetail();
            String mNama = user.get(sessionManager.NAMA);
            String mNim = user.get(sessionManager.NIM);
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Profil.this, "Membuka Data", "Mohon Tunggu...", false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showDetailMahasiswa(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_EMP, mNim);
                return s;
            }
        }
        GetMahasiswa gm = new GetMahasiswa();
        gm.execute();
    }

    private void showDetailMahasiswa(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String nim = c.getString(konfigurasi.TAG_NIM);
            String nama = c.getString(konfigurasi.TAG_NAMA);
            String jeniskelamin = c.getString(konfigurasi.TAG_JENIS_KELAMIN);
            String jurusan = c.getString(konfigurasi.TAG_PROG_STUDI);
            String tempatlahir = c.getString(konfigurasi.TAG_TMP_LAHIR);
            String tanggallahir = c.getString(konfigurasi.TAG_TGL_LAHIR);
            String tahunmasuk = c.getString(konfigurasi.TAG_THN_MASUK);
            String pembimbing = c.getString(konfigurasi.TAG_PEMBIMBING);

            textViewNim.setText(nim);
            textViewNama.setText(nama);
            textViewJK.setText(jeniskelamin);
            textViewProdi.setText(jurusan);
            textViewLahir.setText(tempatlahir);
            textViewTanggal.setText(tanggallahir);
            textViewMasuk.setText(tahunmasuk);
            textViewPA.setText(pembimbing);

    }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
