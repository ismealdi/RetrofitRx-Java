package com.ismealdi.amrestjava.api;

import android.content.Context;

import com.ismealdi.amrestjava.model.request.SignInRequest;
import com.ismealdi.amrestjava.model.request.SignUpRequest;
import com.ismealdi.amrestjava.model.response.BaseResponse;
import com.ismealdi.amrestjava.model.schema.User;
import com.ismealdi.amrestjava.util.Networks;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Al
 * on 22/04/19 | 18:25
 */
public interface Auth {

    @POST("login")
    @Headers("Content-Type: application/json")
    Observable<BaseResponse<User>> signIn(@Body SignInRequest signInRequest);

    @POST("logout")
    @Headers("Content-Type: application/json")
    Observable<BaseResponse<String>> signOut();

    @POST("register")
    @Headers("Content-Type: application/json")
    Observable<BaseResponse<User>> register(@Body SignUpRequest signUpRequest);

    @GET("users")
    @Headers("Content-Type: application/json")
    Observable<BaseResponse<List<User>>> users();
    
}
