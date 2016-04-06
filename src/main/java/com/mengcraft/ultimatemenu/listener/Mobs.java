package com.mengcraft.ultimatemenu.listener;

import com.mengcraft.ultimatemenu.Main;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Mobs {
   private static Plugin pl;

   static {
      pl = Main.pl;
   }

   public static void LoadMobs() {
      MobsListener.clearInfos();

      LivingEntity var10;
      MobFormat var14;
      for(Iterator var1 = getConf().getKeys(false).iterator(); var1.hasNext(); MobsListener.addMob(var10.getUniqueId(), var14)) {
         String var0 = (String)var1.next();
         World var2 = pl.getServer().getWorld(getConf().getString(var0 + ".World"));
         Double var3 = Double.valueOf((double)getConf().getInt(var0 + ".locX"));
         Double var4 = Double.valueOf((double)getConf().getInt(var0 + ".locY"));
         Double var5 = Double.valueOf((double)getConf().getInt(var0 + ".locZ"));
         Float var6 = Float.valueOf((float)getConf().getInt(var0 + ".locPitch"));
         Float var7 = Float.valueOf((float)getConf().getInt(var0 + ".locYaw"));
         Location var8 = new Location(var2, var3.doubleValue(), var4.doubleValue(), var5.doubleValue(), var7.floatValue(), var6.floatValue());
         EntityType var9 = EntityType.fromName(getConf().getString(var0 + ".Type"));
         var10 = (LivingEntity)var8.getWorld().spawnEntity(var8, var9);
         var10.setHealth(1.0D);
         var10.setCustomName(getConf().getString(var0 + ".Name"));
         var10.setCustomNameVisible(getConf().getBoolean(var0 + ".Visible"));
         File var11 = new File(pl.getDataFolder() + File.separator + "Mobs.yml");
         YamlConfiguration var12 = YamlConfiguration.loadConfiguration(var11);
         var12.set(var0 + ".Mob_UUID", var10.getUniqueId().toString());
         String var13 = getConf().getString(var0 + ".Menu");
         var13 = var13.replace(".yml", "");
         var14 = new MobFormat();
         var14.menu = var13;
         var14.mobEntity = var10;
         var14.mobLoc = var10.getLocation();
         var14.mobUUID = var10.getUniqueId();

         try {
            var12.save(var11);
         } catch (IOException var27) {
            var27.printStackTrace();
         }

         String[] var15 = getConf().getString(var0 + ".Armor.HandItem").split(":");
         String[] var16 = getConf().getString(var0 + ".Armor.Helmet").split(":");
         String[] var17 = getConf().getString(var0 + ".Armor.ChestPlate").split(":");
         String[] var18 = getConf().getString(var0 + ".Armor.Leggings").split(":");
         String[] var19 = getConf().getString(var0 + ".Armor.Boots").split(":");
         ItemStack var20 = new ItemStack(Material.getMaterial(Integer.parseInt(var16[0])));
         ItemStack var21 = new ItemStack(Material.getMaterial(Integer.parseInt(var17[0])));
         ItemStack var22 = new ItemStack(Material.getMaterial(Integer.parseInt(var18[0])));
         ItemStack var23 = new ItemStack(Material.getMaterial(Integer.parseInt(var19[0])));
         ItemStack var24 = new ItemStack(Material.getMaterial(Integer.parseInt(var15[0])));
         if(var15.length >= 2) {
            var24 = new ItemStack(Material.getMaterial(Integer.parseInt(var15[0])), 1, (short)Integer.parseInt(var15[1]));
            if(var15.length == 3) {
               var24.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            }
         }

         if(var16.length == 2) {
            var20.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
         }

         if(var17.length == 2) {
            var21.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
         }

         if(var18.length == 2) {
            var22.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
         }

         if(var19.length == 2) {
            var23.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
         }

         var10.getEquipment().setHelmet(var20);
         var10.getEquipment().setChestplate(var21);
         var10.getEquipment().setLeggings(var22);
         var10.getEquipment().setBoots(var23);
         var10.getEquipment().setItemInHand(var24);
         PotionEffect var25 = new PotionEffect(PotionEffectType.SLOW, 10000000, 100);
         var10.addPotionEffect(var25);
         var14.equipamento = var10.getEquipment();
         if(pl.getServer().getVersion().contains("1.8")) {
            ArmorStand var26 = (ArmorStand)var8.getWorld().spawnEntity(var8, EntityType.ARMOR_STAND);
            var26.setCustomName(getConf().getString(var0 + ".Name"));
            var26.setCustomNameVisible(getConf().getBoolean(var0 + ".Visible"));
            var26.setVisible(false);
            var26.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000000, 1000));
            var14.hasArmorStand = true;
            var14.mobArmorStand = var26;
            var10.setCustomName("");
            var10.setCustomNameVisible(false);
         }
      }

   }

   public static void SpawnMobs(Player var0, String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9) {
      var2 = var2.replace("_", " ");
      File var10 = new File(pl.getDataFolder() + File.separator + "Mobs.yml");
      YamlConfiguration var11 = YamlConfiguration.loadConfiguration(var10);
      if(!var10.exists()) {
         try {
            var10.createNewFile();
         } catch (IOException var31) {
            var31.printStackTrace();
         }
      }

      boolean var12 = false;
      if(var3.equalsIgnoreCase("true")) {
         var12 = true;
      }

      Location var13 = var0.getLocation();
      LivingEntity var14 = (LivingEntity)var0.getWorld().spawnEntity(var13, EntityType.fromName(var1));
      var14.setHealth(1.0D);
      var14.setCustomName(var2);
      var14.setCustomNameVisible(var12);
      String[] var15 = var9.split(":");
      String[] var16 = var5.split(":");
      String[] var17 = var6.split(":");
      String[] var18 = var7.split(":");
      String[] var19 = var8.split(":");
      ItemStack var20 = new ItemStack(Material.getMaterial(Integer.parseInt(var16[0])));
      ItemStack var21 = new ItemStack(Material.getMaterial(Integer.parseInt(var17[0])));
      ItemStack var22 = new ItemStack(Material.getMaterial(Integer.parseInt(var18[0])));
      ItemStack var23 = new ItemStack(Material.getMaterial(Integer.parseInt(var19[0])));
      ItemStack var24 = new ItemStack(Material.getMaterial(Integer.parseInt(var15[0])));
      if(var15.length >= 2) {
         var24 = new ItemStack(Material.getMaterial(Integer.parseInt(var15[0])), 1, (short)Integer.parseInt(var15[1]));
         if(var15.length == 3) {
            var24.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
         }
      }

      if(var16.length == 2) {
         var20.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
      }

      if(var17.length == 2) {
         var21.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
      }

      if(var18.length == 2) {
         var22.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
      }

      if(var19.length == 2) {
         var23.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
      }

      var14.getEquipment().setHelmet(var20);
      var14.getEquipment().setChestplate(var21);
      var14.getEquipment().setLeggings(var22);
      var14.getEquipment().setBoots(var23);
      var14.getEquipment().setItemInHand(var24);
      String var25 = UUID.randomUUID().toString();
      String var26 = var4.replace(".yml", "");
      MobFormat var27 = new MobFormat();
      var27.menu = var26;
      var27.mobEntity = var14;
      var27.mobLoc = var14.getLocation();
      var27.mobUUID = var14.getUniqueId();
      var27.equipamento = var14.getEquipment();
      if(pl.getServer().getVersion().contains("1.8")) {
         ArmorStand var28 = (ArmorStand)var13.getWorld().spawnEntity(var13, EntityType.ARMOR_STAND);
         var28.setCustomName(var2);
         var28.setCustomNameVisible(var12);
         var28.setVisible(false);
         var28.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000000, 1000));
         var27.hasArmorStand = true;
         var27.mobArmorStand = var28;
         var14.setCustomName("");
         var14.setCustomNameVisible(false);
      }

      PotionEffect var32 = new PotionEffect(PotionEffectType.SLOW, 10000000, 100);
      var14.addPotionEffect(var32);
      var11.set(var25 + ".World", var13.getWorld().getName());
      var11.set(var25 + ".locX", Double.valueOf(var13.getX()));
      var11.set(var25 + ".locY", Double.valueOf(var13.getY()));
      var11.set(var25 + ".locZ", Double.valueOf(var13.getZ()));
      var11.set(var25 + ".locPitch", Float.valueOf(var13.getPitch()));
      var11.set(var25 + ".locYaw", Float.valueOf(var13.getYaw()));
      var11.set(var25 + ".Visible", Boolean.valueOf(var12));
      var11.set(var25 + ".Type", var1);
      var11.set(var25 + ".Name", var2);
      var11.set(var25 + ".Menu", var4);
      var11.set(var25 + ".Armor.Helmet", var5);
      var11.set(var25 + ".Armor.ChestPlate", var6);
      var11.set(var25 + ".Armor.Leggings", var7);
      var11.set(var25 + ".Armor.Boots", var8);
      var11.set(var25 + ".Armor.HandItem", var9);
      var11.set(var25 + ".Mob_UUID", var14.getUniqueId().toString());

      try {
         var11.save(var10);
      } catch (IOException var30) {
         var30.printStackTrace();
      }

      MobsListener.addMob(var14.getUniqueId(), var27);
   }

   public static File conf() {
      File var0 = new File(pl.getDataFolder() + File.separator + "Mobs.yml");
      if(!var0.exists()) {
         try {
            var0.createNewFile();
         } catch (IOException var2) {
            var2.printStackTrace();
         }
      }

      return var0;
   }

   public static FileConfiguration getConf() {
      File var0 = new File(pl.getDataFolder() + File.separator + "Mobs.yml");
      YamlConfiguration var1 = YamlConfiguration.loadConfiguration(var0);
      if(!var0.exists()) {
         try {
            var0.createNewFile();
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      }

      return var1;
   }
}
