package com.example.administrator.emotionalappuse.mainframe;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.emotionalappuse.R;
import com.example.administrator.emotionalappuse.chatframe.ChatFrameListener;
import com.example.administrator.emotionalappuse.client.ClientService;

public class ClientMainActivity extends AppCompatActivity {
    private final String TAG = "ClientMainActivity";
    private TextView mTextMessage;
    private Button chatButton;
    private ClientService myService;
    private MyServiceConn conn;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        conn = new MyServiceConn();
        bindService(new Intent(this, ClientService.class), conn,
                BIND_AUTO_CREATE);

        mTextMessage = (TextView) findViewById(R.id.message);
        chatButton = findViewById(R.id.chatButton);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);


    }

    public void setFrameListener() {
        chatButton.setOnClickListener(new ChatFrameListener(myService));
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationViewListener(myService));
    }

    public class MyServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = ((ClientService.LocalBinder) service).getService();

            myService.setAppCompatActivity(ClientMainActivity.this);
            setFrameListener();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }
    }

}
