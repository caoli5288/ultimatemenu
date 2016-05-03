package com.mengcraft.ultimatemenu.task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerMenu extends BukkitRunnable {

    private static HashMap playerMenu = new HashMap();
    private static HashMap playerLayout = new HashMap();

    public void run() {
        HashSet var1 = new HashSet(playerMenu.keySet());
        HashMap var2 = new HashMap();
        HashMap var3 = new HashMap();
        var2.putAll(playerMenu);
        var3.putAll(playerLayout);
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
        Iterator var1 = playerMenu.keySet().iterator();

        while (var1.hasNext()) {
            String var0 = (String) var1.next();
            Player var2 = Bukkit.getPlayerExact(var0);
            if (var2 != null) {
                var2.closeInventory();
                quitPlayer(var2);
            }
        }

    }

    public static MenuFormat getPlayerFormat(Player var0) {
        return playerLayout.containsKey(var0.getName()) ? (MenuFormat) playerLayout.get(var0.getName()) : null;
    }

    public static String getPlayerMenuTitle(Player var0) {
        return playerLayout.containsKey(var0.getName()) ? ((MenuFormat) playerLayout.get(var0.getName())).name : null;
    }

    public static void openMenu(Player p, String menuName) {
        MenuFormat menuFormat = MenuManager.MANAGER.getMenu(menuName);
        if (menuFormat == null) {
            System.out.print("The menu: " + menuName + " Don't exist and the player: " + p.getName() + " Are trying to open!");
        } else {
            Inventory menu = InventoryUtil.init(p, menuFormat);
            playerMenu.put(p.getName(), menu);
            playerLayout.put(p.getName(), menuFormat);
            p.openInventory(menu);
        }
    }

    public static void updateMenu(Player var0, MenuFormat var1) {
        if (playerLayout.containsKey(var0.getName())) {
            playerLayout.put(var0.getName(), var1);
        }

    }

    public static boolean isAreadyWithOneMenu(Player var0) {
        return playerMenu.containsKey(var0.getName());
    }

    public static void quitPlayer(Player var0) {
        playerMenu.remove(var0.getName());
        playerLayout.remove(var0.getName());
    }
}
