package me.levansj01.verus.gui.impl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.compat.PacketManager;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.gui.utils.Format;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.item.ItemBuilder;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TypeGUI extends GUI {
    private static final ItemStack previousPage;
    private Map<String, Integer> bansById;
    private Map<Integer, Check> checksById = new ConcurrentHashMap();
    private final List<Check> checks;
    private static final String errorMessage;
    private boolean loadedBans;
    private final boolean hasChecks;
    private final CheckType type;

    static {
        errorMessage = ChatColor.RED + "You do not have access to these checks.";
        previousPage = new ItemBuilder().setType(MaterialList.STAINED_GLASS_PANE).setName(ChatColor.RED + "Previous Page").setLore(Collections.singletonList((ChatColor.GRAY + "Click to go back a page"))).build();
    }

    private void updateLore(ItemStack itemStack, int slot, Check check, CheckManager cManager, int totalBans) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Arrays.asList(
                "",
                Format.info("Display", check.getFriendlyName()),
                "",
                Format.info("Total Bans", totalBans == 0 ? "None" : String.valueOf(totalBans)),
                Format.info("Ban VL", check.getMaxViolation() == Integer.MAX_VALUE ? "None" : String.valueOf(check.getMaxViolation())),
                "",
                Format.toggle(cManager.isEnabled(check), "Alerts"),
                Format.toggle(cManager.isAutoban(check), "Bannable"),
                "",
                Format.description("Left-Click to toggle alerts for this check"),
                Format.description("Middle-Click to update ban count"),
                Format.description("Right-Click to toggle auto banning for this check")
        ));
        itemStack.setItemMeta(itemMeta);
        this.inventory.setItem(slot, itemStack);
    }

    public void openGui(Player player) {
        if (!this.hasChecks) {
            player.sendMessage(errorMessage);
            return;
        }
        if (this.inventory != null) {
            StorageEngine storageEngine;
            if (!this.loadedBans && (storageEngine = StorageEngine.getInstance()).isConnected()) {
                CheckManager checkManager = CheckManager.getInstance();
                AtomicInteger atomicInteger = new AtomicInteger(9);
                for (Check check : this.checks) {
                    int n = atomicInteger.getAndIncrement();
                    ItemStack itemStack = this.inventory.getContents()[n];
                    if (itemStack != null) {
                        storageEngine.getDatabase().getCheckData(check, bans -> {
                            this.bansById.put(check.identifier(), (int)bans);
                            PacketManager.getInstance().postToMainThread(() -> this.updateLore(itemStack, n, check, checkManager, (int)bans));
                        });
                    }
                }
                this.loadedBans = true;
            }
            player.openInventory(this.inventory);
        }
    }

    public TypeGUI(CheckType type, List<Check> checks) {
        super(VerusPlugin.COLOR + type.getName() + " Checks (" + checks.size() + ")", 45);
        this.type = type;
        this.checks = checks;

        if (this.hasChecks = !checks.isEmpty()) {
            for(int slot = 0; slot < 9; slot++) {
                this.inventory.setItem(slot, previousPage);
            }
            CheckType previous = type.previous();
            CheckType next = type.next();
            if (previous != type) {
                this.inventory.setItem(0, (new ItemBuilder(Material.ARROW)).setName(ChatColor.RED + previous.getName() + " Checks").setLore(Collections.singletonList(ChatColor.GRAY + "Click to view " + previous.getName() + " checks")).build());
            }

            if (next != type) {
                this.inventory.setItem(8, (new ItemBuilder(Material.ARROW)).setName(ChatColor.RED + next.getName() + " Checks").setLore(Collections.singletonList(ChatColor.GRAY + "Click to view " + next.getName() + " checks")).build());
            }

            checks.sort(Comparator.comparing(Check::getSubType));
            CheckManager checkManager = CheckManager.getInstance();
            int var6 = 9;
            Iterator var7 = checks.iterator();

            while(var7.hasNext()) {
                Check var8 = (Check)var7.next();
                ItemBuilder var9 = (new ItemBuilder()).setType(MaterialList.PAPER).setName(VerusPlugin.COLOR + var8.name());
                this.updateLore(var9.build(), var6, var8, checkManager, 0);
                this.checksById.put(var6++, var8);
            }
        }
    }


    public void onClick(InventoryClickEvent inventoryClickEvent) {
        if (this.hasChecks) {
            Check check;
            int getSlot = inventoryClickEvent.getSlot();
            CheckType checkType = this.type.next();
            CheckType checkType2 = this.type.previous();
            if (getSlot == 0 && checkType2 != this.type) {
                GUIManager.getInstance().getTypeGuis().get(checkType2).openGui((Player)inventoryClickEvent.getWhoClicked());
                return;
            }
            if (getSlot == 8 && checkType != this.type) {
                GUIManager.getInstance().getTypeGuis().get(checkType).openGui((Player)inventoryClickEvent.getWhoClicked());
                return;
            }
            if (getSlot >= 0 && getSlot < 9) {
                GUIManager.getInstance().getCheckGui().openGui((Player)inventoryClickEvent.getWhoClicked());
                return;
            }
            ClickType clickType = inventoryClickEvent.getClick();
            ItemStack currentItem = inventoryClickEvent.getCurrentItem();
            if (currentItem != null && currentItem.getItemMeta() != null && (check = (Check)this.checksById.get(getSlot)) != null && ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()).equalsIgnoreCase(check.name())) {
                CheckManager checkManager = CheckManager.getInstance();
                switch (clickType.ordinal()) {
                    case 1: {
                        boolean bl = !checkManager.isEnabled(check);
                        checkManager.setEnabled(check, bl);
                       //was a random nullpointer that was thrown here
                    }
                    case 2: {
                        checkManager.setAutoban(check, !checkManager.isAutoban(check));
                    }
                    case 3: {
                        StorageEngine storageEngine = StorageEngine.getInstance();
                        if (storageEngine.isConnected()) {
                            switch (storageEngine.getType().ordinal()) {
                                case 1: {
                                    HumanEntity clickedEntity = inventoryClickEvent.getWhoClicked();
                                    if (!(clickedEntity instanceof Player)) break;
                                    clickedEntity.sendMessage(ChatColor.RED + "This feature is not yet available when using SQL.");
                                }
                                case 2: {
                                    storageEngine.getDatabase().getCheckData(check, bans -> {
                                        this.bansById.put(check.identifier(), (int) bans);
                                        PacketManager.getInstance().postToMainThread(() -> this.updateLore(currentItem, getSlot, check, checkManager, (int)bans));
                                    });

                                }
                                this.updateLore(currentItem, getSlot, check, checkManager, this.bansById.getOrDefault(check.identifier(), 0));
                            }
                        }
                    }
                    default: {
                        return;
                    }
                }
            }
        }
    }

    public void clear() {
        super.clear();
        this.checks.clear();
        this.checksById = null;
        this.bansById = null;
    }
}
