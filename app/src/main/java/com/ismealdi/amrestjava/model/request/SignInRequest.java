package com.ismealdi.amrestjava.model.request;

/**
 * Created by Al
 * on 22/04/19 | 18:10
 */
public class SignInRequest {

    private String password;
    private String email;

    public SignInRequest() {}

    public SignInRequest(String password, String email){
        this.password=password;
        this.email=email;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getPassword(){
        return password;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getEmail(){
        return email;
    }

}
