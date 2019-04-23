package com.ismealdi.amrestjava.util;

import android.content.Context;
import android.util.Log;

import com.ismealdi.amrestjava.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Al
 * on 22/04/19 | 18:29
 */
public class Networks {

    private Context context;
    private HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Log.d("AmHttp", "AmHttp: ," + message);
        }
    });

    public Networks(Context context) {
        this.context = context;
    }

    public Retrofit bridge() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.connectTimeout(120, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder ongoing = chain.request().newBuilder();
                
                String token = new Preferences(context).getToken();

                if (!token.isEmpty()) {
                    ongoing.addHeader("Authorization", "Bearer " + token);
                }

                return chain.proceed(ongoing.build());
            }
        });

        httpClient.addInterceptor(logging);
        
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClient.build())
                .build();


    }

}
