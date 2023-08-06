package com.pdftron.completereader.WebService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

 // public static final String BASE_URL = "https://www.softcrowd.in/aplimandi/webservices/";
    public static final String IMAGES_BASE_URL = "https://www.aapalimandi.com/aapalimandi/assets/upload/product/";
    public static final String BASE_URL = "https://pustakmarket.com/api/";/*   public static final String IMAGES_BASE_URL = "https://softcrowd.in/aplimandi/assets/upload/product/";
    public static final String BASE_URL = "https://www.softcrowd.in/aplimandi/webservices/";*/
    private static Retrofit retrofit = null;


    public static Retrofit getClient()
    {


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit==null)
        {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(1000, TimeUnit.SECONDS)
                    .readTimeout(1000, TimeUnit.SECONDS).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
