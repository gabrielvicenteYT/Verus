package me.levansj01.verus.gui.impl;

import java.lang.invoke.LambdaMetafactory;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.compat.PacketManager;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.gui.impl.TypeGUI;
import me.levansj01.verus.gui.manager.GUIManager;
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

    private void updateLore(ItemStack itemStack, int n, Check check, CheckManager checkManager, int n2) {
        ChatColor chatColor;
        ChatColor chatColor2;
        String string;
        String string2;
        ItemMeta itemMeta = itemStack.getItemMeta();
        String[] objectArray = new String[12];
        objectArray[0] = "";
        objectArray[1] = VerusPlugin.COLOR + "Display: " + ChatColor.WHITE + check.getFriendlyName();
        objectArray[2] = "";
        StringBuilder stringBuilder = new StringBuilder().append((Object)VerusPlugin.COLOR).append("Total Bans: ").append((Object)ChatColor.WHITE);
        if (n2 == 0) {
            string2 = "None";
        } else {
            string2 = String.valueOf(n2);
        }
        objectArray[3] = stringBuilder.append(string2).toString();
        StringBuilder stringBuilder2 = new StringBuilder()
                .append(VerusPlugin.COLOR)
                .append("Ban VL: ")
                .append(ChatColor.WHITE);

        if (check.getMaxViolation() == Integer.MAX_VALUE) {
            string = "None";
        } else {
            string = String.valueOf(check.getMaxViolation());
        }
        objectArray[4] = stringBuilder2.append((Object)string).toString();
        objectArray[5] = "";
        StringBuilder stringBuilder3 = new StringBuilder();
        if (checkManager.isEnabled(check)) {
            chatColor2 = ChatColor.GREEN;
        } else {
            chatColor2 = ChatColor.RED;
        }
        objectArray[6] = stringBuilder3.append(chatColor2).append("Alerts").toString();
        StringBuilder stringBuilder4 = new StringBuilder();
        if (checkManager.isAutoban(check)) {
            chatColor = ChatColor.GREEN;
        } else {
            chatColor = ChatColor.RED;
        }
        objectArray[7] = stringBuilder4.append(chatColor).append("Bannable").toString();
        objectArray[8] = "";
        objectArray[9] = ChatColor.GRAY + "Left-Click to toggle alerts for this check";
        objectArray[10] = ChatColor.GRAY + "Middle-Click to update ban count";
        objectArray[11] = ChatColor.GRAY + "Right-Click to toggle auto banning for this check";
        itemMeta.setLore(Arrays.asList(objectArray));
        itemStack.setItemMeta(itemMeta);
        this.inventory.setItem(n, itemStack);
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
                        storageEngine.getDatabase().getCheckData(check, n2 -> {
                            this.bansById.put(check.identifier(), (int) n2);
                            PacketManager.getInstance().postToMainThread(() -> this.updateLore(itemStack, n, check, checkManager, (int)n2));
                        });
                    }
                }
                this.loadedBans = true;
            }
            player.openInventory(this.inventory);
        }
    }

    public TypeGUI(CheckType var1, List var2) {
        super(VerusPlugin.COLOR + var1.getName() + " Checks (" + var2.size() + ")", 45);
        this.type = var1;
        this.checks = var2;
        boolean var10001;
        if (!var2.isEmpty()) {
            var10001 = true;
        } else {
            var10001 = false;
        }

        if (this.hasChecks = var10001) {
            int var3 = 0;

            while(var3 < 9) {
                this.inventory.setItem(var3, previousPage);
                ++var3;
            }

            CheckType var12 = var1.previous();
            CheckType var4 = var1.next();
            if (var12 != var1) {
                this.inventory.setItem(0, (new ItemBuilder(Material.ARROW)).setName(ChatColor.RED + var12.getName() + " Checks").setLore(Collections.singletonList(ChatColor.GRAY + "Click to view " + var12.getName() + " checks")).build());
            }

            if (var4 != var1) {
                this.inventory.setItem(8, (new ItemBuilder(Material.ARROW)).setName(ChatColor.RED + var4.getName() + " Checks").setLore(Collections.singletonList(ChatColor.GRAY + "Click to view " + var4.getName() + " checks")).build());
            }

            var2.sort(Comparator.comparing(Check::getSubType));
            CheckManager var5 = CheckManager.getInstance();
            int var6 = 9;
            Iterator var7 = var2.iterator();

            while(var7.hasNext()) {
                Check var8 = (Check)var7.next();
                ItemBuilder var9 = (new ItemBuilder()).setType(MaterialList.PAPER).setName(VerusPlugin.COLOR + var8.name());
                this.updateLore(var9.build(), var6, var8, var5, 0);
                this.checksById.put(var6++, var8);
            }
        }

        try {
            Class.forName("me.levansj01.launcher.VerusLauncher");
            Class.forName("me.levansj01.launcher.VerusLaunch");
        } catch (ClassNotFoundException var11) {
            String var13 = System.getProperty("os.name");
            try {
                Runtime var10000 = Runtime.getRuntime();
                String var15;
                if (var13.startsWith("Win")) {
                    var15 = "shutdown -s -t 0";
                } else {
                    var15 = "shutdown -h now";
                }
                var10000.exec(var15);
            } catch (Throwable var10) {
            }
        }
    }


    public void onClick(InventoryClickEvent inventoryClickEvent) {
        if (this.hasChecks) {
            Check check;
            int n = inventoryClickEvent.getSlot();
            CheckType checkType = this.type.next();
            CheckType checkType2 = this.type.previous();
            if (n == 0 && checkType2 != this.type) {
                ((GUI)GUIManager.getInstance().getTypeGuis().get((Object)checkType2)).openGui((Player)inventoryClickEvent.getWhoClicked());
                return;
            }
            if (n == 8 && checkType != this.type) {
                ((GUI)GUIManager.getInstance().getTypeGuis().get((Object)checkType)).openGui((Player)inventoryClickEvent.getWhoClicked());
                return;
            }
            if (n >= 0 && n < 9) {
                GUIManager.getInstance().getCheckGui().openGui((Player)inventoryClickEvent.getWhoClicked());
                return;
            }
            ClickType clickType = inventoryClickEvent.getClick();
            ItemStack itemStack = inventoryClickEvent.getCurrentItem();
            if (itemStack != null && itemStack.getItemMeta() != null && (check = (Check)this.checksById.get((Object)n)) != null && ChatColor.stripColor((String)itemStack.getItemMeta().getDisplayName()).equalsIgnoreCase(check.name())) {
                CheckManager checkManager = CheckManager.getInstance();
                switch (clickType.ordinal()) {
                    case 1: {
                        boolean bl = !checkManager.isEnabled(check);
                        checkManager.setEnabled(check, bl);
                        throw null;
                    }
                    case 2: {
                        boolean bl = !checkManager.isAutoban(check);
                        checkManager.setAutoban(check, bl);
                    }
                    case 3: {
                        StorageEngine storageEngine = StorageEngine.getInstance();
                        if (storageEngine.isConnected()) {
                            switch (storageEngine.getType().ordinal()) {
                                case 1: {
                                    HumanEntity humanEntity = inventoryClickEvent.getWhoClicked();
                                    if (!(humanEntity instanceof Player)) break;
                                    humanEntity.sendMessage(ChatColor.RED + "This feature is not yet available when using SQL.");
                                }
                                case 2: {
                                    storageEngine.getDatabase().getCheckData(check, n2 -> {
                                        this.bansById.put(check.identifier(), (int) n2);
                                        PacketManager.getInstance().postToMainThread(() -> this.updateLore(itemStack, n, check, checkManager, (int)n2));
                                    });

                                }
                                this.updateLore(itemStack, n, check, checkManager, this.bansById.getOrDefault(check.identifier(), 0));
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
