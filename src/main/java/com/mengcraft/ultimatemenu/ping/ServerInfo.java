package com.mengcraft.ultimatemenu.ping;

import com.mengcraft.ultimatemenu.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

public class ServerInfo {

    private static HashMap<String, Info> serverMap = new HashMap<>();
    private static Plugin pl;

    static {
        pl = Main.pl;
    }

    public static void init() {
        serverMap.clear();
        File[] var3;
        int var2 = (var3 = (new File(pl.getDataFolder() + File.separator + "Menus")).listFiles()).length;

        for (int var1 = 0; var1 < var2; ++var1) {
            File var0 = var3[var1];
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(var0);
            Iterator var6 = conf.getKeys(false).iterator();

            while (var6.hasNext()) {
                String var5 = (String) var6.next();
                if (conf.getBoolean(var5 + ".Ping_Server")) {
                    Info info = new Info();
                    info.host = conf.getString(var5 + ".Server_IP");
                    info.port = conf.getInt(var5 + ".Port");
                    serverMap.put(var0.getName() + "-" + var5, info);
                }
            }
        }

    }

    public static void update(String server, Info info) {
        serverMap.put(server, info);
    }

    public static HashMap<String, Info> getServerMap() {
        return serverMap;
    }

    public static double getLag(String var0) {
        if (serverMap.containsKey(var0)) {
            return serverMap.get(var0).lag;
        } else {
            return -1;
        }
    }

    public static int getServerOnline(String var0) {
        if (serverMap.containsKey(var0)) {
            return serverMap.get(var0).online;
        } else {
            return -1;
        }
    }

    public static int getServerMax(String var0) {
        if (serverMap.containsKey(var0)) {
            return serverMap.get(var0).max;
        } else {
            return -1;
        }
    }

    public static String getServerMessage(String var0) {
        if (serverMap.containsKey(var0)) {
            return serverMap.get(var0).getMessage();
        } else {
            return "The message is disabled in this ico!";
        }
    }
}
