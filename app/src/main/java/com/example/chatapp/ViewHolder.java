package com.example.chatapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView sentmsg, receivemsg;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        sentmsg = itemView.findViewById(R.id.smessage);
        receivemsg = itemView.findViewById(R.id.rmessage);
    }
}
