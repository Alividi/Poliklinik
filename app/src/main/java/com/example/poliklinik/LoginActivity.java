package com.example.poliklinik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.poliklinik.dokter.SplashScreenDokterActivity;
import com.example.poliklinik.pasien.PasienMainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    String apiLogin;
    Context contextLogin;
    EditText etUsername;
    EditText etPassword;
    ProgressBar progressBar;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        contextLogin = this;
        loginPreferences = contextLogin.getSharedPreferences("LoginPrefs", 0);
        prefsEditor = loginPreferences.edit();
        TextView tvResetPw = findViewById(R.id.tvResetPassword);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        AppCompatButton btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBarLogin);

        etUsername.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(etUsername.getText().toString().length() == 0){
                    etUsername.setError("Masukan Username");
                    etUsername.setBackgroundResource(R.drawable.et_border_bg_error);
                }else {
                    etUsername.setBackgroundResource(R.drawable.et_border_bg);
                }
                return false;
            }
        });

        etPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(etPassword.getText().toString().equals("")){
                    etPassword.setError("Masukan Password");
                    etPassword.setBackgroundResource(R.drawable.et_border_bg_error);
                }else{
                    etPassword.setBackgroundResource(R.drawable.et_border_bg);
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiLogin = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_user_by_username?username=";
                apiLogin = apiLogin + etUsername.getText().toString();
                if(etUsername.getText().toString().length() == 0){
                    etUsername.setError("Masukan Username");
                    etUsername.setBackgroundResource(R.drawable.et_border_bg_error);
                }else if(etPassword.getText().toString().equals("")){
                    etPassword.setError("Masukan Password");
                    etPassword.setBackgroundResource(R.drawable.et_border_bg_error);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    getDataApi();
                }
            }
        });

        tvResetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://akun.ipb.ac.id/Resetpassword";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }
    private void getDataApi() {
        RequestQueue queue = Volley.newRequestQueue(contextLogin);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj;
                        String id;
                        String etPw;
                        String nama;
                        String aktor;
                        String password;
                        String no_telp;
                        String sip;
                        String nim;
                        String alamat;
                        String bb;
                        String tb;

                        try {
                            obj = new JSONObject(response);

                            etPw = etPassword.getText().toString();
                            id = obj.getString("_id");
                            nama = obj.getString("nama");
                            aktor = obj.getString("aktor");
                            password = obj.getString("password");

                            prefsEditor.putString("user_id",id);
                            prefsEditor.putString("nama",nama);
                            prefsEditor.putString("aktor",aktor);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        if (etPw.equals(password)){
                            if (aktor.equals("dokter")){
                                Intent intent = new Intent(contextLogin, SplashScreenDokterActivity.class);
                                intent.putExtra("nama",nama);
                                progressBar.setVisibility(View.GONE);

                                try {
                                    no_telp = obj.getString("no_telp");
                                    sip = obj.getString("sip");

                                    prefsEditor.putString("no_telp",no_telp);
                                    prefsEditor.putString("sip",sip);
                                    prefsEditor.commit();

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                startActivity(intent);
                            }
                            if (aktor.equals("pasien")){
                                Intent intent = new Intent(contextLogin, PasienMainActivity.class);
                                intent.putExtra("nama",nama);
                                progressBar.setVisibility(View.GONE);

                                try {
                                    nim = obj.getString("nim");
                                    alamat = obj.getString("alamat");
                                    bb = obj.getString("bb");
                                    tb = obj.getString("tb");

                                    prefsEditor.putString("nim",nim);
                                    prefsEditor.putString("alamat",alamat);
                                    prefsEditor.putString("bb",bb);
                                    prefsEditor.putString("tb",tb);
                                    prefsEditor.commit();

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                startActivity(intent);
                            }
                        }else{
                            Toast.makeText(contextLogin, "Username/Password Salah", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(contextLogin, "Cek Jaringan Anda", Toast.LENGTH_SHORT).show();
                Log.e("api", "onErrorResponse: "+ error.getLocalizedMessage());
            }
        });
        queue.add(stringRequest);
    }
}