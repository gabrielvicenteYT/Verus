package me.levansj01.verus.task;

import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.data.manager.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class ServerTickTask implements Runnable {

    public static ServerTickTask instance;
    private BukkitTask bukkitTask;
    private long lastTransaction;
    private long lastTick;
    private long tick;

    public void run() {
        this.lastTick = this.tick;
        this.tick = System.currentTimeMillis();
        if (this.tick - this.lastTransaction >= 250L) {
            this.lastTransaction = this.tick;
            DataManager.getInstance().getPlayers().forEach(playerData -> playerData.handleTransaction(this.tick));
        }
    }

    public long getLastTransaction() {
        return this.lastTransaction;
    }

    public long getLastTick() {
        return this.lastTick;
    }

    public void schedule() {
        this.bukkitTask = Bukkit.getScheduler().runTaskTimer((Plugin)VerusLauncher.getPlugin(), (Runnable)this, 1L, 1L);
    }

    public static ServerTickTask getInstance() {
        ServerTickTask serverTickTask;
        if (instance == null) {
            serverTickTask = instance = new ServerTickTask();
        } else {
            serverTickTask = instance;
        }
        return serverTickTask;
    }

    public long getTick() {
        return this.tick;
    }

    public boolean isLagging(long l) {
        return l - this.tick > 750L || this.tick - this.lastTick > 750L;
    }

    public BukkitTask getBukkitTask() {
        return this.bukkitTask;
    }
}
