package com.ismealdi.amrestjava.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Al
 * on 22/04/19 | 18:10
 */
public class SignUpRequest {

    private String password;
    private String email;
    private String name;

    @SerializedName("password_confirmation")
    private String confirm;

    public SignUpRequest() {}

    public SignUpRequest(String password, String email, String name, String confirm){
        this.password=password;
        this.email=email;
        this.name=name;
        this.confirm=confirm;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
