package com.example.administrator.emotionalappuse.mainframe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.administrator.emotionalappuse.R;
import com.example.administrator.emotionalappuse.client.ClientService;
import com.example.administrator.emotionalappuse.persionalinformation.PersionalInformationActivity;

public class BottomNavigationViewListener implements BottomNavigationView.OnNavigationItemSelectedListener {
    private final String TAG = "BottomNavigationViewListener";
    private ClientService service;

    public BottomNavigationViewListener(ClientService service) {
        this.service = service;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        AppCompatActivity appCompatActivity = service.getAppCompatActivity();
        switch (item.getItemId()) {
            case R.id.navigation_home:
//                mTextMessage.setText(R.string.title_home);
                return true;
            case R.id.navigation_dashboard:
//                mTextMessage.setText(R.string.title_dashboard);
                return true;
            case R.id.navigation_notifications:
                appCompatActivity.startActivity(new Intent(appCompatActivity
                        , PersionalInformationActivity.class));
                return true;
        }
        return false;
    }
}
