package com.example.sisfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Poin extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private Button buttonHome;
    private TextView ynama, ynim;
    SessionManager sessionManager;

    private  String JSON_STRING;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poin);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        buttonHome = findViewById(R.id.buttonHome);
        listView = findViewById(R.id.listView);
        ynama = findViewById(R.id.nama);
        ynim = findViewById(R.id.nim);

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mNama = user.get(sessionManager.NAMA);
        String mNim = user.get(sessionManager.NIM);

        ynama.setText(mNama);
        ynim.setText(mNim);

        buttonHome.setOnClickListener(this);

        getJSON();
    }

    @Override
    public void  onClick(View v) {
        if (v == buttonHome) {
            Intent intent = new Intent(Poin.this, Home.class);
            startActivity(intent);
        }
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;
            HashMap<String, String> user = sessionManager.getUserDetail();
            final String mNim = user.get(sessionManager.NIM);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Poin.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showMahasiswa();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_ALL, mNim);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private  void showMahasiswa(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(konfigurasi.TAG_KET);
                String nama = jo.getString(konfigurasi.TAG_POIN);

                HashMap<String, String> mahasiswa = new HashMap<>();
                mahasiswa.put(konfigurasi.TAG_KET, id);
                mahasiswa.put(konfigurasi.TAG_POIN, nama);
                list.add(mahasiswa);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Poin.this, list, R.layout.list_item,
                new  String[]{konfigurasi.TAG_KET, konfigurasi.TAG_POIN},
                new int[]{R.id.ket, R.id.poin});

        listView.setAdapter(adapter);
    }
}
