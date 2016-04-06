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
        ArrayList<String> ymlName = new ArrayList<>();
        ArrayList var1 = new ArrayList();
        File[] var5;
        int var4 = (var5 = (new File(pl.getDataFolder() + File.separator + "Menus")).listFiles()).length;

        for (int var3 = 0; var3 < var4; ++var3) {
            File ymlFile = var5[var3];
            if (ymlName.contains(ymlFile.getName())) {
                System.out.print("[UltimateMenu] You have 2 menu files with the name " + ymlFile.getName() + "!");
                System.out.print("Not loading one!");
            } else {
                ymlName.add(ymlFile.getName());
                YamlConfiguration yml = YamlConfiguration.loadConfiguration(ymlFile);
                MenuFormat format = new MenuFormat();
                format.slot = yml.getInt("Menu.Slots");
                format.title = yml.getString("Menu.Menu_Name").replaceAll("&", "§");
                ArrayList var8 = new ArrayList();
                ArrayList var9 = new ArrayList();
                HashMap var10 = new HashMap();
                Iterator var12 = yml.getKeys(false).iterator();

                while (true) {
                    while (var12.hasNext()) {
                        String var11 = (String) var12.next();
                        if (var11.equalsIgnoreCase("Menu")) {
                            String var13 = yml.getString("Menu.Menu_Name");
                            if (var1.contains(var13)) {
                                System.out.print("[UltimateMenu] You have 2 menu files with the same title \'" + var13 + "\'");
                                System.out.print("Please change to load the 2 menus!");
                                continue;
                            }

                            var1.add(var13);
                        }

                        if (!var11.equalsIgnoreCase("Menu")) {
                            if (var9.contains(var11)) {
                                System.out.print("[UltimateMenu] The item " + var11 + " on Menu " + ymlFile.getName() + " has set 2 times! Please change !");
                            } else {
                                var9.add(var11);
                                int var20 = yml.getInt(var11 + ".Slot");
                                Iterator var16;
                                if (!var10.containsValue(Integer.valueOf(var20))) {
                                    var10.put(var11, Integer.valueOf(var20));
                                    ItemFormat item = new ItemFormat();
                                    item.id = ymlFile.getName() + "-" + var11;
                                    item.onlineNames.addAll(yml.getStringList(var11 + ".Name_Online"));
                                    item.offlineNames.addAll(yml.getStringList(var11 + ".Name_Offline"));
                                    item.FullNames.addAll(yml.getStringList(var11 + ".Name_Full"));
                                    String var22;
                                    if (yml.isSet(var11 + ".Lore_Online")) {
                                        var16 = yml.getConfigurationSection(var11 + ".Lore_Online").getKeys(false).iterator();

                                        while (var16.hasNext()) {
                                            var22 = (String) var16.next();
                                            item.onlineMotd.add(yml.getStringList(var11 + ".Lore_Online." + var22));
                                        }
                                    }

                                    if (yml.isSet(var11 + ".Lore_Offline")) {
                                        var16 = yml.getConfigurationSection(var11 + ".Lore_Offline").getKeys(false).iterator();

                                        while (var16.hasNext()) {
                                            var22 = (String) var16.next();
                                            item.offlineMotd.add(yml.getStringList(var11 + ".Lore_Offline." + var22));
                                        }
                                    }

                                    if (yml.isSet(var11 + ".Lore_Full")) {
                                        var16 = yml.getConfigurationSection(var11 + ".Lore_Full").getKeys(false).iterator();

                                        while (var16.hasNext()) {
                                            var22 = (String) var16.next();
                                            item.FullMotd.add(yml.getStringList(var11 + ".Lore_Full." + var22));
                                        }
                                    }

                                    if (yml.isSet(var11 + ".ID_Full")) {
                                        item.ID_Full = yml.getInt(var11 + ".ID_Full");
                                    }
                                    if (yml.isSet(var11 + ".Motd_Full_Exp")) {
                                        item.motdField = yml.getString(var11 + ".Motd_Full_Exp");
                                        item.idMotdFull = yml.getInt(var11 + ".ID_Motd_Full", 35);
                                        item.dataMotdFull = yml.getInt(var11 + ".Data_Motd_Full", 1);
                                        item.motdFullNames.addAll(yml.getStringList(var11 + ".Name_Motd_Full"));
                                    }
                                    item.ID_Offline = yml.getInt(var11 + ".ID_Offline");
                                    item.ID_Online = yml.getInt(var11 + ".ID_Online");
                                    if (yml.isSet(var11 + ".DATA_Full")) {
                                        item.DATA_Full = yml.getInt(var11 + ".DATA_Full");
                                    }

                                    item.DATA_Offline = yml.getInt(var11 + ".DATA_Offline");
                                    item.DATA_Online = yml.getInt(var11 + ".DATA_Online");
                                    item.Close_On_Click = yml.getBoolean(var11 + ".CloseMenu");
                                    String[] var18;
                                    int var17 = (var18 = yml.getString(var11 + ".Commands").split(";")).length;

                                    for (int var23 = 0; var23 < var17; ++var23) {
                                        var22 = var18[var23];
                                        item.commands.add(var22);
                                    }

                                    item.Show_Players_On_Item_Amount = yml.getBoolean(var11 + ".Show_Players_On_Item_Amount");
                                    item.Slot = yml.getInt(var11 + ".Slot");
                                    item.awaysOnline = yml.getBoolean(var11 + ".AwaysOnline");
                                    var8.add(item);
                                } else {
                                    System.out.print("[UltimateMenu] You have 2 menu items with the same slot ocuped!");
                                    String var14 = "";
                                    var16 = var10.entrySet().iterator();

                                    while (var16.hasNext()) {
                                        Entry var15 = (Entry) var16.next();
                                        if (((Integer) var15.getValue()).intValue() == var20) {
                                            var14 = (String) var15.getKey();
                                        }
                                    }

                                    System.out.print("File: " + ymlFile.getName() + " Menu Item: " + var11 + " , " + var14);
                                    System.out.print("Please change to load the 2 items!");
                                }
                            }
                        }
                    }

                    var12 = var8.iterator();

                    while (var12.hasNext()) {
                        ItemFormat var19 = (ItemFormat) var12.next();
                        format.itemMap.put(Integer.valueOf(var19.Slot), var19);
                    }

                    menuMap.put(ymlFile.getName().replace(".yml", ""), format);
                    break;
                }
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
