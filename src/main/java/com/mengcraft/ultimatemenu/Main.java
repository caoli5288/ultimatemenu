package com.mengcraft.ultimatemenu;

import com.mengcraft.ultimatemenu.listener.Mobs;
import com.mengcraft.ultimatemenu.listener.MobsListener;
import com.mengcraft.ultimatemenu.listener.SchedulerFix;
import com.mengcraft.ultimatemenu.listener.InventoryCloseListener;
import com.mengcraft.ultimatemenu.listener.InventoryListener;
import com.mengcraft.ultimatemenu.task.MenuManager;
import com.mengcraft.ultimatemenu.task.PlayerMenu;
import com.mengcraft.ultimatemenu.ping.PingTask;
import com.mengcraft.ultimatemenu.ping.ServerInfo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
   public static boolean Vars = false;
   public static Plugin pl;

   public void onEnable() {
      if(this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
         Vars = true;
         System.out.print("TokenEnchant API enabled for UltimateMenu!");
      }

      pl = this;
      pl.getConfig().addDefault("Ping_Delay_Seconds", Integer.valueOf(5));
      pl.getConfig().addDefault("Time_Out", Integer.valueOf(1000));
      pl.getConfig().addDefault("Menu_Update_Time", Integer.valueOf(10));
      CreateFile();
      File[] var4;
      int var3 = (var4 = (new File(pl.getDataFolder() + File.separator + "Menus")).listFiles()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         File var1 = var4[var2];
         YamlConfiguration var5 = YamlConfiguration.loadConfiguration(var1);
         if(!var5.isSet("Menu.Give_Item_On_Join")) {
            var5.addDefault("Menu.Menu_Name", "&aServers");
            var5.addDefault("Menu.Slots", Integer.valueOf(9));
            MenuManager.config(var5, var1);
         }

         try {
            var5.options().copyDefaults(true);
            var5.save(var1);
         } catch (IOException var7) {
            System.out.print(var7);
         }
      }

      PluginManager var8 = Bukkit.getPluginManager();
      var8.registerEvents(new InventoryCloseListener(), this);
      var8.registerEvents(new InventoryListener(), this);
      var8.registerEvents(new MobsListener(), this);
      Mobs.LoadMobs();
      this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
      (new SchedulerFix()).runTaskTimer(this, 0L, 20L);
      (new PlayerMenu()).runTaskTimerAsynchronously(this, 0L, (long)pl.getConfig().getInt("Menu_Update_Time"));
      (new PingTask()).runTaskTimerAsynchronously(this, 0L, (long)(pl.getConfig().getInt("Ping_Delay_Seconds") * 20));
      ServerInfo.init();
      MenuManager.MANAGER.load();
      pl.getConfig().options().copyDefaults(true);
      pl.saveConfig();
   }

   public static void CreateFile() {
      File var0 = new File(pl.getDataFolder().getAbsolutePath() + File.separator + "Menus" + File.separator + "Menu.yml");
      if(!var0.exists()) {
         try {
            var0.createNewFile();
         } catch (Exception var4) {
            System.out.print("[UltimateMenu] Failed to create datafolder!");
         }
      }

      YamlConfiguration var1 = YamlConfiguration.loadConfiguration(var0);

      try {
         var1.save(var0);
      } catch (Exception var3) {
         System.out.print("[UltimateMenu] Failed to save default config!");
      }

   }

   public void onDisable() {
      pl = null;
      MobsListener.offServer();
   }

   public boolean onCommand(CommandSender var1, Command var2, String var3, String[] var4) {
      if(!var2.getName().equalsIgnoreCase("UltimateMenu") && !var2.getName().equalsIgnoreCase("um")) {
         return false;
      } else {
         ArrayList var7;
         File var8;
         int var9;
         int var10;
         File[] var11;
         if(var1 instanceof Player) {
            Player var13 = (Player)var1;
            if(var4.length == 0) {
               if(var13.hasPermission("UltimateMenu.Help")) {
                  sendHelpMenu(var1);
                  return false;
               } else {
                  var13.sendMessage("§cYou dont have permission to do this!");
                  return false;
               }
            } else {
               String var16;
               if(var4.length == 2) {
                  if(!var4[0].equalsIgnoreCase("Open")) {
                     if(!var13.hasPermission("UltimateMenu.Help")) {
                        var13.sendMessage("§cYou dont have permission to do this!");
                        return false;
                     } else {
                        sendHelpMenu(var1);
                        return false;
                     }
                  } else if(!var13.hasPermission("UltimateMenu.OpenMenu." + var4[1]) && !var13.hasPermission("UltimateMenu.OpenMenu.*")) {
                     var13.sendMessage("§cYou dont have permission to do this!");
                     return false;
                  } else {
                     var16 = var4[1].replace(".yml", "");
                     var7 = new ArrayList();
                     var10 = (var11 = (new File(pl.getDataFolder() + File.separator + "Menus")).listFiles()).length;

                     for(var9 = 0; var9 < var10; ++var9) {
                        var8 = var11[var9];
                        var7.add(var8.getName().replace(".yml", ""));
                     }

                     if(!var7.contains(var4[1])) {
                        var13.sendMessage("§cThis menu dont exist!");
                        return false;
                     } else {
                        PlayerMenu.openMenu(var13, var16);
                        return false;
                     }
                  }
               } else if(var4.length == 3) {
                  if(!var4[0].equalsIgnoreCase("Open")) {
                     sendHelpMenu(var1);
                     return false;
                  } else if(!var13.hasPermission("UltimateMenu.OpenMenu.Others")) {
                     var13.sendMessage("§cYou dont have permission to do this!");
                     return false;
                  } else {
                     var16 = var4[1].replace(".yml", "");
                     Player var18 = Bukkit.getPlayerExact(var4[2]);
                     ArrayList var20 = new ArrayList();
                     File[] var12;
                     int var23 = (var12 = (new File(pl.getDataFolder() + File.separator + "Menus")).listFiles()).length;

                     for(var10 = 0; var10 < var23; ++var10) {
                        File var22 = var12[var10];
                        var20.add(var22.getName().replace(".yml", ""));
                     }

                     if(!var20.contains(var4[1])) {
                        var13.sendMessage("§cThis menu dont exist!");
                        return false;
                     } else if(var18 == null) {
                        var13.sendMessage("§cThis player dont exist!");
                        return false;
                     } else {
                        PlayerMenu.openMenu(var18, var16);
                        return false;
                     }
                  }
               } else if(var4.length < 1) {
                  return false;
               } else if(var13.hasPermission("UltimateMenu.CreateMob") && var4[0].equalsIgnoreCase("createmob")) {
                  if(var4.length == 10) {
                     ArrayList var15 = new ArrayList();
                     var15.add("Creeper");
                     var15.add("Cow");
                     var15.add("Villager");
                     var15.add("Zombie");
                     var15.add("Sheep");
                     var15.add("Silverfish");
                     var15.add("Chicken");
                     var15.add("Skeleton");
                     var15.add("Pig");
                     if(var15.contains(var4[1])) {
                        if(!var4[3].equalsIgnoreCase("true") && !var4[3].equalsIgnoreCase("false")) {
                           var13.sendMessage("§a---------------------- Mobs ---------------------");
                           var13.sendMessage("§f - §aUsage:");
                           var13.sendMessage("§f - /UltimateMenu createmob");
                           var13.sendMessage("§f - §a{MobType} §fEx: §aSkeleton");
                           var13.sendMessage("§f - §a{NameOfTheMob} §fEx: §aCoolMob");
                           var13.sendMessage("§f - §a{NameVisible} §fEx: §atrue or False");
                           var13.sendMessage("§f - §a{Menu} §fEx: §amenu");
                           var13.sendMessage("§f - §a{Helm} §fEx: §a298 add :t to enchant the item");
                           var13.sendMessage("§f - §a{ChestPlate} §fEx: §a299 add :t to enchant the item");
                           var13.sendMessage("§f - §a{Leggings} §fEx: §a300 add :t to enchant the item");
                           var13.sendMessage("§f - §a{Boots} §fEx: §a301 add :t to enchant the item");
                           var13.sendMessage("§f - §a{HandItem:Data} §fEx: §a276:Data add :t to enchant the item");
                           var13.sendMessage("§a---------------------- Mobs ---------------------");
                           return false;
                        } else {
                           Mobs.SpawnMobs(var13, var4[1], var4[2].replaceAll("&", "§"), var4[3], var4[4], var4[5], var4[6], var4[7], var4[8], var4[9]);
                           return false;
                        }
                     } else {
                        var13.sendMessage("§a------------------ Mobs -----------------");
                        var13.sendMessage("§f - §aCreeper§f - §aCow§f - §aVillager");
                        var13.sendMessage("§f - §aZombie§f - §aSheep§f - §aSilverfish");
                        var13.sendMessage("§f - §aChicken§f - §aSkeleton§f - §aPig");
                        var13.sendMessage("§a------------------ Mobs -----------------");
                        return false;
                     }
                  } else {
                     var13.sendMessage("§a---------------------- Mobs ---------------------");
                     var13.sendMessage("§f - §aUsage:");
                     var13.sendMessage("§f - /UltimateMenu createmob");
                     var13.sendMessage("§f - §a{MobType} §fEx: §aSkeleton");
                     var13.sendMessage("§f - §a{NameOfTheMob} §fEx: §aCoolMob");
                     var13.sendMessage("§f - §a{NameVisible} §fEx: §atrue or False");
                     var13.sendMessage("§f - §a{Menu} §fEx: §amenu");
                     var13.sendMessage("§f - §a{Helm} §fEx: §a298 add :t to enchant the item");
                     var13.sendMessage("§f - §a{ChestPlate} §fEx: §a299 add :t to enchant the item");
                     var13.sendMessage("§f - §a{Leggings} §fEx: §a300 add :t to enchant the item");
                     var13.sendMessage("§f - §a{Boots} §fEx: §a301 add :t to enchant the item");
                     var13.sendMessage("§f - §a{HandItem:Data} §fEx: §a276:Data add :t to enchant the item");
                     var13.sendMessage("§a---------------------- Mobs ---------------------");
                     return false;
                  }
               } else if(var4[0].equalsIgnoreCase("reload")) {
                  if(!var13.hasPermission("UltimateMenu.Reload")) {
                     var13.sendMessage("§cYou dont have permission to do this!");
                     return false;
                  } else {
                     PlayerMenu.quitAllPlayers();
                     ServerInfo.init();
                     MenuManager.MANAGER.load();
                     var13.sendMessage("§aConfig reloaded!");
                     return false;
                  }
               } else if(!var4[0].equalsIgnoreCase("list")) {
                  if(!var13.hasPermission("UltimateMenu.Help")) {
                     var13.sendMessage("§cYou dont have permission to do this!");
                     return false;
                  } else {
                     sendHelpMenu(var1);
                     return false;
                  }
               } else if(!var13.hasPermission("UltimateMenu.List")) {
                  var13.sendMessage("§cYou dont have permission to do this!");
                  return false;
               } else {
                  var13.sendMessage("§a------------- §bUltimateMenu List §a------------");
                  File[] var21;
                  int var19 = (var21 = (new File(pl.getDataFolder() + File.separator + "Menus")).listFiles()).length;

                  for(int var17 = 0; var17 < var19; ++var17) {
                     File var14 = var21[var17];
                     var13.sendMessage("§f - §a" + var14.getName());
                  }

                  return false;
               }
            }
         } else if(var4.length == 3 && var4[0].equalsIgnoreCase("Open")) {
            String var5 = var4[1].replace(".yml", "");
            Player var6 = Bukkit.getPlayerExact(var4[2]);
            var7 = new ArrayList();
            var10 = (var11 = (new File(pl.getDataFolder() + File.separator + "Menus")).listFiles()).length;

            for(var9 = 0; var9 < var10; ++var9) {
               var8 = var11[var9];
               var7.add(var8.getName().replace(".yml", ""));
            }

            if(!var7.contains(var4[1])) {
               var1.sendMessage("§cThis menu dont exist!");
               return false;
            } else if(var6 == null) {
               var1.sendMessage("§cThis player dont exist!");
               return false;
            } else {
               PlayerMenu.openMenu(var6, var5);
               return false;
            }
         } else {
            sendHelpMenu(var1);
            return false;
         }
      }
   }

   public static void sendHelpMenu(CommandSender var0) {
      var0.sendMessage("§a------------------ Help Menu -----------------");
      var0.sendMessage("§a/UltimateMenu open §cFile §b-Open the Menu on File");
      var0.sendMessage("§a/UltimateMenu list §b- Show loaded menus");
      var0.sendMessage("§a/UltimateMenu reload §b- Reload The config");
      var0.sendMessage("§a/UltimateMenu help §b- Open the help menu");
      var0.sendMessage("§a/UltimateMenu createmob §b- Open the createmob help");
      var0.sendMessage("§a------------------ Help Menu -----------------");
   }
}
