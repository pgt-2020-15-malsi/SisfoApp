package com.example.sisfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class LupaPass extends AppCompatActivity implements View.OnClickListener {
    private EditText editNama, editBaru, editKonfir;
    private Button buttonSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_pass);

        editNama = findViewById(R.id.textNama);
        editBaru = findViewById(R.id.textPassBaru);
        editKonfir = findViewById(R.id.textPassKonfir);
        buttonSimpan = findViewById(R.id.btn_Simpan);

        buttonSimpan.setOnClickListener(this);
    }

    @Override
    public void  onClick(View v) {
        if (v == buttonSimpan){
            updatePassword();
        }

    }

    private void updatePassword(){
        final String Nama = editNama.getText().toString().trim();
        final String passBaru = editBaru.getText().toString().trim();
        final String passKonfirBaru = editKonfir.getText().toString().trim();



        class UpdateMahasiswa extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LupaPass.this, "Mengupdate Password .....", "tunggu ....", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(LupaPass.this, s, Toast.LENGTH_LONG).show();
                startActivity(new Intent(LupaPass.this, MainActivity.class));
            }
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_EMP_NAMA, Nama);
                hashMap.put(konfigurasi.KEY_EMP_PASSWORD_BARU, passBaru);
                hashMap.put(konfigurasi.KEY_EMP_PASSWORD_KONFIR_BARU, passKonfirBaru);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.URL_LUPA_EMP, hashMap);
                return s;
            }

        }

        UpdateMahasiswa um = new UpdateMahasiswa();
        um.execute();
    }
}
