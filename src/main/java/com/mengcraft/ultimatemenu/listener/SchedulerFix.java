package com.mengcraft.ultimatemenu.listener;

import org.bukkit.scheduler.BukkitRunnable;

public class SchedulerFix extends BukkitRunnable {
   public void run() {
      MobsListener.fixMobs();
   }
}
