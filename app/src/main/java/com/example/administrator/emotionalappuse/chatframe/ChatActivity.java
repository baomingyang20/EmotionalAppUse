package com.example.administrator.emotionalappuse.chatframe;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.emotionalappuse.R;
import com.example.administrator.emotionalappuse.client.ClientService;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "ChatActivity";
    private List<String> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerDemoAdapter adapter;
    private ClientService myService;
    private MyServiceConn conn;
    private EditText inputText;
    private Button send;
    private String useName;
    private Handler handler;


    public void setList(String message) {
        adapter.update(message);
//        adapter.notifyItemInserted(list.size() - 1);
//        recyclerView.scrollToPosition(list.size() - 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        conn = new MyServiceConn();
        bindService(new Intent(this, ClientService.class), conn,
                BIND_AUTO_CREATE);

        HandlerThread handlerThread = new HandlerThread("chatFrameHandleThread");
        handlerThread.start();

        handler = new Handler(handlerThread.getLooper());

        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        recyclerView = findViewById(R.id.recyclerview);

        send.setOnClickListener(this);
        adapter = new RecyclerDemoAdapter(this, list, handler);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onClick(View v) {
        String input = inputText.getText().toString();
        String message;
        if (!"".equals(input)) {
            if ("bmy".equals(useName)) {
                message = "message" + "\t" + useName + "\t" + "aaa" + "\t" + input;
            } else {
                message = "message" + "\t" + useName + "\t" + "bmy" + "\t" + input;
            }
            myService.sendRequest(message);
            inputText.setText("");
        }
    }

    public class MyServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = ((ClientService.LocalBinder) service).getService();

            useName = myService.getUseName();
            myService.setAppCompatActivity(ChatActivity.this);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }
    }
}
