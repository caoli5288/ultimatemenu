package com.mengcraft.ultimatemenu.listener;

import com.mengcraft.ultimatemenu.task.PlayerMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InventoryCloseListener implements Listener {
   @EventHandler
   public void onClose(InventoryCloseEvent var1) {
      if(var1.getPlayer() instanceof Player) {
         Player var2 = (Player)var1.getPlayer();
         if(PlayerMenu.isWithMenuHold(var2)) {
            PlayerMenu.quitPlayer(var2);
         }
      }

   }

   @EventHandler
   public void onLeave(PlayerQuitEvent var1) {
      Player var2 = var1.getPlayer();
      if(PlayerMenu.isWithMenuHold(var2)) {
         PlayerMenu.quitPlayer(var2);
      }

   }
}
