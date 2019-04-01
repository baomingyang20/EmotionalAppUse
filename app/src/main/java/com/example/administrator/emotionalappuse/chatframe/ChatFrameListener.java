package com.example.administrator.emotionalappuse.chatframe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.emotionalappuse.client.ClientService;

public class ChatFrameListener implements View.OnClickListener {
    private final String TAG = "ChatFrameListener";
    private ClientService service;

    public ChatFrameListener(ClientService service) {
        this.service = service;
    }

    @Override
    public void onClick(View v) {
        AppCompatActivity appCompatActivity = service.getAppCompatActivity();
        appCompatActivity.startActivity(new Intent(appCompatActivity, ChatActivity.class));
    }
}
