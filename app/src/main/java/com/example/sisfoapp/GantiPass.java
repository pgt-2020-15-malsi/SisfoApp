package com.example.sisfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class GantiPass extends AppCompatActivity implements View.OnClickListener {
    private TextView nama, nim;
    private EditText editLama, editBaru, editKonfir;
    private Button buttonBatal, buttonGantiPass;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_pass);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        nama = findViewById(R.id.nama);
        nim = findViewById(R.id.nim);
        editLama = findViewById(R.id.gantiPassLama);
        editBaru = findViewById(R.id.gantiPassBaru);
        editKonfir = findViewById(R.id.gantiKonfirmPassBaru);
        buttonBatal = findViewById(R.id.buttonBatal);
        buttonGantiPass = findViewById(R.id.buttonGPass);

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mNama = user.get(sessionManager.NAMA);
        String mNim = user.get(sessionManager.NIM);

        nama.setText(mNama);
        nim.setText(mNim);

        buttonGantiPass.setOnClickListener(this);
        buttonBatal.setOnClickListener(this);

    }

    @Override
    public void  onClick(View v) {
        if (v == buttonBatal) {
            Intent intent = new Intent(GantiPass.this, Profil.class);
            HashMap<String, String> user = sessionManager.getUserDetail();
            String mNim = user.get(sessionManager.NIM);
            intent.putExtra(konfigurasi.EMP_NIM, mNim);
            startActivity(intent);
        }
        if (v == buttonGantiPass){
            confirmGantiPass();
        }
    }

    public void confirmGantiPass(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Yakin Ingin Mengganti?");

        alertDialogBuilder.setPositiveButton("ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                updatePassword();
                startActivity(new Intent(GantiPass.this, Profil.class));
            }
        });
        alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void updatePassword(){
        final String passLama = editLama.getText().toString().trim();
        final String passBaru = editBaru.getText().toString().trim();
        final String passKonfirBaru = editKonfir.getText().toString().trim();

        HashMap<String, String> user = sessionManager.getUserDetail();
        final String mNim = user.get(sessionManager.NIM);

        class UpdateMahasiswa extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GantiPass.this, "Mengganti Password .....", "tunggu ....", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(GantiPass.this, s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_EMP_NIM, mNim);
                hashMap.put(konfigurasi.KEY_EMP_PASSWORD_LAMA, passLama);
                hashMap.put(konfigurasi.KEY_EMP_PASSWORD_BARU, passBaru);
                hashMap.put(konfigurasi.KEY_EMP_PASSWORD_KONFIR_BARU, passKonfirBaru);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_EMP, hashMap);
                return s;
            }
        }

        UpdateMahasiswa um = new UpdateMahasiswa();
        um.execute();
    }


}
