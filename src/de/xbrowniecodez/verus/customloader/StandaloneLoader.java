package de.xbrowniecodez.verus.customloader;

import org.bukkit.Bukkit;

import me.levansj01.verus.type.Loader;
import net.md_5.bungee.api.ChatColor;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class StandaloneLoader extends Loader {

    private final String prefix = "[VerusLoader] ";
    private final String basePackage = "me.levansj01.verus.";

    private final String[] commandClasses = { "AlertsCommand",
            "LogsCommand", "ManualBanCommand",
            "PingCommand", "RecentLogsCommand",
            "VerusCommand"};

    private final String[] checkClasses = { "badpackets.BadPacketsA",
            "badpackets.BadPacketsB",
            "badpackets.BadPacketsC",
            "badpackets.BadPacketsD",
            "badpackets.BadPacketsE",
            "badpackets.BadPacketsF",
            "badpackets.BadPacketsG1",
            "badpackets.BadPacketsG2",
            "badpackets.BadPacketsG3",
            "badpackets.BadPacketsG4",
            "badpackets.BadPacketsG5",
            "badpackets.BadPacketsG6",
            "badpackets.BadPacketsH",
            "badpackets.BadPacketsI",
            "badpackets.BadPacketsK",
            "badpackets.BadPacketsM",
            "badpackets.BadPacketsO",
            "badpackets.BadPacketsP",
            "badpackets.BadPacketsR",
            "badpackets.BadPacketsU",
            "badpackets.BadPacketsU",
            "badpackets.BadPacketsV",
            "badpackets.BadPacketsV",
            "badpackets.BadPacketsX",
            "badpackets.BadPacketsY",
            "badpackets.BadPacketsZ",
            "crasher.ServerCrasherA",
            "crasher.ServerCrasherB",
            "crasher.ServerCrasherC",
            "crasher.ServerCrasherD",
            "crasher.ServerCrasherE",
            "crasher.ServerCrasherF",
            "fly.FlyA", "fly.FlyB",
            "fly.FlyC", "fly.FlyD",
            "fly.FlyE", "fly.FlyF",
            "fly.FlyG", "fly.FlyI",
            "fly.FlyK", "fly.FlyZ",
            "inventory.InventoryG",
            "inventory.InventoryJ",
            "killaura.KillAuraA", "killaura.KillAuraB",
            "killaura.KillAuraC", "killaura.KillAuraD",
            "killaura.KillAuraE", "killaura.KillAuraH",
            "killaura.KillAuraM", "killaura.KillAuraN",
            "reach.ReachA",
            "scaffold.ScaffoldE",
            "speed.SpeedA", "speed.SpeedB",
            "timer.TimerA", "timer.TimerB",
            "velocity.VelocityC" };

    private final String[] premiumCheckClasses = { "aim.AimA",
            "aim.AimA1", "aim.AimC",
            "aim.AimD", "aim.AimH",
            "aim.AimJ",
            "autoclicker.AutoClickerA",
            "autoclicker.AutoClickerB",
            "autoclicker.AutoClickerC",
            "autoclicker.AutoClickerD",
            "autoclicker.AutoClickerE",
            "autoclicker.AutoClickerI",
            "autoclicker.AutoClickerJ",
            "autoclicker.AutoClickerX",
            "autoclicker.AutoClickerX2",
            "autoclicker.AutoClickerY",
            "inventory.InventoryA",
            "inventory.InventoryB",
            "inventory.InventoryC",
            "inventory.InventoryK",
            "killaura.KillAuraF",
            "killaura.KillAuraK",
            "killaura.KillAuraO",
            "killaura.KillAuraQ",
            "payload.CustomPayloadA",
            "payload.CustomPayloadB",
            "reach.ReachB",
            "reach.ReachC",
            "scaffold.ScaffoldA",
            "scaffold.ScaffoldD",
            "speed.SpeedE",
            "velocity.VelocityA",
            "velocity.VelocityB" };

    @Override
    public void load() {
        Bukkit.getConsoleSender()
                .sendMessage(ChatColor.RED + prefix + "Loading CustomLoader by " + ChatColor.AQUA + "xBrownieCodez");
        loadCommands();
        loadChecks();
        loadPremiumChecks();
    }

    public void loadCommands() {
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(commandClasses).forEach(s -> {
            try {
                Class.forName(basePackage + "command.impl." + s);
                i.getAndIncrement();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + prefix + "Loaded " + i + " Commands");
    }

    public void loadChecks() {
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(checkClasses).forEach(s -> {
            try {
                Class.forName(basePackage + "check.checks." + s);
                i.getAndIncrement();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + prefix + "Loaded " + i + " Basic Checks");
    }

    public void loadPremiumChecks() {
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(premiumCheckClasses).forEach(s -> {
            try {
                Class.forName(basePackage + "type.premium.checks." + s);
                i.getAndIncrement();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + prefix + "Loaded " + i + " Premium Checks");
    }
}
