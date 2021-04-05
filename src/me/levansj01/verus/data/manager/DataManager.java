package me.levansj01.verus.data.manager;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.API;
import me.levansj01.verus.compat.PacketManager;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.storage.StorageEngine;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DataManager {
    private static Boolean lunarClientAPI;
    private final ExecutorService executorService;
    private final Map<UUID, PlayerData> players = new ConcurrentHashMap<UUID, PlayerData>();
    public final VerusPlugin plugin;
    private static DataManager instance;
    public void inject(Player player) {
        if (player != null && !player.hasMetadata("fake")) {
            PlayerData playerData = new PlayerData(player, this.plugin.getTypeLoader().loadChecks());
            API aPI = API.getAPI();
            if (aPI != null && aPI.fireInitEvent(playerData)) {
                playerData.setEnabled(false);
            }
            this.players.put(player.getUniqueId(), playerData);
            PacketManager.getInstance().inject(playerData);
            if (StorageEngine.getInstance().isConnected()) {
                playerData.loadData();
            }
        }
    }

    public VerusPlugin getPlugin() {
        return this.plugin;
    }

    public static boolean isLunarClientAPI() {
        if (lunarClientAPI == null) {
            lunarClientAPI = Bukkit.getServer().getPluginManager().isPluginEnabled("LunarClient-API");
        }
        return lunarClientAPI;
    }

    public PlayerData getPlayer(Player player) {
        return this.getPlayer(player.getUniqueId());
    }

    public PlayerData getPlayer(UUID uUID) {
        return (PlayerData)this.players.get((Object)uUID);
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public void uninject(Player player) {
        PlayerData playerData = (PlayerData)this.players.remove((Object)player.getUniqueId());
        if (playerData != null) {
            playerData.saveData();
            playerData.setEnabled(false);
            PacketManager.getInstance().uninject(playerData);
        }
    }

    public static DataManager getInstance() {
        return instance;
    }

    public DataManager(VerusPlugin verusPlugin) {
        this.executorService = Executors.newCachedThreadPool();
        this.plugin = verusPlugin;
    }

    public static void enable(VerusPlugin verusPlugin) {
        instance = new DataManager(verusPlugin);
        instance.getExecutorService().submit(() -> Bukkit.getOnlinePlayers().forEach(instance::inject));
    }

    public Collection<PlayerData> getPlayers() {
        return this.players.values();
    }

    public static void disable() {
        if (instance != null) {
            DataManager.instance.executorService.shutdown();
        }
        Thread thread = new Thread(() -> Bukkit.getOnlinePlayers().forEach(instance::uninject));
        thread.start();
        try {
            thread.join(TimeUnit.SECONDS.toMillis(15L));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        instance = null;
    }
}
