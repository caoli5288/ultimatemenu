package com.mengcraft.ultimatemenu.task;

import com.mengcraft.ultimatemenu.TextUtil;
import com.mengcraft.ultimatemenu.ping.ServerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class InventoryUpdater {

    public static Inventory getInventory(Player p, Inventory content, MenuFormat format) {
        HashMap<Integer, MenuItemFormat> itemMap = new HashMap<>();

        format.getItemMap().forEach((slot, format1) -> {
            ItemStack item = getItem(format1, format1.id, p);
            itemMap.put(slot, updateInfo(format1, format1.id));
            content.setItem(slot, item);
        });

        format.setItemMap(itemMap);
        PlayerMenu.updateMenu(p, format);

        return content;
    }

    public static Inventory init(Player p, MenuFormat menuFormat) {
        Inventory menu = Bukkit.createInventory(p, menuFormat.slot, menuFormat.name);
        menuFormat.getItemMap().forEach((slot, format) -> {
            ItemStack item = getItem(format, format.id, p);
            menu.setItem(slot, item);
            updateInfo(format, format.id);
        });
        PlayerMenu.updateMenu(p, menuFormat);
        return menu;
    }

    private static ItemStack getItem(MenuItemFormat format, String serverId, Player p) {
        Material material;
        byte data;
        int online;

        if (format.Show_Players_On_Item_Amount && ServerInfo.getServerOnline(serverId) != -1) {
            online = ServerInfo.getServerOnline(serverId);
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
                loreLine = TextUtil.getFinished(loreLineRaw, serverId, p);
                motdList.add(loreLine);
            }

            nameLine = TextUtil.getFinished(format.onlineNameList.get(format.onlineFrameNames), serverId, p);
        } else {
            if (ServerInfo.getLag(serverId) == -1) {
                material = Material.getMaterial(format.ID_Offline);
                data = (byte) format.DATA_Offline;
                loreList = ((List) format.offlineMotd.get(format.offlineFrame)).iterator();

                while (loreList.hasNext()) {
                    loreLineRaw = loreList.next();
                    loreLine = TextUtil.getFinished(loreLineRaw, serverId, p);
                    motdList.add(loreLine);
                }

                nameLine = TextUtil.getFinished(format.offlineNameList.get(format.offlineFrameNames), serverId, p);
            } else {
                if (ServerInfo.getServerMax(serverId) == ServerInfo.getServerOnline(serverId) && !format.fullMotd.isEmpty() && !format.fullNameList.isEmpty()) {
                    material = Material.getMaterial(format.ID_Full);
                    data = (byte) format.DATA_Full;
                    loreList = ((List) format.fullMotd.get(format.fullFrame)).iterator();

                    while (loreList.hasNext()) {
                        loreLineRaw = loreList.next();
                        loreLine = TextUtil.getFinished(loreLineRaw, serverId, p);
                        motdList.add(loreLine);
                    }

                    nameLine = TextUtil.getFinished(format.fullNameList.get(format.fullFrameNames), serverId, p);
                } else if (format.motdFull != null && ServerInfo.getServerMessage(serverId).contains(format.motdFull)) {
                    material = Material.getMaterial(format.idMotdFull);
                    data = (byte) format.dataMotdFull;
                    loreList = ((List) format.fullMotd.get(format.fullFrame)).iterator();

                    while (loreList.hasNext()) {
                        loreLineRaw = loreList.next();
                        loreLine = TextUtil.getFinished(loreLineRaw, serverId, p);
                        motdList.add(loreLine);
                    }

                    nameLine = TextUtil.getFinished(format.motdFullNameList.get(format.onlineFrameNames), serverId, p);
                } else {
                    material = Material.getMaterial(format.ID_Online);
                    data = (byte) format.DATA_Online;
                    loreList = format.onlineMotd.get(format.onlineFrame).iterator();

                    while (loreList.hasNext()) {
                        loreLineRaw = loreList.next();
                        loreLine = TextUtil.getFinished(loreLineRaw, serverId, p);
                        motdList.add(loreLine);
                    }

                    nameLine = TextUtil.getFinished(format.onlineNameList.get(format.onlineFrameNames), serverId, p);
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

    private static MenuItemFormat updateInfo(MenuItemFormat format, String name) {
        if (format.forceOnline) {
            if (format.onlineMotd.size() - 1 == format.onlineFrame) {
                format.onlineFrame = 0;
            } else {
                ++format.onlineFrame;
            }

            if (format.onlineNameList.size() - 1 == format.onlineFrameNames) {
                format.onlineFrameNames = 0;
            } else {
                ++format.onlineFrameNames;
            }

            return format;
        } else {
            if (ServerInfo.getLag(name) == -1.0D) {
                if (format.offlineMotd.size() - 1 == format.offlineFrame) {
                    format.offlineFrame = 0;
                } else {
                    ++format.offlineFrame;
                }

                if (format.offlineNameList.size() - 1 == format.offlineFrameNames) {
                    format.offlineFrameNames = 0;
                } else {
                    ++format.offlineFrameNames;
                }
            }

            if (ServerInfo.getLag(name) != -1.0D) {
                if (ServerInfo.getServerMax(name) == ServerInfo.getServerOnline(name)) {
                    if (format.fullMotd.size() - 1 == format.fullFrame) {
                        format.fullFrame = 0;
                    } else {
                        ++format.fullFrame;
                    }

                    if (format.fullNameList.size() - 1 == format.fullFrameNames) {
                        format.fullFrameNames = 0;
                    } else {
                        ++format.fullFrameNames;
                    }
                } else {
                    if (format.onlineMotd.size() - 1 == format.onlineFrame) {
                        format.onlineFrame = 0;
                    } else {
                        ++format.onlineFrame;
                    }

                    if (format.onlineNameList.size() - 1 == format.onlineFrameNames) {
                        format.onlineFrameNames = 0;
                    } else {
                        ++format.onlineFrameNames;
                    }
                }
            }

            return format;
        }
    }
}
