package com.mengcraft.ultimatemenu.ping;

/**
 * Created by on 16-4-29.
 */
public class PingResponse {
    private String message;
    private int max;
    private int online;

    public String getMessage() {
        return message;
    }

    public PingResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getMax() {
        return max;
    }

    public PingResponse setMax(int max) {
        this.max = max;
        return this;
    }

    public int getOnline() {
        return online;
    }

    public PingResponse setOnline(int online) {
        this.online = online;
        return this;
    }

    public boolean valid() {
        return max != 0;
    }
}
