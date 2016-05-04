package com.mengcraft.ultimatemenu.ping;

import com.mengcraft.ultimatemenu.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

public class InfoManager {

    public static final InfoManager MANAGER = new InfoManager();

    private final HashMap<String, Info> map;

    public InfoManager() {
        this(new HashMap<>());
    }

    private InfoManager(HashMap<String, Info> map) {
        this.map = map;
    }

    public void init() {
        map.clear();
        File[] var3;
        int var2 = (var3 = (new File(Main.main.getDataFolder() + File.separator + "Menus")).listFiles()).length;

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
                    map.put(var0.getName() + "-" + var5, info);
                }
            }
        }

    }

    public void update(String server, Info info) {
        map.put(server, info);
    }

    public HashMap<String, Info> getServerMap() {
        return map;
    }

    public int getOnline(String var0) {
        if (map.containsKey(var0)) {
            return map.get(var0).online;
        } else {
            return -1;
        }
    }

    public int getServerMax(String var0) {
        if (map.containsKey(var0)) {
            return map.get(var0).max;
        } else {
            return -1;
        }
    }

    public String getServerMessage(String var0) {
        if (map.containsKey(var0)) {
            return map.get(var0).getMessage();
        } else {
            return "The message is disabled in this ico!";
        }
    }
}
