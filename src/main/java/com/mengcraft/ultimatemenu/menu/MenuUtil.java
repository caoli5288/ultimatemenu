package com.mengcraft.ultimatemenu.menu;

import com.mengcraft.ultimatemenu.TextUtil;
import com.mengcraft.ultimatemenu.ping.InfoManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MenuUtil {

    public static final InventoryHolder HOLDER = new InventoryHolder() {
        @Override
        public Inventory getInventory() {
            return null;
        }
    };

    public static void update(MenuInfo menuInfo) {
        menuInfo.getItemMap().forEach((slot, format) -> {
            ItemStack item = getItem(format, format.id);
            menuInfo.setItem(slot, item);
            updateInfo(format, format.id);
        });
    }

    public static Inventory toInventory(MenuInfo menuInfo) {
        Inventory menu = Bukkit.createInventory(HOLDER, menuInfo.getSlot(), menuInfo.getName());
        menuInfo.getItemMap().forEach((slot, format) -> {
            ItemStack item = getItem(format, format.id);
            menu.setItem(slot, item);
            updateInfo(format, format.id);
        });
        return menu;
    }

    private static ItemStack getItem(ItemInfo format, String serverId) {
        Material material;
        byte data;
        int online;

        if (format.Show_Players_On_Item_Amount && InfoManager.MANAGER.getOnline(serverId) != -1) {
            online = InfoManager.MANAGER.getOnline(serverId);
        } else {
            online = 1;
        }

        ArrayList<String> motdList = new ArrayList<>();
        Iterator<String> loreList;

        String nameLine;
        String loreLineRaw;
        String loreLine;

        if (format.forceOnline) {
            material = Material.getMaterial(format.ID_Online);
            data = (byte) format.DATA_Online;
            loreList = format.onlineMotd.get(format.onlineFrame).iterator();

            while (loreList.hasNext()) {
                loreLineRaw = loreList.next();
                loreLine = TextUtil.getFinished(loreLineRaw, serverId);
                motdList.add(loreLine);
            }

            nameLine = TextUtil.getFinished(format.onlineNameList.get(format.onlineFrameName), serverId);
        } else {
            if (InfoManager.MANAGER.getOnline(serverId) == -1) {
                material = Material.getMaterial(format.ID_Offline);
                data = (byte) format.DATA_Offline;
                loreList = ((List) format.offlineMotd.get(format.offlineFrame)).iterator();

                while (loreList.hasNext()) {
                    loreLineRaw = loreList.next();
                    loreLine = TextUtil.getFinished(loreLineRaw, serverId);
                    motdList.add(loreLine);
                }

                nameLine = TextUtil.getFinished(format.offlineNameList.get(format.offlineFrameName), serverId);
            } else {
                if (InfoManager.MANAGER.getServerMax(serverId) == InfoManager.MANAGER.getOnline(serverId) && !format.fullMotd.isEmpty() && !format.fullNameList.isEmpty()) {
                    material = Material.getMaterial(format.ID_Full);
                    data = (byte) format.DATA_Full;
                    loreList = ((List) format.fullMotd.get(format.fullFrame)).iterator();

                    while (loreList.hasNext()) {
                        loreLineRaw = loreList.next();
                        loreLine = TextUtil.getFinished(loreLineRaw, serverId);
                        motdList.add(loreLine);
                    }

                    nameLine = TextUtil.getFinished(format.fullNameList.get(format.fullFrameName), serverId);
                } else if (format.motdFull != null && InfoManager.MANAGER.getServerMessage(serverId).contains(format.motdFull)) {
                    material = Material.getMaterial(format.motdFullId);
                    data = (byte) format.dataMotdFull;
                    loreList = ((List) format.fullMotd.get(format.fullFrame)).iterator();

                    while (loreList.hasNext()) {
                        loreLineRaw = loreList.next();
                        loreLine = TextUtil.getFinished(loreLineRaw, serverId);
                        motdList.add(loreLine);
                    }

                    nameLine = TextUtil.getFinished(format.motdFullNameList.get(format.onlineFrameName), serverId);
                } else {
                    material = Material.getMaterial(format.ID_Online);
                    data = (byte) format.DATA_Online;
                    loreList = format.onlineMotd.get(format.onlineFrame).iterator();

                    while (loreList.hasNext()) {
                        loreLineRaw = loreList.next();
                        loreLine = TextUtil.getFinished(loreLineRaw, serverId);
                        motdList.add(loreLine);
                    }

                    nameLine = TextUtil.getFinished(format.onlineNameList.get(format.onlineFrameName), serverId);
                }
            }
        }

        ItemStack item = new ItemStack(material, online, data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(nameLine);
        meta.setLore(motdList);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemInfo updateInfo(ItemInfo format, String name) {
        if (format.forceOnline) {
            if (format.onlineMotd.size() - 1 == format.onlineFrame) {
                format.onlineFrame = 0;
            } else {
                ++format.onlineFrame;
            }

            if (format.onlineNameList.size() - 1 == format.onlineFrameName) {
                format.onlineFrameName = 0;
            } else {
                ++format.onlineFrameName;
            }

            return format;
        } else {
            if (InfoManager.MANAGER.getOnline(name) == -1.0D) {
                if (format.offlineMotd.size() - 1 == format.offlineFrame) {
                    format.offlineFrame = 0;
                } else {
                    ++format.offlineFrame;
                }

                if (format.offlineNameList.size() - 1 == format.offlineFrameName) {
                    format.offlineFrameName = 0;
                } else {
                    ++format.offlineFrameName;
                }
            }

            if (InfoManager.MANAGER.getOnline(name) != -1.0D) {
                if (InfoManager.MANAGER.getServerMax(name) == InfoManager.MANAGER.getOnline(name)) {
                    if (format.fullMotd.size() - 1 == format.fullFrame) {
                        format.fullFrame = 0;
                    } else {
                        ++format.fullFrame;
                    }

                    if (format.fullNameList.size() - 1 == format.fullFrameName) {
                        format.fullFrameName = 0;
                    } else {
                        ++format.fullFrameName;
                    }
                } else {
                    if (format.onlineMotd.size() - 1 == format.onlineFrame) {
                        format.onlineFrame = 0;
                    } else {
                        ++format.onlineFrame;
                    }

                    if (format.onlineNameList.size() - 1 == format.onlineFrameName) {
                        format.onlineFrameName = 0;
                    } else {
                        ++format.onlineFrameName;
                    }
                }
            }

            return format;
        }
    }
}
