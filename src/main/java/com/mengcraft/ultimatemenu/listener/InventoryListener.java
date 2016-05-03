package com.mengcraft.ultimatemenu.listener;

import com.mengcraft.ultimatemenu.task.MenuAction;
import com.mengcraft.ultimatemenu.task.PlayerMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {
   @EventHandler
   public void InvClick(InventoryClickEvent var1) {
      Player var2 = (Player)var1.getWhoClicked();
      if(PlayerMenu.isAreadyWithOneMenu(var2)) {
         String var3 = PlayerMenu.getPlayerMenuTitle(var2);
         if(var1.getInventory() == null) {
            return;
         }

         if(var1.getInventory().getTitle() == null) {
            return;
         }

         if(!var1.getInventory().getTitle().equalsIgnoreCase(var3)) {
            return;
         }

         var1.setCancelled(true);
         if(MenuAction.Functions(var2, var1.getSlot())) {
            var2.closeInventory();
         }
      }

   }
}
