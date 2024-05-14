package com.techindia.mgggggggg.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.techindia.mgggggggg.Activity.LoginActivity;
import com.techindia.mgggggggg.Adpter.HomeAdpterClass;
import com.techindia.mgggggggg.Contest.MyAlertDialog;
import com.techindia.mgggggggg.Model.Home.Datasss;
import com.techindia.mgggggggg.Model.Home.Root;
import com.techindia.mgggggggg.Model.JobList.Datum;
import com.techindia.mgggggggg.Model.JobList.JobList;
import com.techindia.mgggggggg.R;
import com.techindia.mgggggggg.Rest.ApiService;
import com.techindia.mgggggggg.Rest.RetrofitClient;
import com.techindia.mgggggggg.Utils.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class JobFragment extends Fragment {

View view;
    RecyclerView rcy_joblistdata;
    private List<Datum> dataList;
    private HomeAdpterClass adapter;
    private ProgressDialog progressDialog;
    private Session preferenceManager;
    private LinearLayout liner_logout;

    public JobFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_job, container, false);
        rcy_joblistdata =view.findViewById(R.id.rcy_joblistdata);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        preferenceManager = Session.getInstance(getContext());
        rcy_joblistdata.setLayoutManager(new LinearLayoutManager(getContext()));
        liner_logout=view.findViewById(R.id.liner_logout);
        dataList = new ArrayList<>();
        adapter = new HomeAdpterClass(getContext(),dataList);
        rcy_joblistdata.setAdapter(adapter);
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
        fetchApi();

        return  view;
    }
    private void fetchApi() {
        Retrofit apiService = RetrofitClient.getRetrofitInstance(getContext());
      ApiService aa=  apiService.create(ApiService.class);
        progressDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("job_status", "all");

        Call<JobList> call = aa.jobListPage(params);
        call.enqueue(new Callback<JobList>() {
            @Override
            public void onResponse(Call<JobList> call, Response<JobList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    Log.d("bdbdfdsnnfbnnnnn",response.body().data.toString());
                    JobList apiResponse = response.body();
                    if(apiResponse.status==true) {

                        dataList.clear(); // Clear existing data
                        dataList.addAll(apiResponse.data);
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(getContext(), ""+apiResponse.msg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    // Handle unsuccessful response
                    Toast.makeText(getContext(), " failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }




}