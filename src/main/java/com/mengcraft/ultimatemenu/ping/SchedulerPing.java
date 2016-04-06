package com.mengcraft.ultimatemenu.ping;

import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.scheduler.BukkitRunnable;

public class SchedulerPing extends BukkitRunnable {
   public void run() {
      HashMap var1 = new HashMap(ServersInfo.getServersToPing());
      String var2;
      Info var5;
      if(var1 != null) {
         for(Iterator var3 = var1.keySet().iterator(); var3.hasNext(); ServersInfo.finishServer(var2, var5)) {
            var2 = (String)var3.next();
            Long var4 = Long.valueOf(System.currentTimeMillis());
            var5 = (Info)var1.get(var2);
            String var6 = "";
            boolean var7 = true;

            try {
               var6 = PingServer.getMOTD(var5.ip, var5.port);
            } catch (Exception var9) {
               var7 = false;
               var5.max = 0;
               var5.motd = "";
               var5.offLine = true;
               var5.online = 0;
            }

            Long var8 = Long.valueOf(System.currentTimeMillis() - var4.longValue());
            if(var6.equalsIgnoreCase("OFFLINE")) {
               var7 = false;
               var5.max = -1;
               var5.motd = null;
               var5.offLine = true;
               var5.online = -1;
               var5.ms = -1.0D;
            }

            if(var7) {
               var5.max = Integer.parseInt(var6.split(";")[2]);
               var5.online = Integer.parseInt(var6.split(";")[1]);
               var5.motd = var6.split(";")[0].replaceAll("&", "§");
               var5.ms = (double)var8.longValue();
            }
         }
      }

   }
}
