package com.ismealdi.amrestjava.model.response;

/**
 * Created by Al
 * on 22/04/19 | 18:24
 */
public class BaseResponse<T> {
    
    private T data;
    
    public BaseResponse() {}

    public BaseResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
} 
