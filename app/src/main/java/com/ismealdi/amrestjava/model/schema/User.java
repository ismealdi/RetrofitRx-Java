package com.ismealdi.amrestjava.model.schema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Al
 * on 22/04/19 | 18:10
 */
public class User{

    @SerializedName("api_token") private String token;
    private String name;
    private Integer id;
    private String email;

    public User(){ }

    public User(String token,String name,Integer id,String email){
        this.token=token;
        this.name=name;
        this.id=id;
        this.email=email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}