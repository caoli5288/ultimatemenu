package com.mengcraft.ultimatemenu.listener;

import com.mengcraft.ultimatemenu.Main;
import com.mengcraft.ultimatemenu.task.PlayerMenu;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.Plugin;

public class MobsListener implements Listener {
   private static HashMap mobsByUUid = new HashMap();
   public static Plugin pl;

   static {
      pl = Main.pl;
   }

   @EventHandler
   public void onEntityTarget(EntityTargetEvent var1) {
      if(var1.getEntity() instanceof LivingEntity && mobsByUUid.containsKey(var1.getEntity().getUniqueId())) {
         var1.setCancelled(true);
      }

   }

   @EventHandler
   public void cancelCombust(EntityCombustEvent var1) {
      if(var1.getEntity() != null && var1.getEntity() instanceof LivingEntity && mobsByUUid.containsKey(var1.getEntity().getUniqueId())) {
         var1.setCancelled(true);
      }

   }

   @EventHandler
   public void DamageCancel(EntityDamageEvent var1) {
      if(var1.getEntity() != null && var1.getEntity() instanceof LivingEntity && mobsByUUid.containsKey(var1.getEntity().getUniqueId())) {
         var1.setCancelled(true);
      }

   }

   @EventHandler
   public void DamageListener(EntityDamageByEntityEvent var1) {
      if(var1.getEntity() != null && var1.getEntity() instanceof LivingEntity) {
         UUID var2 = var1.getEntity().getUniqueId();
         if(mobsByUUid.containsKey(var2)) {
            MobFormat var3 = (MobFormat)mobsByUUid.get(var2);
            String var4 = var3.menu;
            var1.setCancelled(true);
            if(var1.getDamager() instanceof Player) {
               Player var5 = (Player)var1.getDamager();
               PlayerMenu.openMenu(var5, var4);
               return;
            }
         }
      }

   }

   @EventHandler
   public void OpMenu(PlayerInteractEntityEvent var1) {
      if(var1.getPlayer() != null && var1.getRightClicked() != null && var1.getRightClicked() instanceof LivingEntity) {
         UUID var2 = var1.getRightClicked().getUniqueId();
         if(mobsByUUid.containsKey(var2)) {
            MobFormat var3 = (MobFormat)mobsByUUid.get(var2);
            String var4 = var3.menu;
            var1.setCancelled(true);
            Player var5 = var1.getPlayer();
            PlayerMenu.openMenu(var5, var4);
            return;
         }
      }

   }

   public static void fixMobs() {
      if(!mobsByUUid.isEmpty()) {
         Iterator var1 = mobsByUUid.values().iterator();

         while(var1.hasNext()) {
            MobFormat var0 = (MobFormat)var1.next();
            Location var2 = var0.mobLoc;
            if(var0.mobEntity.isDead()) {
               MobFormat var3 = new MobFormat();
               var3.hasArmorStand = var0.hasArmorStand;
               var3.menu = var0.menu;
               var3.mobLoc = var0.mobLoc;
               var3.equipamento = var0.equipamento;
               LivingEntity var4 = (LivingEntity)var0.mobArmorStand.getWorld().spawnEntity(var0.mobLoc, var0.mobEntity.getType());
               var3.mobEntity = var4;
               var3.mobUUID = var4.getUniqueId();
               var4.getEquipment().setArmorContents(var3.equipamento.getArmorContents());
               var4.getEquipment().setItemInHand(var3.equipamento.getItemInHand());
               mobsByUUid.remove(var0.mobUUID);
               addMob(var3.mobUUID, var3);
            } else {
               Location var5 = var0.mobEntity.getLocation();
               if(var2 != var5) {
                  var0.mobEntity.teleport(var2);
               }
            }
         }

      }
   }

   public static void offServer() {
      Iterator var1 = mobsByUUid.values().iterator();

      while(var1.hasNext()) {
         MobFormat var0 = (MobFormat)var1.next();
         var0.mobEntity.remove();
         if(var0.hasArmorStand) {
            var0.mobArmorStand.remove();
         }
      }

   }

   public static void addMob(UUID var0, MobFormat var1) {
      if(mobsByUUid.containsKey(var0)) {
         mobsByUUid.remove(var0);
      }

      mobsByUUid.put(var0, var1);
   }

   public static MobFormat getMobProfile(UUID var0) {
      return mobsByUUid.containsKey(var0)?(MobFormat)mobsByUUid.get(var0):null;
   }

   public static void clearInfos() {
      mobsByUUid.clear();
   }
}
