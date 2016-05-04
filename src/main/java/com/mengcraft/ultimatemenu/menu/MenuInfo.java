package com.mengcraft.ultimatemenu.menu;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

import static com.mengcraft.ultimatemenu.menu.MenuUtil.toInventory;
import static com.mengcraft.ultimatemenu.menu.MenuUtil.update;

public class MenuInfo {
    private final HashMap<Integer, ItemInfo> itemMap = new HashMap<>();
    private String name;
    private Inventory menu;
    private int slot;

    public HashMap<Integer, ItemInfo> getItemMap() {
        return itemMap;
    }

    public void setMenu(Inventory menu) {
        this.menu = menu;

    }

    public boolean needUpdate() {
        return menu != null && !menu.getViewers().isEmpty();
    }

    public Inventory getMenu() {
        if (menu == null) {
            setMenu(toInventory(this));
        }
        return menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void putItem(int slot, ItemInfo item) {
        itemMap.put(slot, item);
    }

    public void setItem(Integer slot, ItemStack item) {
        menu.setItem(slot, item);
    }

    public void updateView() {
        for (HumanEntity entity : menu.getViewers()) {
            if (entity instanceof Player) {
                Player.class.cast(entity).updateInventory();
            }
        }
    }

    public void updateMenu() {
        update(this);
    }
}
