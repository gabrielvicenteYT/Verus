package de.xbrowniecodez.verus.customloader;

import org.bukkit.Bukkit;

import me.levansj01.verus.type.Loader;
import net.md_5.bungee.api.ChatColor;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomLoader extends Loader {
    private final String prefix = "[VerusLoader] ";

    private final String[] commandClasses = { "me.levansj01.verus.command.impl.AlertsCommand",
            "me.levansj01.verus.command.impl.LogsCommand", "me.levansj01.verus.command.impl.ManualBanCommand",
            "me.levansj01.verus.command.impl.PingCommand", "me.levansj01.verus.command.impl.RecentLogsCommand",
            "me.levansj01.verus.command.impl.VerusCommand", };

    private final String[] checkClasses = { "me.levansj01.verus.check.checks.badpackets.BadPacketsA",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsB",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsC",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsD",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsE",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsF",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsG1",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsG2",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsG3",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsG4",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsG5",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsG6",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsH",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsI",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsK",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsM",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsO",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsP",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsR",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsU",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsU",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsV",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsV",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsX",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsY",
            "me.levansj01.verus.check.checks.badpackets.BadPacketsZ",
            "me.levansj01.verus.check.checks.crasher.ServerCrasherA",
            "me.levansj01.verus.check.checks.crasher.ServerCrasherB",
            "me.levansj01.verus.check.checks.crasher.ServerCrasherC",
            "me.levansj01.verus.check.checks.crasher.ServerCrasherD",
            "me.levansj01.verus.check.checks.crasher.ServerCrasherE",
            "me.levansj01.verus.check.checks.crasher.ServerCrasherF", "me.levansj01.verus.check.checks.fly.FlyA",
            "me.levansj01.verus.check.checks.fly.FlyB", "me.levansj01.verus.check.checks.fly.FlyC",
            "me.levansj01.verus.check.checks.fly.FlyD", "me.levansj01.verus.check.checks.fly.FlyE",
            "me.levansj01.verus.check.checks.fly.FlyF", "me.levansj01.verus.check.checks.fly.FlyG",
            "me.levansj01.verus.check.checks.fly.FlyI", "me.levansj01.verus.check.checks.fly.FlyK",
            "me.levansj01.verus.check.checks.fly.FlyZ", "me.levansj01.verus.check.checks.inventory.InventoryG",
            "me.levansj01.verus.check.checks.inventory.InventoryJ",
            "me.levansj01.verus.check.checks.killaura.KillAuraA", "me.levansj01.verus.check.checks.killaura.KillAuraB",
            "me.levansj01.verus.check.checks.killaura.KillAuraC", "me.levansj01.verus.check.checks.killaura.KillAuraD",
            "me.levansj01.verus.check.checks.killaura.KillAuraE", "me.levansj01.verus.check.checks.killaura.KillAuraH",
            "me.levansj01.verus.check.checks.killaura.KillAuraM", "me.levansj01.verus.check.checks.killaura.KillAuraN",
            "me.levansj01.verus.check.checks.reach.ReachA", "me.levansj01.verus.check.checks.scaffold.ScaffoldE",
            "me.levansj01.verus.check.checks.speed.SpeedA", "me.levansj01.verus.check.checks.speed.SpeedB",
            "me.levansj01.verus.check.checks.timer.TimerA", "me.levansj01.verus.check.checks.timer.TimerB",
            "me.levansj01.verus.check.checks.velocity.VelocityC" };

    private final String[] premiumCheckClasses = { "me.levansj01.verus.type.premium.checks.aim.AimA",
            "me.levansj01.verus.type.premium.checks.aim.AimA1", "me.levansj01.verus.type.premium.checks.aim.AimC",
            "me.levansj01.verus.type.premium.checks.aim.AimD", "me.levansj01.verus.type.premium.checks.aim.AimH",
            "me.levansj01.verus.type.premium.checks.aim.AimJ",
            "me.levansj01.verus.type.premium.checks.autoclicker.AutoClickerA",
            "me.levansj01.verus.type.premium.checks.autoclicker.AutoClickerB",
            "me.levansj01.verus.type.premium.checks.autoclicker.AutoClickerC",
            "me.levansj01.verus.type.premium.checks.autoclicker.AutoClickerD",
            "me.levansj01.verus.type.premium.checks.autoclicker.AutoClickerE",
            "me.levansj01.verus.type.premium.checks.autoclicker.AutoClickerI",
            "me.levansj01.verus.type.premium.checks.autoclicker.AutoClickerJ",
            "me.levansj01.verus.type.premium.checks.autoclicker.AutoClickerX",
            "me.levansj01.verus.type.premium.checks.autoclicker.AutoClickerX2",
            "me.levansj01.verus.type.premium.checks.autoclicker.AutoClickerY",
            "me.levansj01.verus.type.premium.checks.inventory.InventoryA",
            "me.levansj01.verus.type.premium.checks.inventory.InventoryB",
            "me.levansj01.verus.type.premium.checks.inventory.InventoryC",
            "me.levansj01.verus.type.premium.checks.inventory.InventoryK",
            "me.levansj01.verus.type.premium.checks.killaura.KillAuraF",
            "me.levansj01.verus.type.premium.checks.killaura.KillAuraK",
            "me.levansj01.verus.type.premium.checks.killaura.KillAuraO",
            "me.levansj01.verus.type.premium.checks.killaura.KillAuraQ",
            "me.levansj01.verus.type.premium.checks.payload.CustomPayloadA",
            "me.levansj01.verus.type.premium.checks.payload.CustomPayloadB",
            "me.levansj01.verus.type.premium.checks.reach.ReachB",
            "me.levansj01.verus.type.premium.checks.reach.ReachC",
            "me.levansj01.verus.type.premium.checks.scaffold.ScaffoldA",
            "me.levansj01.verus.type.premium.checks.scaffold.ScaffoldD",
            "me.levansj01.verus.type.premium.checks.speed.SpeedE",
            "me.levansj01.verus.type.premium.checks.velocity.VelocityA",
            "me.levansj01.verus.type.premium.checks.velocity.VelocityB" };

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
                Class.forName(s);
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
                Class.forName(s);
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
                Class.forName(s);
                i.getAndIncrement();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + prefix + "Loaded " + i + " Premium Checks");
    }
}
