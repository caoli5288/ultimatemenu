package com.mengcraft.ultimatemenu.task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerMenu extends BukkitRunnable {

    private static final HashMap<String, Inventory> PLAYER_MENU = new HashMap<>();
    private static final HashMap<String, MenuFormat> PLAYER_LAYOUT = new HashMap<>();

    public void run() {
        HashSet var1 = new HashSet(PLAYER_MENU.keySet());
        HashMap var2 = new HashMap();
        HashMap var3 = new HashMap();
        var2.putAll(PLAYER_MENU);
        var3.putAll(PLAYER_LAYOUT);
        if (!var1.isEmpty()) {
            Iterator var5 = var1.iterator();

            while (var5.hasNext()) {
                String var4 = (String) var5.next();
                Player var6 = Bukkit.getPlayerExact(var4);
                if (var6 != null) {
                    InventoryUtil.getInventory(var6, (Inventory) var2.get(var4), (MenuFormat) var3.get(var4));
                    var6.updateInventory();
                }
            }
        }

    }

    public static void quitAllPlayers() {
        Iterator var1 = PLAYER_MENU.keySet().iterator();

        while (var1.hasNext()) {
            String var0 = (String) var1.next();
            Player var2 = Bukkit.getPlayerExact(var0);
            if (var2 != null) {
                var2.closeInventory();
                quitPlayer(var2);
            }
        }

    }

    public static MenuFormat getFormat(Player var0) {
        return PLAYER_LAYOUT.containsKey(var0.getName()) ? PLAYER_LAYOUT.get(var0.getName()) : null;
    }

    public static void openMenu(Player p, String menuName) {
        MenuFormat menuFormat = MenuManager.MANAGER.getMenu(menuName);
        if (menuFormat == null) {
            System.out.print("The menu: " + menuName + " Don't exist and the player: " + p.getName() + " Are trying to open!");
        } else {
            Inventory menu = InventoryUtil.init(p, menuFormat);
            PLAYER_MENU.put(p.getName(), menu);
            PLAYER_LAYOUT.put(p.getName(), menuFormat);
            p.openInventory(menu);
        }
    }

    public static void updateMenu(Player var0, MenuFormat var1) {
        if (PLAYER_LAYOUT.containsKey(var0.getName())) {
            PLAYER_LAYOUT.put(var0.getName(), var1);
        }

    }

    public static boolean isWithMenuHold(HumanEntity p) {
        return PLAYER_MENU.containsKey(p.getName());
    }

    public static void quitPlayer(Player var0) {
        PLAYER_MENU.remove(var0.getName());
        PLAYER_LAYOUT.remove(var0.getName());
    }
}
