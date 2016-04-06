package com.mengcraft.ultimatemenu.task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerMenu extends BukkitRunnable {
   private static HashMap PlayerMenu = new HashMap();
   private static HashMap PlayerLayout = new HashMap();

   public void run() {
      HashSet var1 = new HashSet(PlayerMenu.keySet());
      HashMap var2 = new HashMap();
      HashMap var3 = new HashMap();
      var2.putAll(PlayerMenu);
      var3.putAll(PlayerLayout);
      if(!var1.isEmpty()) {
         Iterator var5 = var1.iterator();

         while(var5.hasNext()) {
            String var4 = (String)var5.next();
            Player var6 = Bukkit.getPlayerExact(var4);
            if(var6 != null) {
               InventoryUpdater.getInvInfos(var6, (Inventory)var2.get(var4), (MenuFormat)var3.get(var4));
               var6.updateInventory();
            }
         }
      }

   }

   public static void quitAllPlayers() {
      Iterator var1 = PlayerMenu.keySet().iterator();

      while(var1.hasNext()) {
         String var0 = (String)var1.next();
         Player var2 = Bukkit.getPlayerExact(var0);
         if(var2 != null) {
            var2.closeInventory();
            quitPlayer(var2);
         }
      }

   }

   public static MenuFormat getPlayerFormat(Player var0) {
      return PlayerLayout.containsKey(var0.getName())?(MenuFormat)PlayerLayout.get(var0.getName()):null;
   }

   public static String getPlayerMenuTitle(Player var0) {
      return PlayerLayout.containsKey(var0.getName())?((MenuFormat)PlayerLayout.get(var0.getName())).title :null;
   }

   public static void openMenu(Player p, String menuName) {
      MenuFormat menuFormat = LoadMenus.getMenu(menuName);
      if(menuFormat == null) {
         System.out.print("The menu: " + menuName + " Don't exist and the player: " + p.getName() + " Are trying to open!");
      } else {
         Inventory inv = InventoryUpdater.createInv(p, menuFormat);
         PlayerMenu.put(p.getName(), inv);
         PlayerLayout.put(p.getName(), menuFormat);
         p.openInventory(inv);
      }
   }

   public static void updatePlayerMenu(Player var0, MenuFormat var1) {
      if(PlayerLayout.containsKey(var0.getName())) {
         PlayerLayout.put(var0.getName(), var1);
      }

   }

   public static boolean isAreadyWithOneMenu(Player var0) {
      return PlayerMenu.containsKey(var0.getName());
   }

   public static void quitPlayer(Player var0) {
      PlayerMenu.remove(var0.getName());
      PlayerLayout.remove(var0.getName());
   }
}
