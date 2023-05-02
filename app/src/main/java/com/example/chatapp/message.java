package com.example.chatapp;

public class message {
    String message;
    boolean sent;

    public message(String message, boolean sent){
        this.message = message;
        this.sent = sent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
