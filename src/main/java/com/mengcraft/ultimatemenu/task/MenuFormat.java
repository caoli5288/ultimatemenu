package com.mengcraft.ultimatemenu.task;

import java.util.HashMap;

public class MenuFormat {
    HashMap<Integer, MenuItemFormat> itemMap = new HashMap<>();
    String name;
    int slot;

    public void setItemMap(HashMap<Integer, MenuItemFormat> itemMap) {
        this.itemMap = itemMap;
    }

    public HashMap<Integer, MenuItemFormat> getItemMap() {
        return itemMap;
    }
}
