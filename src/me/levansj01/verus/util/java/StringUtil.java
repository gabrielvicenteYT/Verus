package me.levansj01.verus.util.java;

import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;

public class StringUtil {
    public static final String LINE;

    public static String differenceAsTime(long l) {
        long l2 = TimeUnit.MILLISECONDS.toDays(l);
        long l3 = TimeUnit.HOURS.convert(l, TimeUnit.MILLISECONDS);
        return l2 == 0L ? (l3 == 0L ? String.valueOf(new StringBuilder().append(ChatColor.WHITE).append(String.valueOf(TimeUnit.MINUTES.convert(l, TimeUnit.MILLISECONDS)).concat(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append(" minutes ago"))))) : String.valueOf(new StringBuilder().append(ChatColor.WHITE).append(String.valueOf(l3).concat(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append(" hours ago")))))) : String.valueOf(new StringBuilder().append(ChatColor.WHITE).append(String.valueOf(l2).concat(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append(" days ago")))));
    }

    static {
        LINE = String.valueOf(new StringBuilder().append(ChatColor.GRAY).append(ChatColor.STRIKETHROUGH.toString()).append("-----------------------------------------"));
    }

    public static String plainDifferenceAsTime(long l) {
        long l2 = TimeUnit.MILLISECONDS.toDays(l);
        long l3 = TimeUnit.MILLISECONDS.toHours(l);
        return l2 == 0L ? (l3 == 0L ? String.valueOf(new StringBuilder().append(TimeUnit.MILLISECONDS.toMinutes(l)).append(" mins")) : String.valueOf(new StringBuilder().append(l3).append(" hrs"))) : String.valueOf(new StringBuilder().append(l2).append(" days"));
    }
}

