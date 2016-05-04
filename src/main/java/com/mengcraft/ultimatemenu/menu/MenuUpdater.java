package com.mengcraft.ultimatemenu.menu;

/**
 * Created on 16-5-4.
 */
public class MenuUpdater implements Runnable {
    @Override
    public void run() {
        MenuManager.MANAGER.getMenuMap().values().forEach(menu -> {
            if (menu.needUpdate()) {
                menu.updateView();
                menu.updateMenu();
            }
        });
    }
}
