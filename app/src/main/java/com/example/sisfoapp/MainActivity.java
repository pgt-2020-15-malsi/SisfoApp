package com.example.sisfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editNim, editPass;
    private TextView txt_lupa;
    private Button btn_login;
    SessionManager sessionManager;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        editNim = findViewById(R.id.editNim);
        editPass = findViewById(R.id.editPass);
        txt_lupa = findViewById(R.id.txt_lupa);
        btn_login = findViewById(R.id.btn_login);

        txt_lupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == txt_lupa) {
                    Intent intent = new Intent(MainActivity.this, LupaPass.class);
                    startActivity(intent);
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = editNim.getText().toString().trim();
                String password = editPass.getText().toString().trim();

                if (!nim.isEmpty() || !password.isEmpty()) {
                    Login(nim, password);
                }  else {
                    editNim.setError("Masukan Nim");
                    editPass.setError("Masukan Password");
                }
            }
        });
    }

    private void Login(final String nim, final String password) {
        loading = ProgressDialog.show(MainActivity.this, "Login", "Melakukan Login...", false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String nama = object.getString("nama").trim();
                                    String nim = object.getString("nim").trim();

                                    sessionManager.createSession(nama, nim);
                                    loading.dismiss();

                                    Intent intent = new Intent(MainActivity.this, Home.class);
                                    intent.putExtra("nama", nama);
                                    intent.putExtra("nim", nim);
                                    startActivity(intent);

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nim", nim);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
