package com.example.administrator.emotionalappuse.chatframe;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.emotionalappuse.R;

import java.util.List;

public class RecyclerDemoAdapter extends RecyclerView.Adapter<RecyclerDemoAdapter.MyHold> {

    private Context context;
    private List<String> list;
    private Handler handler;

    public RecyclerDemoAdapter(Context context, List<String> list, Handler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    public void update(String message) {
        final String s = message;
        list.add(s);

    }

    @NonNull
    @Override
    public MyHold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_chat, viewGroup, false);

        MyHold myHold = new MyHold(view);
        return myHold;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHold myHold, int i) {
        final String s = list.get(i);
        final TextView textView = myHold.textView;
        textView.setText(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHold extends RecyclerView.ViewHolder {

        private TextView textView;

        public MyHold(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }


}
