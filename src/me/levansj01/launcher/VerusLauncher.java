package me.levansj01.launcher;

import me.levansj01.launcher.VerusLaunch;
import me.levansj01.verus.VerusPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class VerusLauncher
extends JavaPlugin {
    private static VerusLauncher plugin;
    private VerusLaunch launch;

    public static VerusLauncher getPlugin() {
        return plugin;
    }

    public void onLoad() {
        plugin = this;
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
    }

    public void onEnable() {
        this.launch = new VerusPlugin();
        this.launch.launch(this);
    }

    public void onDisable() {
        this.launch.shutdown();
    }
}
