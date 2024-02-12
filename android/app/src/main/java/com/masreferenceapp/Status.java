package com.masreferenceapp;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class Status {
    public static String status(String statusCode, String message){
        try {
            JSONObject jo = new JSONObject();
            jo.put("statusCode", statusCode);
            jo.put("message", message);
            return (jo.toString());
        } catch (Exception e){
            return e.toString();
        }
    }
}
