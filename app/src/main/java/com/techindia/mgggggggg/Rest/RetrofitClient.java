package com.techindia.mgggggggg.Rest;

import android.content.Context;

import com.techindia.mgggggggg.Urls.BaseUrlClass;
import com.techindia.mgggggggg.Utils.Session;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
        public  Session userSession;
   // private static UserSession userSession;
    private static String token;
    public static ApiService getApiService() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrlClass.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
    public static Retrofit getRetrofitInstance(Context context) {
        Session session=new Session(context);
       token= session.getString("token","");

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " +token)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })

                .readTimeout(80, TimeUnit.SECONDS)
                .writeTimeout(80, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BaseUrlClass.BaseUrl)
              //  .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        return builder.build();
    }


}
