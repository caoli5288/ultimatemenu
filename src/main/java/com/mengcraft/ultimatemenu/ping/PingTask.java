package com.mengcraft.ultimatemenu.ping;

import com.mengcraft.ultimatemenu.Main;
import com.mengcraft.ultimatemenu.menu.MenuManager;

public class PingTask implements Runnable {

    private volatile boolean running;

    public void run() {
        if (isNotRunning()) {
            setToRunning();
            process();
            setRunFinish();
        }
    }

    private void process() {
        InfoManager.MANAGER.getServerMap().values().parallelStream().forEach(info -> {
            PingResponse response = PingUtil.ping(info.host, info.port);
            if (response.valid()) {
                info.wrap(response);
            } else {
                info.init();
            }
        });
        Main.runTask(() -> MenuManager.MANAGER.update());
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
