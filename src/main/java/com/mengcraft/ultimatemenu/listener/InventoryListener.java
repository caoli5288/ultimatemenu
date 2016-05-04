package com.mengcraft.ultimatemenu.listener;

import com.mengcraft.ultimatemenu.menu.MenuManager;
import com.mengcraft.ultimatemenu.menu.MenuUtil;
import com.mengcraft.ultimatemenu.menu.MenuAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {
    @EventHandler
    public void handle(InventoryClickEvent event) {
        if (MenuManager.MANAGER.hasOpened(event.getWhoClicked()) && isInventory(event.getClickedInventory())) {
            Player p = ((Player) event.getWhoClicked());
            if (MenuAction.process(p, event.getSlot())) {
                p.closeInventory();
            }
            event.setCancelled(true);
        }
    }

    private boolean isInventory(Inventory inventory) {
        return inventory.getHolder().equals(MenuUtil.HOLDER);
    }
}
