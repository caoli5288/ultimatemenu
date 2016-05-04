package com.mengcraft.ultimatemenu.listener;

import com.mengcraft.ultimatemenu.menu.MenuManager;
import com.mengcraft.ultimatemenu.menu.MenuUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        MenuManager.MANAGER.release(event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        MenuManager.MANAGER.release(event.getPlayer());
    }
}
