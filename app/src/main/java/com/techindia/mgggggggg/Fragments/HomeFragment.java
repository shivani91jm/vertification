package com.techindia.mgggggggg.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.techindia.mgggggggg.Activity.ChangePasswordActivity;
import com.techindia.mgggggggg.Activity.DashBoardActivity;
import com.techindia.mgggggggg.Activity.LoginActivity;
import com.techindia.mgggggggg.Adpter.HomeAdpterClass;
import com.techindia.mgggggggg.Contest.MyAlertDialog;
import com.techindia.mgggggggg.MainActivity;
import com.techindia.mgggggggg.Model.Data;
import com.techindia.mgggggggg.Model.Home.Datasss;
import com.techindia.mgggggggg.Model.Home.Root;

import com.techindia.mgggggggg.R;
import com.techindia.mgggggggg.Rest.ApiService;
import com.techindia.mgggggggg.Rest.RetrofitClient;
import com.techindia.mgggggggg.Utils.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeFragment extends Fragment {


    View view;
    private ProgressDialog progressDialog;
    private Session preferenceManager;
    TextView tv_totalJob,tv_open,tv_notassign,tv_request,tv_close,tv_timeout;
    TextView tv_name,tv_mob,tv_pan,tv_adhar,tv_email;
    Toolbar toolbar;
    private LinearLayout liner_logout;
    TextView tv_change_pass;
    public HomeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_home, container, false);

       inits(getContext());

        tv_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
       fetchApi();
       return view;
    }

    private void fetchApi() {
        Retrofit apiService = RetrofitClient.getRetrofitInstance(getContext());
       ApiService service= apiService.create(ApiService.class);
        progressDialog.show();
        Call<Root> call = service.homePage();
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    Log.d("bdbdfdsnnf222",response.body().msg);
                    Root apiResponse = response.body();
                    if(apiResponse.status==true) {
                        Datasss datasss=apiResponse.data;
                        if(datasss!=null)
                        {
                            tv_close.setText(""+datasss.job_details.close);
                            tv_open.setText(""+datasss.job_details.open);
                            tv_notassign.setText(""+datasss.job_details.notAssign);
                            tv_timeout.setText(""+datasss.job_details.timeout);
                            tv_totalJob.setText(""+datasss.job_details.total);
                            tv_name.setText(""+datasss.name);

                            if(datasss.email.contains("@"))
                            {
                                String[] parts = datasss.email.split("@");
                                String maskedEmail = "XXXXX" + "@" + parts[1];
                                tv_email.setText(""+maskedEmail);

                            }
                            if(datasss.mobile!=null || datasss.mobile!="")
                            {
                                String maskedNumber = "XXXXXXXX" + datasss.mobile.substring(datasss.mobile.length() - 2);
                                tv_mob.setText(""+maskedNumber);
                            }


                            tv_adhar.setText(""+datasss.adhar);
                            tv_pan.setText(""+datasss.pan);

                        }



                    }
                    else {
                        Toast.makeText(getContext(), ""+apiResponse.msg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    // Handle unsuccessful response
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inits(Context context) {
        toolbar=view.findViewById(R.id.toolbar);
        tv_close=view.findViewById(R.id.tv_close);
        tv_totalJob=view.findViewById(R.id.tv_totaljob);
        tv_notassign=view.findViewById(R.id.tv_notassign);
        tv_request=view.findViewById(R.id.tv_requestedjob);
        tv_open=view.findViewById(R.id.tv_open);
        tv_timeout=view.findViewById(R.id.tv_timeout);
        tv_email=view.findViewById(R.id.tv_email);
        tv_name=view.findViewById(R.id.tv_name);
        tv_mob=view.findViewById(R.id.tv_mobile);
        tv_pan=view.findViewById(R.id.tv_pan);
        tv_adhar=view.findViewById(R.id.tv_adhar);
        liner_logout=view.findViewById(R.id.liner_logout_home);
        tv_change_pass=view.findViewById(R.id.tv_change_pass);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        preferenceManager = Session.getInstance(context);
        liner_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlertDialog.showAlert(getContext(),
                        "Logout",
                        "Are you sure to logout",
                        "Ok",
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Session session = Session.getInstance(getContext());
                                session.clear();
                                Intent intent=new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();

                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            }
        });

    }
}