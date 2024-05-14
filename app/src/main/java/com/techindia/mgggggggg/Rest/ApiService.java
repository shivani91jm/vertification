package com.techindia.mgggggggg.Rest;

import com.techindia.mgggggggg.Model.ForgetPass.Roots;
import com.techindia.mgggggggg.Model.Home.Root;
import com.techindia.mgggggggg.Model.JobList.JobList;
import com.techindia.mgggggggg.Model.LoginModel;
import com.techindia.mgggggggg.Urls.BaseUrlClass;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;

import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @FormUrlEncoded
    @POST(BaseUrlClass.login_url)
    Call<LoginModel> login(
            @Field("email") String email,
            @Field("password") String password
    );
     @FormUrlEncoded
    @POST(BaseUrlClass.forgetPassword_url)
    Call<Roots> forgetPassword(@Field("email") String password);
  @FormUrlEncoded
    @POST(BaseUrlClass.changePassword_url)
    Call<Roots> changePassword(@FieldMap HashMap<String, String>  job_status);


    @POST(BaseUrlClass.home_url)
    Call<Root> homePage();


    @FormUrlEncoded
    @POST(BaseUrlClass.jobList_url)
   Call<JobList> jobListPage(@FieldMap HashMap<String, String>  job_status);





}

