package com.mengcraft.ultimatemenu.task;

import com.mengcraft.ultimatemenu.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class LoadMenus {

    private static Plugin pl;
    private static HashMap<String, MenuFormat> menuMap;

    static {
        pl = Main.pl;
        menuMap = new HashMap<>();
    }

    public static MenuFormat getMenu(String menuName) {
        return menuMap.containsKey(menuName) ? menuMap.get(menuName) : null;
    }

    public static void load() {
        menuMap.clear();

        ArrayList<String> fileNameList = new ArrayList<>();
        ArrayList<String> menuNameList = new ArrayList<>();

        File[] fileList = new File(pl.getDataFolder() + File.separator + "Menus").listFiles();
        for (File file : fileList != null ? fileList : new File[]{}) {
            if (fileNameList.contains(file.getName())) {
                System.out.print("[UltimateMenu] You have 2 menu files with the name " + file.getName() + "!");
                System.out.print("Not loading one!");
            } else {
                fileNameList.add(file.getName());
                YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);

                MenuFormat format = new MenuFormat();
                format.slot = yml.getInt("Menu.Slots");
                format.name = yml.getString("Menu.Menu_Name").replaceAll("&", "§");

                HashMap<String, Integer> slotMap = new HashMap<>();

                ArrayList<ItemFormat> itemList = new ArrayList<>();
                ArrayList<String> itemNameList = new ArrayList<>();

                for (String itemName : yml.getKeys(false)) {
                    if (itemName.equalsIgnoreCase("Menu")) {
                        String menuName = yml.getString("Menu.Menu_Name");
                        if (menuNameList.contains(menuName)) {
                            throw new RuntimeException("Multiple menu with same title " + menuName + '!');
                        }
                        menuNameList.add(menuName);
                    } else {
                        if (itemNameList.contains(itemName)) {
                            throw new RuntimeException("Multiple item " + itemName + " on menu " + file.getName() + '!');
                        }
                        itemNameList.add(itemName);
                        int slot = yml.getInt(itemName + ".Slot");
                        if (slotMap.containsValue(slot)) {
                            throw new RuntimeException("Multiple slot " + slot + " on menu " + file.getName() + '!');
                        }
                        slotMap.put(itemName, slot);
                        ItemFormat item = new ItemFormat();
                        item.id = file.getName() + "-" + itemName;
                        item.onlineNameList.addAll(yml.getStringList(itemName + ".Name_Online"));
                        item.offlineNameList.addAll(yml.getStringList(itemName + ".Name_Offline"));
                        item.fullNameList.addAll(yml.getStringList(itemName + ".Name_Full"));

                        if (yml.isSet(itemName + ".Lore_Online")) {
                            for (String line : yml.getConfigurationSection(itemName + ".Lore_Online").getKeys(false)) {
                                item.onlineMotd.add(yml.getStringList(itemName + ".Lore_Online." + line));
                            }
                        }

                        if (yml.isSet(itemName + ".Lore_Offline")) {
                            for (String o : yml.getConfigurationSection(itemName + ".Lore_Offline").getKeys(false)) {
                                item.offlineMotd.add(yml.getStringList(itemName + ".Lore_Offline." + o));
                            }
                        }

                        if (yml.isSet(itemName + ".Lore_Full")) {
                            for (String o : yml.getConfigurationSection(itemName + ".Lore_Full").getKeys(false)) {
                                item.FullMotd.add(yml.getStringList(itemName + ".Lore_Full." + o));
                            }
                        }

                        if (yml.isSet(itemName + ".ID_Full")) {
                            item.ID_Full = yml.getInt(itemName + ".ID_Full");
                        }

                        if (yml.isSet(itemName + ".Motd_Full_Exp")) {
                            item.motdField = yml.getString(itemName + ".Motd_Full_Exp");
                            item.idMotdFull = yml.getInt(itemName + ".ID_Motd_Full", 35);
                            item.dataMotdFull = yml.getInt(itemName + ".Data_Motd_Full", 1);
                            item.motdFullNameList.addAll(yml.getStringList(itemName + ".Name_Motd_Full"));
                        }

                        item.ID_Offline = yml.getInt(itemName + ".ID_Offline");
                        item.ID_Online = yml.getInt(itemName + ".ID_Online");

                        if (yml.isSet(itemName + ".DATA_Full")) {
                            item.DATA_Full = yml.getInt(itemName + ".DATA_Full");
                        }

                        item.DATA_Offline = yml.getInt(itemName + ".DATA_Offline");
                        item.DATA_Online = yml.getInt(itemName + ".DATA_Online");
                        item.Close_On_Click = yml.getBoolean(itemName + ".CloseMenu");

                        String[] commandList = yml.getString(itemName + ".Commands").split(";");
                        for (String command : commandList) {
                            item.commandList.add(command);
                        }

                        item.Show_Players_On_Item_Amount = yml.getBoolean(itemName + ".Show_Players_On_Item_Amount");
                        item.slot = yml.getInt(itemName + ".Slot");
                        item.awaysOnline = yml.getBoolean(itemName + ".AwaysOnline");

                        itemList.add(item);
                    }
                }

                for (ItemFormat item : itemList) {
                    format.itemMap.put(item.slot, item);
                }

                menuMap.put(file.getName().replace(".yml", ""), format);
            }
        }
    }

    public static void config(FileConfiguration var0, File var1) {
        ArrayList var2 = new ArrayList();
        var2.add("&aS");
        var2.add("&aSe");
        var2.add("&aSer");
        var2.add("&aServ");
        var2.add("&aServe");
        var2.add("&aServer");
        var2.add("&aServer ");
        var2.add("&aServer O");
        var2.add("&aServer On");
        var2.add("&aServer Onl");
        var2.add("&aServer Onli");
        var2.add("&aServer Onlin");
        var2.add("&aServer Online");
        var2.add("&aServer Online!");
        var0.addDefault("Item1.Name_Online", var2);
        ArrayList var3 = new ArrayList();
        var3.add("&4S");
        var3.add("&4Se");
        var3.add("&4Ser");
        var3.add("&4Serv");
        var3.add("&4Serve");
        var3.add("&4Server");
        var3.add("&4Server ");
        var3.add("&4Server O");
        var3.add("&4Server Of");
        var3.add("&4Server Off");
        var3.add("&4Server Offl");
        var3.add("&4Server Offli");
        var3.add("&4Server Offlin");
        var3.add("&4Server Offline");
        var0.addDefault("Item1.Name_Offline", var3);
        ArrayList var4 = new ArrayList();
        var4.add("&4S");
        var4.add("&4Se");
        var4.add("&4Ser");
        var4.add("&4Serv");
        var4.add("&4Serve");
        var4.add("&4Server");
        var4.add("&4Server ");
        var4.add("&4Server F");
        var4.add("&4Server Fu");
        var4.add("&4Server Ful");
        var4.add("&4Server Full");
        var0.addDefault("Item1.Name_Full", var4);
        var0.addDefault("Item1.ID_Full", Integer.valueOf(159));
        var0.addDefault("Item1.DATA_Full", Integer.valueOf(8));
        var0.addDefault("Item1.ID_Online", Integer.valueOf(276));
        var0.addDefault("Item1.DATA_Online", Integer.valueOf(0));
        var0.addDefault("Item1.ID_Offline", Integer.valueOf(159));
        var0.addDefault("Item1.DATA_Offline", Integer.valueOf(14));
        var0.addDefault("Item1.Slot", Integer.valueOf(0));
        var0.addDefault("Item1.CloseMenu", Boolean.valueOf(false));
        var0.addDefault("Item1.AwaysOnline", Boolean.valueOf(true));
        var0.addDefault("Item1.Commands", "asConsole:say teste;asOP:say teste;send:pvp");
        var0.addDefault("Item1.Show_Players_On_Item_Amount", Boolean.valueOf(true));
        var0.addDefault("Item1.Ping_Server", Boolean.valueOf(true));
        var0.addDefault("Item1.Server_IP", "localhost");
        var0.addDefault("Item1.Port", Integer.valueOf(25565));
        ArrayList var5 = new ArrayList();
        var5.add("&aThis is a Online Lore");
        var5.add("&aMotd: {Motd}");
        var5.add("&aMax Players: {Max}");
        var5.add("&aOnline Players: {Online}");
        var5.add("&aPing(Ms): {Ping}");
        var0.addDefault("Item1.Lore_Online.1", var5);
        ArrayList var6 = new ArrayList();
        var6.add("&aThis is a Online Lore 2");
        var6.add("&aLine 2 [X] This is a block <<");
        var0.addDefault("Item1.Lore_Online.2", var6);
        ArrayList var7 = new ArrayList();
        var7.add("&aThis is a offline Lore");
        var7.add("&aYou cant use variables here");
        var0.addDefault("Item1.Lore_Offline.1", var7);
        ArrayList var8 = new ArrayList();
        var8.add("&aThis is a offline Lore");
        var8.add("&aLine 2 [X] This is a block <<");
        var0.addDefault("Item1.Lore_Offline.2", var8);
        ArrayList var9 = new ArrayList();
        var9.add("&2This is a full Lore");
        var9.add("&2You can use variables here");
        var0.addDefault("Item1.Lore_Full.1", var9);
        ArrayList var10 = new ArrayList();
        var10.add("&2This is a Full Lore");
        var10.add("&2Motd: {Motd}");
        var10.add("&2Max Players: {Max}");
        var10.add("&2Online Players: {Online}");
        var10.add("&2Ping(Ms): {Ping}");
        var0.addDefault("Item1.Lore_Full.2", var10);

        try {
            var0.options().copyDefaults(true);
            var0.save(var1);
        } catch (Exception var12) {
        }

    }
}
