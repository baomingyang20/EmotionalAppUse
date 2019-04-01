package com.example.administrator.emotionalappuse.mainframe;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.emotionalappuse.R;
import com.example.administrator.emotionalappuse.client.ClientService;
import com.example.administrator.emotionalappuse.loginframe.LoginFrameListener;

public class MainFrame extends AppCompatActivity {

    private final String TAG = "MainFrame";
    private TextView mTextMessage;
    private Button loginButton;
    private ClientService myService;
    private MyServiceConn conn;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conn = new MyServiceConn();
        bindService(new Intent(this, ClientService.class), conn,
                BIND_AUTO_CREATE);

        mTextMessage = findViewById(R.id.message);
        loginButton = findViewById(R.id.loginButton);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public void setFrameListener() {
        loginButton.setOnClickListener(new LoginFrameListener(myService));
    }

    public class MyServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = ((ClientService.LocalBinder) service).getService();

            myService.setAppCompatActivity(MainFrame.this);
            setFrameListener();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unbindService(conn);
    }
}
