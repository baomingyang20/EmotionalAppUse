package com.example.administrator.emotionalappuse.loginframe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.emotionalappuse.client.ClientService;

public class LoginFrameListener implements View.OnClickListener {

    private final String TAG = "LoginFrameListener";
    private ClientService service;

    public LoginFrameListener(ClientService service) {
        this.service = service;
    }

    @Override
    public void onClick(View v) {

        AppCompatActivity appCompatActivity = service.getAppCompatActivity();
        appCompatActivity.startActivity(new Intent(appCompatActivity, LoginActivity.class));
    }
}
