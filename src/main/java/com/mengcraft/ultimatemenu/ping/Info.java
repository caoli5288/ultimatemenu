package com.mengcraft.ultimatemenu.ping;

import net.md_5.bungee.api.ChatColor;

public class Info {
    String host;
    int port;

    int online = -1;
    String message;
    int max = -1;
    double lag = -1;

    public String getMessage() {
        return message == null ? null : ChatColor.translateAlternateColorCodes('&', message);
    }

    public void wrap(PingResponse response) {
        message = response.getMessage();
        max = response.getMax();
        online = response.getOnline();
        lag = response.getLag();
    }

    public void init() {
        message = null;
        max = -1;
        online = -1;
        lag = -1;
    }
}
