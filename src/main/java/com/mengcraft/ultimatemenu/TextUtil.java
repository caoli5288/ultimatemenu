package com.mengcraft.ultimatemenu;

import com.mengcraft.ultimatemenu.ping.ServerInfo;
import org.bukkit.entity.Player;

public class TextUtil {
    public static String getFinished(String raw, String serverId) {
        raw = BungeeVariables(raw, serverId);
        raw = chatNormals(raw);
        return raw;
    }

    public static String chatNormals(String var0) {
        var0 = var0.replace("[X]", "█");
        var0 = var0.replace("[OK]", "✔");
        var0 = var0.replace("[Arrow_1]", "←");
        var0 = var0.replace("[Arrow_2]", "↑");
        var0 = var0.replace("[Arrow_3]", "→");
        var0 = var0.replace("[Arrow_4]", "↓");
        var0 = var0.replace("[<3]", "♥");
        var0 = var0.replace("[1]", "❶");
        var0 = var0.replace("[2]", "❷");
        var0 = var0.replace("[3]", "❸");
        var0 = var0.replace("[4]", "❹");
        var0 = var0.replace("[5]", "❺");
        var0 = var0.replace("[6]", "❻");
        var0 = var0.replace("[7]", "❼");
        var0 = var0.replace("[8]", "❽");
        var0 = var0.replace("[9]", "❾");
        var0 = var0.replace("[10]", "❿");
        var0 = var0.replaceAll("&", "§");
        return var0;
    }

    public static String BungeeVariables(String var0, String var1) {
        var0 = var0.replace("{Online}", String.valueOf(ServerInfo.getOnline(var1)));
        if (ServerInfo.getServerMessage(var1) != null) {
            var0 = var0.replace("{Motd}", ServerInfo.getServerMessage(var1).replaceAll("&", "§"));
        }

        var0 = var0.replace("{Max}", String.valueOf(ServerInfo.getServerMax(var1)));
        return var0;
    }
}
