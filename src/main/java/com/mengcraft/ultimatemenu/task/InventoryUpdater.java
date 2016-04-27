package com.mengcraft.ultimatemenu.task;

import com.mengcraft.ultimatemenu.ping.ServersInfo;
import com.mengcraft.ultimatemenu.text.VariablesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryUpdater {

    public static Inventory getInvInfo(Player var0, Inventory var1, MenuFormat var2) {
        HashMap var3 = new HashMap();

        int var7;
        ItemStack var9;
        for (Iterator var5 = var2.itemMap.entrySet().iterator(); var5.hasNext(); var1.setItem(var7, var9)) {
            Entry var4 = (Entry) var5.next();
            ItemFormat var6 = (ItemFormat) var4.getValue();
            var7 = (Integer) var4.getKey();
            String var8 = var6.id;
            if (var6.awaysOnline) {
                var9 = getItemStack(var6, var8, true, var0);
                var3.put(var7, updateInfo(var6, var8, true));
            } else {
                var9 = getItemStack(var6, var8, false, var0);
                var3.put(var7, updateInfo(var6, var8, false));
            }
        }

        var2.itemMap.clear();
        var2.itemMap.putAll(var3);
        PlayerMenu.updatePlayerMenu(var0, var2);
        return var1;
    }

    public static Inventory createInv(Player player, MenuFormat menuFormat) {
        Inventory menu = Bukkit.createInventory(player, menuFormat.slot, menuFormat.name);
        for (Entry<Integer, ItemFormat> entry : menuFormat.itemMap.entrySet()) {
            ItemFormat itemFormat = entry.getValue();
            int slot = entry.getKey();
            String serverId = itemFormat.id;
            ItemStack item;
            if (itemFormat.awaysOnline) {
                item = getItemStack(itemFormat, serverId, true, player);
            } else {
                item = getItemStack(itemFormat, serverId, false, player);
            }
            menu.setItem(slot, item);
            updateInfo(itemFormat, serverId, itemFormat.awaysOnline);
        }
        PlayerMenu.updatePlayerMenu(player, menuFormat);
        return menu;
    }

    private static ItemStack getItemStack(ItemFormat itemFormat, String serverId, boolean alwaysOnline, Player p) {
        Material material;
        byte data;
        int online;

        if (itemFormat.Show_Players_On_Item_Amount && ServersInfo.getServerOnlinePlayers(serverId) != -1) {
            online = ServersInfo.getServerOnlinePlayers(serverId);
        } else {
            online = 1;
        }

        ArrayList<String> motdList = new ArrayList<>();

        Iterator<String> loreList;

        String nameLine;
        String loreLineRaw;
        String loreLine;

        if (alwaysOnline) {
            material = Material.getMaterial(itemFormat.ID_Online);
            data = (byte) itemFormat.DATA_Online;
            loreList = itemFormat.onlineMotd.get(itemFormat.onlineFrame).iterator();

            while (loreList.hasNext()) {
                loreLineRaw = loreList.next();
                loreLine = VariablesUtils.getFinished(loreLineRaw, serverId, p);
                motdList.add(loreLine);
            }

            nameLine = VariablesUtils.getFinished(itemFormat.onlineNameList.get(itemFormat.onlineFrameNames), serverId, p);
        } else {
            if (ServersInfo.getServerPing(serverId) == -1.0D) {
                material = Material.getMaterial(itemFormat.ID_Offline);
                data = (byte) itemFormat.DATA_Offline;
                loreList = ((List) itemFormat.offlineMotd.get(itemFormat.offlineFrame)).iterator();

                while (loreList.hasNext()) {
                    loreLineRaw = loreList.next();
                    loreLine = VariablesUtils.getFinished(loreLineRaw, serverId, p);
                    motdList.add(loreLine);
                }

                nameLine = VariablesUtils.getFinished(itemFormat.offlineNameList.get(itemFormat.offlineFrameNames), serverId, p);
            } else {
                if (ServersInfo.getServerMaxPlayers(serverId) == ServersInfo.getServerOnlinePlayers(serverId) && !itemFormat.fullMotd.isEmpty() && !itemFormat.fullNameList.isEmpty()) {
                    material = Material.getMaterial(itemFormat.ID_Full);
                    data = (byte) itemFormat.DATA_Full;
                    loreList = ((List) itemFormat.fullMotd.get(itemFormat.fullFrame)).iterator();

                    while (loreList.hasNext()) {
                        loreLineRaw = loreList.next();
                        loreLine = VariablesUtils.getFinished(loreLineRaw, serverId, p);
                        motdList.add(loreLine);
                    }

                    nameLine = VariablesUtils.getFinished(itemFormat.fullNameList.get(itemFormat.fullFrameNames), serverId, p);
                } else if (itemFormat.motdField != null && ServersInfo.getServerMotd(serverId).contains(itemFormat.motdField)) {
                    material = Material.getMaterial(itemFormat.idMotdFull);
                    data = (byte) itemFormat.dataMotdFull;
                    loreList = ((List) itemFormat.fullMotd.get(itemFormat.fullFrame)).iterator();

                    while (loreList.hasNext()) {
                        loreLineRaw = loreList.next();
                        loreLine = VariablesUtils.getFinished(loreLineRaw, serverId, p);
                        motdList.add(loreLine);
                    }

                    nameLine = VariablesUtils.getFinished(itemFormat.motdFullNameList.get(itemFormat.onlineFrameNames), serverId, p);
                } else {
                    material = Material.getMaterial(itemFormat.ID_Online);
                    data = (byte) itemFormat.DATA_Online;
                    loreList = itemFormat.onlineMotd.get(itemFormat.onlineFrame).iterator();

                    while (loreList.hasNext()) {
                        loreLineRaw = loreList.next();
                        loreLine = VariablesUtils.getFinished(loreLineRaw, serverId, p);
                        motdList.add(loreLine);
                    }

                    nameLine = VariablesUtils.getFinished(itemFormat.onlineNameList.get(itemFormat.onlineFrameNames), serverId, p);
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

    private static ItemFormat updateInfo(ItemFormat var0, String var1, boolean var2) {
        if (var2) {
            if (var0.onlineMotd.size() - 1 == var0.onlineFrame) {
                var0.onlineFrame = 0;
            } else {
                ++var0.onlineFrame;
            }

            if (var0.onlineNameList.size() - 1 == var0.onlineFrameNames) {
                var0.onlineFrameNames = 0;
            } else {
                ++var0.onlineFrameNames;
            }

            return var0;
        } else {
            if (ServersInfo.getServerPing(var1) == -1.0D) {
                if (var0.offlineMotd.size() - 1 == var0.offlineFrame) {
                    var0.offlineFrame = 0;
                } else {
                    ++var0.offlineFrame;
                }

                if (var0.offlineNameList.size() - 1 == var0.offlineFrameNames) {
                    var0.offlineFrameNames = 0;
                } else {
                    ++var0.offlineFrameNames;
                }
            }

            if (ServersInfo.getServerPing(var1) != -1.0D) {
                if (ServersInfo.getServerMaxPlayers(var1) == ServersInfo.getServerOnlinePlayers(var1)) {
                    if (var0.fullMotd.size() - 1 == var0.fullFrame) {
                        var0.fullFrame = 0;
                    } else {
                        ++var0.fullFrame;
                    }

                    if (var0.fullNameList.size() - 1 == var0.fullFrameNames) {
                        var0.fullFrameNames = 0;
                    } else {
                        ++var0.fullFrameNames;
                    }
                } else {
                    if (var0.onlineMotd.size() - 1 == var0.onlineFrame) {
                        var0.onlineFrame = 0;
                    } else {
                        ++var0.onlineFrame;
                    }

                    if (var0.onlineNameList.size() - 1 == var0.onlineFrameNames) {
                        var0.onlineFrameNames = 0;
                    } else {
                        ++var0.onlineFrameNames;
                    }
                }
            }

            return var0;
        }
    }
}
