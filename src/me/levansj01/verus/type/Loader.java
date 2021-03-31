package me.levansj01.verus.type;

import me.levansj01.launcher.VerusLauncher;

public abstract class Loader {
    public static String pingCommand;
    public static String logsCommand;
    public static String recentlogsCommand;
    public static String alertsCommand;
    public static String manualbanCommand;
    private static final String username;

    public static String getUsername() {
        return username;
    }

    public static String getRecentlogsCommand() {
        return recentlogsCommand;
    }

    public abstract void load();

    static {
        username = VerusLauncher.getPlugin().getConfig().getString("launcher.username", "");
        logsCommand = "logs";
        recentlogsCommand = "recentlogs";
        manualbanCommand = "manualban";
        alertsCommand = "alerts";
        pingCommand = "ping";
    }

    public static String getLogsCommand() {
        return logsCommand;
    }

    public static String getAlertsCommand() {
        return alertsCommand;
    }

    public static String getManualbanCommand() {
        return manualbanCommand;
    }

    public static String getPingCommand() {
        return pingCommand;
    }
}
