package me.levansj01.verus.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class GUI implements InventoryHolder {
    public Inventory inventory;
    private final String header;
    private final Integer size;

    public void clear() {
        this.inventory.getViewers().stream().forEach(HumanEntity::closeInventory);
    }

    public GUI(String string, Integer n) {
        this.inventory = Bukkit.createInventory((InventoryHolder) this, (int) n, (String) string);
        this.header = string;
        this.size = n;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Integer getSize() {
        return this.size;
    }

    public void openGui(Player player) {
        if (this.inventory != null) {
            player.openInventory(this.inventory);
        }
    }

    public abstract void onClick(InventoryClickEvent var1);

    public String getHeader() {
        return this.header;
    }
}
