package me.levansj01.verus.api;

import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class API {
    private static Boolean enabled = null;
    private String version = "Unknown";
    private static API api = null;

    protected static boolean isEnabled() {
        if (enabled == null) {
            enabled = Bukkit.getPluginManager().isPluginEnabled("VerusAPI");
        }
        return enabled;
    }

    public void enable(VerusPlugin verusPlugin) {
    }

    public String getVersion() {
        return this.version;
    }

    public boolean fireInitEvent(PlayerData playerData) {
        return false;
    }

    public void setVersion(String string) {
        this.version = string;
    }

    public void disable() {
    }

    public abstract boolean fireViolationEvent(PlayerData var1, Check var2, int var3);

    public static Plugin fetchPlugin() {
        return Bukkit.getPluginManager().getPlugin("VerusAPI");
    }

    public static void check() {
        enabled = null;
    }

    public static API getAPI() {
        if (API.isEnabled()) {
            if (api == null) {
                Plugin plugin = API.fetchPlugin();
                String string = plugin.getDescription().getVersion();
                try {
                    api = (API)Class.forName((String)("me.levansj01.verus.api.impl.API" + string.replace((CharSequence)".", (CharSequence)"_"))).asSubclass(API.class).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                api.setVersion(string);
                enabled = false;
            }
            return api;
        }
        return null;
    }

    public abstract boolean fireBanEvent(PlayerData var1, Check var2);
}
