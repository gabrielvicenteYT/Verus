package me.levansj01.verus.command.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.type.Loader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class PingCommand
extends BaseCommand {

    public PingCommand() {
        super(Loader.getPingCommand());
    }

    public void execute(CommandSender commandSender, String[] stringArray) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            if (stringArray.length == 1) {
                Player player2 = Bukkit.getPlayer((String)stringArray[0]);
                if (player2 != null) {
                    PlayerData playerData = DataManager.getInstance().getPlayer(player2);
                    if (playerData != null) {
                        player.sendMessage(VerusPlugin.COLOR + player2.getName() + "'s ping is " + ChatColor.GRAY + playerData.getAveragePing() + "ms (" + playerData.getAverageTransactionPing() + "ms)");
                    }

                } else {
                    player.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NOT_FOUND.get());
                }

            } else {
                PlayerData playerData = DataManager.getInstance().getPlayer(player);
                if (playerData != null) {
                    player.sendMessage(VerusPlugin.COLOR + "Your ping is " + ChatColor.GRAY + playerData.getAveragePing() + "ms (" + playerData.getAverageTransactionPing() + "ms)");
                }
            }
        }
    }

    public List<String> tabComplete(CommandSender commandSender, String string, String[] stringArray) throws IllegalArgumentException {
        Player player;
        if (commandSender instanceof Player) {
            player = (Player)commandSender;

        } else {
            player = null;
        }
        Player player2 = player;
        ArrayList arrayList = new ArrayList();
        Iterator iterator = commandSender.getServer().getOnlinePlayers().iterator();
        while (true) {
            String string2;
            if (!iterator.hasNext()) {
                arrayList.sort(String.CASE_INSENSITIVE_ORDER);
                return arrayList;
            }
            Player player3 = (Player)iterator.next();
            String string3 = player3.getName();
            if (player2 != null && !player2.canSee(player3)) continue;
            if (stringArray.length == 1 && StringUtil.startsWithIgnoreCase((String)string3, (String)(string2 = stringArray[stringArray.length - 1]))) {
                arrayList.add((Object)string3);
            }
            
        }
       
    }
}
