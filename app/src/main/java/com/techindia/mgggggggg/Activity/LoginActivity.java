package com.techindia.mgggggggg.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.techindia.mgggggggg.Model.Data;
import com.techindia.mgggggggg.Model.LoginModel;
import com.techindia.mgggggggg.R;
import com.techindia.mgggggggg.Rest.ApiService;
import com.techindia.mgggggggg.Rest.RetrofitClient;
import com.techindia.mgggggggg.Utils.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText et_email;
    TextInputEditText et_pass;
    Button btn_login;
    LinearLayout liner_forget;

    private ProgressDialog progressDialog;
    private Session preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ini();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                if(email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if (!isValidEmail(email)) {
                    Toast.makeText(LoginActivity.this, "Please Enter a valid email address", Toast.LENGTH_SHORT).show();
                }
                else if(pass.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if(pass.length()<3)
                {
                    Toast.makeText(LoginActivity.this, "Please Minimum 3 digit Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    ApiService apiService = RetrofitClient.getApiService();
                    progressDialog.show(); // Show progress dialog before making the API call

                    Call<LoginModel> call = apiService.login(email, pass);
                    call.enqueue(new Callback<LoginModel>() {
                        @Override
                        public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                progressDialog.dismiss();
                                Log.d("bdbdfdsnnf",response.body().getMsg());
                                LoginModel apiResponse = response.body();
                                if(apiResponse.status==true) {
                                    String token = apiResponse.getToken();
                                    Data data= apiResponse.data;
                                    if(data!=null) {
                                        preferenceManager.saveString("Email", data.email);
                                        preferenceManager.saveString("Name",data.name );
                                        preferenceManager.saveString("token", token);
                                        preferenceManager.saveString("Pan",data.pan.toString() );
                                        preferenceManager.saveString("Mobile", data.mobile.toString());
                                        preferenceManager.saveString("Adahr", data.adhar.toString());
                                        Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                                        startActivity(intent);
                                        finish();
                                        // Process further
                                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, ""+apiResponse.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressDialog.dismiss();
                                // Handle unsuccessful response
                                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginModel> call, Throwable t) {
                            progressDialog.dismiss();
                            // Handle failure
                            Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        liner_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    private void ini() {

        et_email=findViewById(R.id.email);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Regular.ttf");
       
        et_email.setTypeface(typeface);

        et_pass =findViewById(R.id.et_password);
        btn_login= findViewById(R.id.login_btn);
        liner_forget= findViewById(R.id.liner_forget);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        preferenceManager = Session.getInstance(LoginActivity.this);
    }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}