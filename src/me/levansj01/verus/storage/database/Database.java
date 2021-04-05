package me.levansj01.verus.storage.database;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.storage.database.Ban;
import me.levansj01.verus.storage.database.Log;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public abstract class Database {
    protected final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor((ThreadFactory)new ThreadFactoryBuilder().setPriority(3).setNameFormat(String.valueOf((Object)new StringBuilder().append(this.getClass().getSimpleName()).append(" Executor Thread"))).build());

    public void getLogs(UUID uUID, Consumer consumer) {
        this.getLogs(uUID, 3000, consumer);
    }

    public abstract void removeData(PlayerData var1);

    private void getUUID2(String string, Consumer consumer) {
        Player player = Bukkit.getPlayerExact((String)string);
        if (player != null) {
            consumer.accept((Object)player.getUniqueId());
            return;
        }
        UUID uUID = this.fetchUUID(string);
        if (uUID == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer((String)string);
            if (offlinePlayer != null && offlinePlayer.hasPlayedBefore()) {
                consumer.accept((Object)offlinePlayer.getUniqueId());

            } else {
                consumer.accept(null);
            }

        } else {
            consumer.accept((Object)uUID);
        }
    }

    public void getBans(UUID uUID, Consumer consumer) {
        this.getBans(uUID, 10, consumer);
    }

    public abstract void getLogs(UUID var1, int var2, Consumer var3);

    public void start() {
        if (StorageEngine.getInstance().getVerusConfig().isCleanup()) {
            this.executorService.scheduleAtFixedRate(this::cleanup, 5L, 60L, TimeUnit.MINUTES);
        }
    }

    public abstract void updateData(PlayerData var1);

    public abstract boolean isConnected();

    protected long getPersistenceTime() {
        VerusConfiguration verusConfiguration = StorageEngine.getInstance().getVerusConfig();
        return System.currentTimeMillis() - verusConfiguration.getPersistenceMillis();
    }

    public abstract void getCheckData(String var1, String var2, Consumer var3);

    protected boolean shouldPersist(long l) {
        boolean bl;
        VerusConfiguration verusConfiguration = StorageEngine.getInstance().getVerusConfig();
        if (!verusConfiguration.isPersistence()) {
            return false;
        }
        if (System.currentTimeMillis() - l < verusConfiguration.getPersistenceMillis()) {
            bl = true;
        } else {
            bl = false;
        }
        return bl;
    }

    public abstract void getBans(UUID var1, int var2, Consumer var3);

    public abstract int getTotalBans();

    public abstract void connect(VerusConfiguration var1);

    public void getUUID(String string, Consumer consumer) {
        this.execute(() -> this.getUUID2(string, consumer));
    }

    public abstract void stop();

    public abstract void insertLog(Log var1);

    protected abstract UUID fetchUUID(String var1);

    public abstract void updateAlerts(PlayerData var1);

    public abstract void insertBan(Ban var1);

    public abstract void loadAlerts(PlayerData var1);

    public abstract int getTotalLogs();

    public abstract void cleanup();

    public abstract void loadData(PlayerData var1);

    public void execute(Runnable runnable, long l) {
        this.executorService.schedule(runnable, l, TimeUnit.MILLISECONDS);
    }

    public void execute(Runnable runnable) {
        this.executorService.execute(runnable);
    }

    public void getCheckData(Check check, Consumer consumer) {
        this.getCheckData(check.getType().getName(), check.getSubType(), consumer);
    }
}
