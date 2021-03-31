package me.levansj01.verus.listener;

import java.util.stream.Collectors;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.MovementCheck2;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.listener.DataListener;
import me.levansj01.verus.type.Loader;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.java.JavaV;
import me.levansj01.verus.verus2.data.player.TickerType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class DataListener implements Listener {

    private static final String DEVELOPER_MESSAGE = String.format(
            JavaV.stream(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH
                    + "------------------------------------------",
                    ChatColor.WHITE + "This server is running %s (%s)", ChatColor.GRAY +
                    ChatColor.STRIKETHROUGH.toString() + "------------------------------------------"
            ).collect(Collectors.joining("\n")) + VerusPlugin.COLOR + VerusPlugin.getNameFormatted() + " Anticheat" + ChatColor.GRAY, Loader.getUsername());

    private static final DataManager MANAGER = DataManager.getInstance();

    public void sendDeveloperMessage(Player player) {
        player.sendMessage(DEVELOPER_MESSAGE);
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        MANAGER.getExecutorService().submit(() -> MANAGER.inject(player));
        if (BukkitUtil.isDev((Player)player)) {
            this.sendDeveloperMessage(player);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        MANAGER.getExecutorService().submit(() -> MANAGER.uninject(player));
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
    public void onDamage(EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent.getEntity() instanceof Player) {
            Player player = (Player)entityDamageEvent.getEntity();
            EntityDamageEvent.DamageCause damageCause = entityDamageEvent.getCause();
            switch (damageCause.ordinal()) {
                case 1: {
                    PlayerData playerData = MANAGER.getPlayer(player);
                    if (playerData == null) break;
                    playerData.getTickerMap().reset(TickerType.SUFFOCATING);
                    break;
                }
                case 2: {
                    PlayerData playerData = MANAGER.getPlayer(player);
                    if (playerData == null) break;
                    playerData.setFallDamage(NMSManager.getInstance().getCurrentTick());
                }
            }
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
    public void onVelocityEvent(PlayerVelocityEvent playerVelocityEvent) {
        PlayerData playerData = MANAGER.getPlayer(playerVelocityEvent.getPlayer());
        if (playerData != null && playerData.getFallDamage() == NMSManager.getInstance().getCurrentTick()) {
            playerData.setFallDamage(0);
            if (playerVelocityEvent.getVelocity().getY() <= 0.08) {
                playerVelocityEvent.setCancelled(true);
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent playerDeathEvent) {
        PlayerData playerData = MANAGER.getPlayer(playerDeathEvent.getEntity());
        if (playerData != null) {
            playerData.reset();
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onTeleport(PlayerTeleportEvent playerTeleportEvent) {
        Player player = playerTeleportEvent.getPlayer();
        if (playerTeleportEvent.getCause() == PlayerTeleportEvent.TeleportCause.UNKNOWN) {
            return;
        }
        PlayerData playerData = MANAGER.getPlayer(player);
        if (playerData != null && !playerTeleportEvent.isCancelled()) {
            for (MovementCheck2 movementCheck2 : playerData.getCheckData().getMovementChecks2()) {
                movementCheck2.handleTeleport();
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onFish(PlayerFishEvent playerFishEvent) {
        Player player;
        PlayerData playerData;
        if (playerFishEvent.getCaught() instanceof Player && (playerData = MANAGER.getPlayer(player = (Player)playerFishEvent.getCaught())) != null) {
            playerData.setHookTicks(0);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        Inventory inventory = inventoryClickEvent.getClickedInventory() != null ? inventoryClickEvent.getClickedInventory() : inventoryClickEvent.getInventory();
        if (inventory != null && inventory.getHolder() instanceof GUI) {
            GUI gUI = (GUI)inventory.getHolder();
            gUI.onClick(inventoryClickEvent);
            inventoryClickEvent.setCancelled(true);
        } else {
            Inventory inventory3;
            InventoryView inventoryView = inventoryClickEvent.getView();
            if (inventoryView != null && (inventory3 = inventoryView.getTopInventory()) != null && inventory3.getHolder() instanceof GUI) {
                inventoryClickEvent.setCancelled(true);
            }
        }
    }
}
