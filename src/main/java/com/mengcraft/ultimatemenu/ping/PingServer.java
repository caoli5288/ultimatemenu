package com.mengcraft.ultimatemenu.ping;

import com.mengcraft.ultimatemenu.Main;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

final class PingServer {
   public static Plugin pl;

   static {
      pl = Main.pl;
   }

   public static String getMOTD(String var0, int var1) {
      Socket var2 = null;
      DataOutputStream var3 = null;
      DataInputStream var4 = null;
      String var5 = "OFFLINE";

      String var17;
      try {
         var2 = new Socket(var0, var1);
         var2.setSoTimeout(pl.getConfig().getInt("Time_Out"));
         var3 = new DataOutputStream(var2.getOutputStream());
         var4 = new DataInputStream(var2.getInputStream());
         ByteArrayOutputStream var6 = new ByteArrayOutputStream();
         DataOutputStream var7 = new DataOutputStream(var6);
         var7.write(0);
         PacketUtils.writeVarInt(var7, 4);
         PacketUtils.writeString(var7, var0, PacketUtils.UTF8);
         var7.writeShort(var1);
         PacketUtils.writeVarInt(var7, 1);
         byte[] var8 = var6.toByteArray();
         PacketUtils.writeVarInt(var3, var8.length);
         var3.write(var8);
         var8 = new byte[1];
         PacketUtils.writeVarInt(var3, var8.length);
         var3.write(var8);
         PacketUtils.readVarInt(var4);
         PacketUtils.readVarInt(var4);
         byte[] var9 = new byte[PacketUtils.readVarInt(var4)];
         var4.readFully(var9);
         String var10 = new String(var9, PacketUtils.UTF8);
         JSONObject var11 = (JSONObject)JSONValue.parse(var10);
         String var12 = (String)var11.get("description");
         JSONObject var13 = (JSONObject)var11.get("players");
         int var14 = ((Long)var13.get("online")).intValue();
         int var15 = ((Long)var13.get("max")).intValue();
         var5 = var12 + ";" + var14 + ";" + var15;
         var17 = var5;
         return var17;
      } catch (Exception var20) {
         var17 = var5;
      } finally {
         PacketUtils.closeQuietly(var3);
         PacketUtils.closeQuietly(var4);
         PacketUtils.closeQuietly(var2);
      }

      return var17;
   }
}
