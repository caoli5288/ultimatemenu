package com.mengcraft.ultimatemenu.task;

import com.mengcraft.ultimatemenu.Main;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SendTitle {
   public static Plugin pl;

   static {
      pl = Main.pl;
   }

   public static void sendTitle(Player var0, String var1, String var2, int var3, int var4, int var5) {
      if(pl.getServer().getVersion().contains("1.8")) {
         CraftPlayer var6 = (CraftPlayer)var0;
         PlayerConnection var7 = var6.getHandle().playerConnection;
         IChatBaseComponent var8 = ChatSerializer.a("{\'text\': \'" + var1 + "\'}");
         IChatBaseComponent var9 = ChatSerializer.a("{\'text\': \'" + var2 + "\'}");
         PacketPlayOutTitle var10 = new PacketPlayOutTitle(EnumTitleAction.TITLE, var8, var3, var4, var5);
         PacketPlayOutTitle var11 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, var9);
         var7.sendPacket(var10);
         var7.sendPacket(var11);
      } else {
         System.out.print("[UltimateMenu] Tutles dont work on 1.7!");
      }

   }
}
