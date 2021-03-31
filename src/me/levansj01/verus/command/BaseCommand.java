package me.levansj01.verus.command;

import java.util.List;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.BukkitUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand
extends Command {
    private boolean database;
    private String usageMessage;

    public void setUsageMessage(String string) {
        this.usageMessage = string;
    }

    public String getUsageMessage() {
        return this.usageMessage;
    }

    public abstract void execute(CommandSender var1, String[] var2);

    public BaseCommand(String string, String string2, String string3, List<String> list) {
        super(string, string2, string3, list);
    }

    public void setDatabase(boolean bl) {
        this.database = bl;
    }

    public BaseCommand(String string) {
        super(string);
    }

    public boolean execute(CommandSender commandSender, String string, String[] stringArray) {
        if (this.getPermission() != null && !BukkitUtil.hasPermission((CommandSender)commandSender, (String)this.getPermission())) {
            commandSender.sendMessage(ChatColor.RED + this.getPermissionMessage());
            return false;
        }
        if (this.database && !StorageEngine.getInstance().isConnected()) {
            commandSender.sendMessage(ChatColor.RED + "Please connect to a database to use this command.");
            return false;
        }
        this.execute(commandSender, stringArray);
        return false;
    }
}
