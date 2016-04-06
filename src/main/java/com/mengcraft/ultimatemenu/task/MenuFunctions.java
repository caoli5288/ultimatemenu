package com.mengcraft.ultimatemenu.task;

import com.mengcraft.ultimatemenu.Main;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MenuFunctions {
   public static Plugin pl;

   static {
      pl = Main.pl;
   }

   public static boolean Functions(Player var0, int var1) {
      MenuFormat var2 = PlayerMenu.getPlayerFormat(var0);
      if(var2.itemMap.keySet().contains(Integer.valueOf(var1))) {
         ArrayList var3 = ((ItemFormat)var2.itemMap.get(Integer.valueOf(var1))).commands;
         BungeeTP(var3, var0);
         asOP(var0, var3);
         asConsole(var3, var0);
         loadTitle(var3, var0);
         sendMessage(var0, var3);
         if(((ItemFormat)var2.itemMap.get(Integer.valueOf(var1))).Close_On_Click) {
            return true;
         }
      }

      return false;
   }

   public static void sendMessage(Player var0, List var1) {
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         String var2 = (String)var3.next();
         if(var2.contains("msg:")) {
            var0.sendMessage(var2.replace("msg:", "").replaceAll("&", "§"));
         }
      }

   }

   public static void asOP(Player var0, List var1) {
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         String var2 = (String)var3.next();
         if(var2.contains("asOP:")) {
            if(var0.isOp()) {
               var0.performCommand(var2.replace("asOP:", ""));
            } else {
               var0.setOp(true);
               var0.performCommand(var2.replace("asOP:", ""));
               var0.setOp(false);
            }
         }
      }

   }

   public static void asConsole(List var0, Player var1) {
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         String var2 = (String)var3.next();
         if(var2.contains("asConsole:")) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), var2.replace("%PlayerName%", var1.getName()).replace("asConsole:", ""));
         }
      }

   }

   public static void BungeeTP(List var0, Player var1) {
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         String var2 = (String)var3.next();
         if(var2.contains("send:")) {
            ByteArrayDataOutput var4 = ByteStreams.newDataOutput();
            var4.writeUTF("Connect");
            var4.writeUTF(var2.replace("send:", ""));
            var1.sendPluginMessage(pl, "BungeeCord", var4.toByteArray());
         }
      }

   }

   public static void loadTitle(List var0, Player var1) {
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         String var2 = (String)var3.next();
         if(var2.contains("title:")) {
            if(pl.getServer().getVersion().contains("1.8")) {
               String var4 = var2.replace("title:", "").replaceAll("&", "§");
               String[] var5 = var4.split("##");
               if(((String[])var5.clone()).length == 5) {
                  SendTitle.sendTitle(var1, var5[0], var5[1], Integer.parseInt(var5[2]), Integer.parseInt(var5[3]), Integer.parseInt(var5[4]));
               } else {
                  System.out.print("[UltimateMenu] Title must have 5 Parts, Title, Subtitle,FadeIn,Stay,FadeOut");
               }
            } else {
               System.out.print("[UltimateMenu] Titles dont work on 1.7!");
            }
         }
      }

   }
}
