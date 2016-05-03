package com.mengcraft.ultimatemenu.ping;

import org.bukkit.scheduler.BukkitRunnable;

public class PingTask extends BukkitRunnable {

    private volatile boolean running;

    public void run() {
        if (isNotRunning()) {
            setToRunning();
            process();
            setRunFinish();
        }
    }

    private void process() {
        ServerInfo.getServerMap().values().parallelStream().forEach(info -> {
            PingResponse response = PingUtil.ping(info.host, info.port);
            if (response.valid()) {
                info.wrap(response);
            } else {
                info.init();
            }
        });
    }

    private void setRunFinish() {
        running = false;
    }

    private void setToRunning() {
        running = true;
    }

    private boolean isNotRunning() {
        return !running;
    }

}
