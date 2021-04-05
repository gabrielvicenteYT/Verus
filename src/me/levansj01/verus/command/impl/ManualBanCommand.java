package me.levansj01.verus.command.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.type.Loader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class ManualBanCommand extends BaseCommand {

    public ManualBanCommand() {
        super(Loader.getManualbanCommand());
        this.setDatabase(true);
        this.setPermission("verus.admin");
        this.setUsageMessage(ChatColor.RED + "Usage: /" + this.getName() + " <name>");
    }
    
    public void execute(CommandSender commandSender, String[] stringArray) {
        if (stringArray.length == 0) {
            commandSender.sendMessage(this.getUsageMessage());
            return;
        }
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Player player2 = Bukkit.getPlayer((String) stringArray[0]);
            if (player2 != null) {
                PlayerData playerData = DataManager.getInstance().getPlayer(player2);
                AlertManager alertManager = AlertManager.getInstance();
                alertManager.getExecutorService()
                        .submit(() -> ManualBanCommand.insertBan(alertManager, playerData, player));

            } else {
                player.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NOT_FOUND.get());
            }
        }
    }

    private static void insertBan(AlertManager alertManager, PlayerData playerData, Player player) {
        alertManager.insertBan(playerData, new Check(CheckType.MANUAL, "-" + player.getName(), "Manual",
                CheckVersion.RELEASE, new ClientVersion[0]));
    }
    
    public List<String> tabComplete(CommandSender commandSender, String string, String[] stringArray)
            throws IllegalArgumentException {
        Player player;
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        } else {
            player = null;
        }
        Player player2 = player;
        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator<? extends Player> iterator = commandSender.getServer().getOnlinePlayers().iterator();
        while (true) {
            String string2;
            if (!iterator.hasNext()) {
                arrayList.sort(String.CASE_INSENSITIVE_ORDER);
                return arrayList;
            }
            Player player3 = (Player) iterator.next();
            String string3 = player3.getName();
            if (player2 != null && !player2.canSee(player3))
                continue;
            if (stringArray.length == 1
                    && StringUtil.startsWithIgnoreCase(string3, string2 = stringArray[stringArray.length - 1])) {
                arrayList.add(string3);
            }
        }

    }
}
