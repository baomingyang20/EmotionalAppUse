package com.example.administrator.emotionalappuse.persionalinformation;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.emotionalappuse.R;
import com.example.administrator.emotionalappuse.client.ClientService;

public class SearchExpert extends AppCompatActivity {

    private ClientService myService = new ClientService();
    private MyServiceConn conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_expert);
        Button b = findViewById(R.id.button);
        initExpert();
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initExpert();
//
//            }
//        });

    }

    public class MyServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = ((ClientService.LocalBinder) service).getService();
            myService.setAppCompatActivity(SearchExpert.this);
            //setFrameListener();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }
    }

    private void initExpert() {
        final String s = "allTeacherInformationRequest";


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("11111111111111111111","1111111111111111");
                myService.sendRequest(s);
            }
        }).start();


    }

    @Override
    protected void onStart() {
        super.onStart();
        conn = new MyServiceConn();
        bindService(new Intent(this, ClientService.class), conn,
                BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
