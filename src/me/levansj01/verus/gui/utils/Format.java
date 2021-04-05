package me.levansj01.verus.gui.utils;

import me.levansj01.verus.VerusPlugin;
import org.bukkit.ChatColor;


public class Format {


    public static String info(String name, String value) {
        return String.format(VerusPlugin.COLOR + "%s" + ChatColor.WHITE + "%s", name + ": ", value);
    }

    public static String toggle(boolean condition, String text) {
        return (condition ? ChatColor.GREEN : ChatColor.RED) + text;
    }

    public static String description(String desc) {
        return ChatColor.GRAY + desc;
    }

}
