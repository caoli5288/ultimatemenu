package com.mengcraft.ultimatemenu.ping;

import com.mengcraft.ultimatemenu.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

public class ServersInfo {
    private static HashMap servers = new HashMap();
    private static Plugin pl;

    static {
        pl = Main.pl;
    }

    public static void registerServers() {
        servers.clear();
        File[] var3;
        int var2 = (var3 = (new File(pl.getDataFolder() + File.separator + "Menus")).listFiles()).length;

        for (int var1 = 0; var1 < var2; ++var1) {
            File var0 = var3[var1];
            YamlConfiguration var4 = YamlConfiguration.loadConfiguration(var0);
            Iterator var6 = var4.getKeys(false).iterator();

            while (var6.hasNext()) {
                String var5 = (String) var6.next();
                if (var4.getBoolean(var5 + ".Ping_Server")) {
                    Info var7 = new Info();
                    var7.ip = var4.getString(var5 + ".Server_IP");
                    var7.port = var4.getInt(var5 + ".Port");
                    servers.put(var0.getName() + "-" + var5, var7);
                }
            }
        }

    }

    public static void finishServer(String var0, Info var1) {
        servers.remove(var0);
        servers.put(var0, var1);
    }

    public static HashMap getServersToPing() {
        return servers;
    }

    public static double getServerPing(String var0) {
        if (servers.containsKey(var0)) {
            Info var1 = (Info) servers.get(var0);
            return var1.ms;
        } else {
            return -1.0D;
        }
    }

    public static int getServerOnlinePlayers(String var0) {
        if (servers.containsKey(var0)) {
            Info var1 = (Info) servers.get(var0);
            return var1.online;
        } else {
            return -1;
        }
    }

    public static int getServerMaxPlayers(String var0) {
        if (servers.containsKey(var0)) {
            Info var1 = (Info) servers.get(var0);
            return var1.max;
        } else {
            return -1;
        }
    }

    public static String getServerMotd(String var0) {
        if (servers.containsKey(var0)) {
            Info var1 = (Info) servers.get(var0);
            return var1.motd == null ? null : var1.motd.replaceAll("&", "§");
        } else {
            return "The Motd is disabled in this ico!";
        }
    }
}
