package com.techindia.mgggggggg.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.techindia.mgggggggg.Contest.AppContest;
import com.techindia.mgggggggg.Model.ForgetPass.Roots;
import com.techindia.mgggggggg.Model.LoginModel;
import com.techindia.mgggggggg.R;
import com.techindia.mgggggggg.Rest.ApiService;
import com.techindia.mgggggggg.Rest.RetrofitClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePasswordActivity extends AppCompatActivity {
    TextInputEditText et_email;
    TextInputEditText et_pass;
    Button btn_login;
    String  flag="";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Intent intent=getIntent();
        if(intent!=null)
        {
           flag= intent.getStringExtra("status");
        }
        initss();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass=et_email.getText().toString().trim();
                String conpass=et_pass.getText().toString().trim();
                if(pass.isEmpty())
                {
                    Toast.makeText(ChangePasswordActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if(pass.length()<3)
                {
                    Toast.makeText(ChangePasswordActivity.this, "Minimum 3 digit Password ", Toast.LENGTH_SHORT).show();
                }
                else if(conpass.isEmpty())
                {
                    Toast.makeText(ChangePasswordActivity.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                }
                else if (!pass.equals(conpass)) {
                    Toast.makeText(ChangePasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else {
                    Retrofit apiService = RetrofitClient.getRetrofitInstance(ChangePasswordActivity.this);
                    ApiService service= apiService.create(ApiService.class);
                    progressDialog.show();
                    HashMap<String, String> params = new HashMap<>();
                    params.put("new_password", pass);

                    Call<Roots> call = service.changePassword(params);
                    call.enqueue(new Callback<Roots>() {
                        @Override
                        public void onResponse(Call<Roots> call, Response<Roots> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                progressDialog.dismiss();

                                Roots apiResponse = response.body();
                                if(apiResponse.status==true) {
                                    Intent intent = new Intent(ChangePasswordActivity.this, DashBoardActivity.class);
                                    startActivity(intent);
                                    finish();
                                    // Process further
                                    Toast.makeText(ChangePasswordActivity.this, "Change Password successful!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(ChangePasswordActivity.this, ""+apiResponse.msg.toString(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressDialog.dismiss();
                                // Handle unsuccessful response
                                Toast.makeText(ChangePasswordActivity.this, "failed"+response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Roots> call, Throwable t) {
                            progressDialog.dismiss();
                            // Handle failure
                            Toast.makeText(ChangePasswordActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void initss() {
        et_email=findViewById(R.id.et_conpassword);
        et_pass =findViewById(R.id.et_password);
        btn_login= findViewById(R.id.change_btn);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
    }
}