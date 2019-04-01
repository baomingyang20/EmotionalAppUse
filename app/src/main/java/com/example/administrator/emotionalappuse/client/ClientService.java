package com.example.administrator.emotionalappuse.client;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.emotionalappuse.chatframe.ChatActivity;
import com.example.administrator.emotionalappuse.loginframe.LoginActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientService extends Service {

    private final String TAG = "ClientService";
    private AppCompatActivity appCompatActivity;
    private SocketChannel socketChannel = null;
    private String useName = "";

    public String getUseName() {
        return useName;
    }

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socketChannel = SocketChannel.open();
                    socketChannel.socket().connect(
                            new InetSocketAddress("203.195.137.57", 9999));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                readMessage();
            }
        }).start();
    }

    public void readMessage() {
        while (true) {
            String message;
            byte b[] = new byte[1024];
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                socketChannel.read(buffer);
                message = new String(buffer.array());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                close();
                break;
            }

            message = message.trim();
            if (message.indexOf("login success") != -1) {
                String name = message.substring(0, message.indexOf(" "));
                Log.i(TAG, name);
                useName = name;
                ((LoginActivity) appCompatActivity).jumpToClientFrmae();

            } else if (message.equals("login fail")) {
                Log.i(TAG, message);
            } else if (message.equals("register success")) {
                Log.i(TAG, "register success");
            } else if (message.equals("register fail")) {
                Log.i(TAG, message);
            } else if (message.indexOf("message") == 0) {
                Log.e(TAG, message);

                String[] s = message.split("\t");

                ChatActivity chatActivity = (ChatActivity) appCompatActivity;
                chatActivity.setList(s[1]);
                chatActivity.setList(s[3]);
                Log.i(TAG, message);
            } else if (message.indexOf("allUserIformationRequest") == 0) {
                String s1[] = message.split("\r\n");
                String s2[] = s1[1].split("\t");
                Log.e(TAG, message);
            }
        }
    }

    public void sendRequest(String s) {

        final String message = s;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socketChannel.write(ByteBuffer.wrap(message.getBytes()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void close() {
        try {
            socketChannel.close();
//            System.exit(0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    public final class LocalBinder extends Binder {
        public ClientService getService() {
            return ClientService.this;
        }
    }
}
