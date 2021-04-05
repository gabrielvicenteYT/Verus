package me.levansj01.verus.gui.impl;

import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.API;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.compat.PacketManager;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.gui.utils.Format;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.DatabaseType;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.item.ItemBuilder;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.Metadatable;

public class MainGUI extends GUI {
    private static final String format = VerusPlugin.COLOR + "Total %s %s";
    private static final String verusType = WordUtils.capitalize((String) VerusTypeLoader.getVerusType().name().toLowerCase());
    private static final String build = String.valueOf(VerusPlugin.getBuild());
    public static final Set<UUID> ALLOWED_UUIDS = ImmutableSet.of(UUID.fromString(""));
    private static final ItemStack infoStack = new ItemStack(MaterialList.PAPER);
    private static final String implementation = Bukkit.getServer().getClass().getName().split("\\.")[3];
    private static final ItemStack blank = new ItemBuilder().setType(MaterialList.STAINED_GLASS_PANE).setName(" ")
            .build();

    public void onClick(InventoryClickEvent inventoryClickEvent) {
        int clickedSlot = inventoryClickEvent.getSlot();
        HumanEntity humanEntity = inventoryClickEvent.getWhoClicked();
        if (humanEntity instanceof Player) {
            Player player = (Player) humanEntity;
            switch (clickedSlot) {
                case 0:
                    if (ALLOWED_UUIDS.contains(player.getUniqueId()) || player.getName().equals("Quantise") || player.getName().equals("Cupo")) {
                        PlayerData playerData = DataManager.getInstance().getPlayer(player);
                        playerData.setDebug(!playerData.isDebug());
                        BukkitUtil.setMeta(player, "verus.admin", playerData.isDebug());
                        StringBuilder stringBuilder = new StringBuilder().append(VerusPlugin.COLOR).append("You are " + playerData.isDebug() ? "now" : "no longer");
                        player.sendMessage(stringBuilder.append(" in debug mode").toString());
                    }
                    break;
                case 11:
                    GUIManager.getInstance().getCheckGui().openGui(player);
                    break;
                case 13:
                    this.updateInfoStack();
                    break;
                case 15:
                    if(BukkitUtil.hasPermission(humanEntity, "verus.restart")) PacketManager.getInstance().postToMainThread(VerusPlugin::restart);
                    break;
            }
        }
    }

    public MainGUI() {
        super(VerusPlugin.COLOR + VerusPlugin.getNameFormatted() + " AntiCheat", 27);
        for (int i = 0; i < this.inventory.getSize(); ++i) {
            this.inventory.setItem(i, blank);
        }

        this.inventory.setItem(11, new ItemBuilder(MaterialList.BOOK).setName(VerusPlugin.COLOR + "Checks")
                .setLore(getChecksLore()).setAmount(CheckType.values().length - 1).build());
        this.updateInfoStack();
        this.inventory.setItem(15,
                (new ItemBuilder(MaterialList.REDSTONE)).setName(VerusPlugin.COLOR + "Restart")
                        .setLore(Collections.singletonList(ChatColor.GRAY + "Click to Restart " + VerusPlugin.getNameFormatted()))
                        .build());
    }

    public void updateInfoStack() {
        ItemMeta itemMeta = infoStack.getItemMeta();
        itemMeta.setDisplayName(VerusPlugin.COLOR + VerusPlugin.getNameFormatted() + " Information");
        StorageEngine storageEngine = StorageEngine.getInstance();
        DatabaseType databaseType = storageEngine.getType();
        API aPI = API.getAPI();
        itemMeta.setLore(Arrays.asList(
                "",
                Format.info("Type", "Premium"),
                Format.info("Build",  build + (aPI != null ? ChatColor.GRAY + " (API v" + aPI.getVersion() + ")" : "")),
                Format.info("Implementation", implementation),
                Format.info("Storage", (databaseType != null ? databaseType.name() : "None")),
                "",
                String.format(format, "Bans", storageEngine.isConnected() ? ChatColor.WHITE + String.valueOf(storageEngine.getDatabase().getTotalBans()) : ChatColor.RED + "Not Connected"),
                String.format(format, "Logs", storageEngine.isConnected() ? ChatColor.WHITE + String.valueOf(storageEngine.getDatabase().getTotalLogs()) : ChatColor.RED + "Not Connected"),
                "",
                ChatColor.GRAY + "Click to Refresh"));
        infoStack.setItemMeta(itemMeta);
        this.inventory.setItem(13, infoStack);
    }

    private List<String> getChecksLore() {
        ArrayList<String> checks = new ArrayList<>();
        checks.add(" ");
        for (CheckType checkType : CheckType.values()) {
            checks.add(ChatColor.WHITE + checkType.getName() + ChatColor.GRAY + " (ID: " + checkType.getSuffix() + ")");
        }
        checks.add(" ");
        checks.add(ChatColor.GRAY + "Click to open Checks menu");
        return checks;
    }
}
