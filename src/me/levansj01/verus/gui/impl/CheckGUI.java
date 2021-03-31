package me.levansj01.verus.gui.impl;

import java.util.Arrays;
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
    private Map<Integer, CheckType> checksById = new ConcurrentHashMap<Integer, CheckType>();

    public void clear() {
        super.clear();
        this.checksById = null;
    }

    public void onClick(InventoryClickEvent inventoryClickEvent) {
        String string;
        CheckType checkType;
        int n = inventoryClickEvent.getSlot();
        if (n >= 0 && n < 9) {
            GUIManager.getInstance().getMainGui().openGui((Player) inventoryClickEvent.getWhoClicked());
            return;
        }
        ItemStack itemStack = inventoryClickEvent.getCurrentItem();
        if (itemStack != null && itemStack.getItemMeta() != null
                && (checkType = (CheckType) this.checksById.get((Object) n)) != null
                && (string = ChatColor.stripColor((String) itemStack.getItemMeta().getDisplayName()
                        .replace((CharSequence) " Checks", (CharSequence) ""))).equalsIgnoreCase(checkType.getName())) {
            GUIManager.getInstance().getTypeGui(checkType).openGui((Player) inventoryClickEvent.getWhoClicked());
        }
    }

    public CheckGUI() {
        super(VerusPlugin.COLOR + VerusPlugin.getNameFormatted() + " Checks", Integer.valueOf((int) 45));
        int n = 0;
        if (n < 9) {
            this.inventory.setItem(n, new ItemBuilder().setType(MaterialList.STAINED_GLASS_PANE)
                    .setName(ChatColor.RED + "Previous Page").setAmount(1)
                    .setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Click to go back a page" })).build());
            ++n;
        }
        n = 9;
        for (CheckType checkType : CheckType.values()) {
            if (checkType == CheckType.MANUAL) {

            }
            this.inventory.setItem(n, new ItemBuilder().setType(MaterialList.DIAMOND_SWORD)
                    .setName(VerusPlugin.COLOR + checkType.getName() + " Checks")
                    .setLore(Arrays.asList(
                            new String[] { VerusPlugin.COLOR + "ID: " + ChatColor.WHITE + checkType.getSuffix() }))
                    .setAmount(1).build());
            this.checksById.put(n, checkType);
            ++n;
        }
    }
}
