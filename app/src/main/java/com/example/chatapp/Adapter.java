package com.example.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    List<message> messages;

    public Adapter(Context context, List<message> messages){
        this.context = context;
        this.messages = messages;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.messageview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(messages.get(position).isSent()){
            holder.receivemsg.setText("");
            holder.sentmsg.setText(messages.get(position).getMessage());
        }
        else{
            holder.sentmsg.setText("");
            holder.receivemsg.setText(messages.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
