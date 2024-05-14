package com.techindia.mgggggggg.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.techindia.mgggggggg.Contest.AppContest;
import com.techindia.mgggggggg.Model.ForgetPass.Roots;
import com.techindia.mgggggggg.R;
import com.techindia.mgggggggg.Rest.ApiService;
import com.techindia.mgggggggg.Rest.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    TextInputEditText et_email;
    Button btn_forget;

    private ProgressDialog progressDialog;
    LinearLayout liner_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_activity);
        inits();
        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=et_email.getText().toString().trim();
                if(email.isEmpty()) {
                    Toast.makeText(ForgetPasswordActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if (!isValidEmail(email)) {
                    Toast.makeText(ForgetPasswordActivity.this, "Please Enter a valid email address", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ApiService apiService = RetrofitClient.getApiService();
                    progressDialog.show();
                    Call<Roots> call = apiService.forgetPassword(email);
                    call.enqueue(new Callback<Roots>() {
                        @Override
                        public void onResponse(Call<Roots> call, Response<Roots> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                progressDialog.dismiss();

                                Roots apiResponse = response.body();
                                if(apiResponse.status==true) {

                                    Intent intent = new Intent(ForgetPasswordActivity.this, ChangePasswordActivity.class);
                                    intent.putExtra("status", AppContest.flag);
                                    startActivity(intent);

                                }
                                else {
                                    Toast.makeText(ForgetPasswordActivity.this, ""+apiResponse.msg.toString(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressDialog.dismiss();
                                // Handle unsuccessful response
                                Toast.makeText(ForgetPasswordActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Roots> call, Throwable t) {
                            progressDialog.dismiss();
                            // Handle failure
                            Toast.makeText(ForgetPasswordActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });
        liner_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    private void inits() {
        et_email=findViewById(R.id.forget_email);
        btn_forget=findViewById(R.id.forget_btn);
        liner_forget =findViewById(R.id.liner_forget);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

    }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}