package me.levansj01.verus.util.item;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class ItemBuilder {
    private String name;
    private int amount = 1;
    private Material type;
    private List<String> lore;
    private short damage;

    public short getDamage() {
        return this.damage;
    }

    public ItemBuilder() {
    }

    public ItemBuilder setLore(List<String> list) {
        this.lore = list;
        return this;
    }

    public ItemBuilder setName(String string) {
        this.name = string;
        return this;
    }

    public Material getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public ItemBuilder setAmount(int n) {
        this.amount = n;
        return this;
    }

    public ItemBuilder setType(Material material) {
        this.type = material;
        return this;
    }

    public int getAmount() {
        return this.amount;
    }

    public ItemBuilder setDamage(int n) {
        this.damage = (short)n;
        return this;
    }

    public ItemBuilder setTypeAndData(MaterialData materialData) {
        this.type = materialData.getItemType();
        this.damage = materialData.getData();
        return this;
    }

    public ItemBuilder(Material material) {
        this.type = material;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(this.type);
        itemStack.setAmount(this.amount);
        itemStack.setDurability(this.damage);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(this.name);
        itemMeta.setLore(this.lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

