package me.levansj01.verus.gui.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.util.item.ItemBuilder;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CheckGUI extends GUI {
    private Map<Integer, CheckType> checksById = new ConcurrentHashMap<>();

    public void clear() {
        super.clear();
        this.checksById = null;
    }

    public void onClick(InventoryClickEvent inventoryClickEvent) {
        int slot = inventoryClickEvent.getSlot();
        if (slot >= 0 && slot < 9) {
            GUIManager.getInstance().getMainGui().openGui((Player) inventoryClickEvent.getWhoClicked());
            return;
        }
        CheckType checkType = this.checksById.get(slot);
        ItemStack itemStack = inventoryClickEvent.getCurrentItem();
        if (itemStack != null && itemStack.getItemMeta() != null
                && checkType != null
                && (ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()
                        .replace(" Checks", ""))).equalsIgnoreCase(checkType.getName())) {
            GUIManager.getInstance().getTypeGui(checkType).openGui((Player) inventoryClickEvent.getWhoClicked());
        }
    }

    public CheckGUI() {
        super(VerusPlugin.COLOR + VerusPlugin.getNameFormatted() + " Checks", 45);
        int slot = 0;

        while(slot < 9) {
            this.inventory.setItem(0, new ItemBuilder().setType(MaterialList.STAINED_GLASS_PANE)
                    .setName(ChatColor.RED + "Previous Page").setAmount(1)
                    .setLore(Collections.singletonList(ChatColor.GRAY + "Click to go back a page")).build());
            ++slot;
        }

        for (CheckType checkType : CheckType.values()) {
            this.inventory.setItem(slot, new ItemBuilder().setType(MaterialList.DIAMOND_SWORD)
                    .setName(VerusPlugin.COLOR + checkType.getName() + " Checks")
                    .setLore(Collections.singletonList(
                            VerusPlugin.COLOR + "ID: " + ChatColor.WHITE + checkType.getSuffix()))
                    .setAmount(1).build());
            this.checksById.put(slot, checkType);
            ++slot;
        }
    }
}
