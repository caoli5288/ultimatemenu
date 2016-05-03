package com.mengcraft.ultimatemenu.listener;

import com.mengcraft.ultimatemenu.task.InventoryUtil;
import com.mengcraft.ultimatemenu.task.MenuAction;
import com.mengcraft.ultimatemenu.task.PlayerMenu;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {
    @EventHandler
    public void handle(InventoryClickEvent event) {
        if (PlayerMenu.isWithMenuHold(event.getWhoClicked()) && isInventory(event.getClickedInventory())) {
            Player p = ((Player) event.getWhoClicked());

            if (MenuAction.process(p, event.getSlot())) {
                p.closeInventory();
            }

            event.setCancelled(true);
        }
    }

    private boolean isInventory(Inventory inventory) {
        return inventory.getHolder() == InventoryUtil.HOLDER;
    }
}
