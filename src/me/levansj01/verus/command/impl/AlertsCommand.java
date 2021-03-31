package me.levansj01.verus.command.impl;

import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.type.Loader;
import me.levansj01.verus.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.Metadatable;

public class AlertsCommand extends BaseCommand {

    public void execute(CommandSender commandSender, String[] stringArray) {
        if (BukkitUtil.hasPermission((CommandSender) commandSender, (String) "verus.staff")
                || BukkitUtil.hasPermission((CommandSender) commandSender, (String) "verus.staff.alerts")
                || BukkitUtil.hasPermission((CommandSender) commandSender, (String) "verus.alerts")) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                PlayerData playerData = DataManager.getInstance().getPlayer(player);
                if (playerData != null) {
                    EnumMessage enumMessage;
                    boolean bl;
                    if (BukkitUtil.isDev((Player) player) && stringArray.length == 1
                            && stringArray[0].toLowerCase().equalsIgnoreCase("debug")) {
                        String string;
                        boolean bl2;
                        if (!playerData.isDebug()) {
                            bl2 = true;
                        } else {
                            bl2 = false;
                        }
                        playerData.setDebug(bl2);
                        this.update(playerData);
                        BukkitUtil.setMeta((Metadatable) player, (String) "verus.admin",
                                (boolean) playerData.isDebug());
                        StringBuilder stringBuilder = new StringBuilder().append((Object) VerusPlugin.COLOR)
                                .append("You are ");
                        if (playerData.isDebug()) {
                            string = "now";
                        } else {
                            string = "no longer";
                        }
                        commandSender.sendMessage(stringBuilder.append(string).append(" in debug mode").toString());
                        return;
                    }
                    if (!playerData.isAlerts()) {
                        bl = true;
                    } else {
                        bl = false;
                    }
                    playerData.setAlerts(bl);
                    this.update(playerData);
                    BukkitUtil.setMeta((Metadatable) player, (String) "verus.alerts", (boolean) playerData.isAlerts());
                    if (playerData.isAlerts()) {
                        enumMessage = EnumMessage.ALERTS_ENABLED_COMMAND;

                    } else {
                        enumMessage = EnumMessage.ALERTS_DISABLED_COMMAND;
                    }
                    EnumMessage enumMessage2 = enumMessage;
                    commandSender.sendMessage(VerusPlugin.COLOR + enumMessage2.get());
                }

            }
        } else {
            commandSender.sendMessage(ChatColor.RED + this.getPermissionMessage());
        }
    }

    private void update(PlayerData playerData) {
        StorageEngine storageEngine = StorageEngine.getInstance();
        if (storageEngine.isConnected()) {
            storageEngine.getDatabase().updateAlerts(playerData);
        }
    }

    public AlertsCommand() {
        super(Loader.getAlertsCommand());
    }
}
