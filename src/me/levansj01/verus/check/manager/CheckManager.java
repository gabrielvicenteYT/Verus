package me.levansj01.verus.check.manager;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.type.VerusTypeLoader;
import org.bukkit.configuration.file.YamlConfiguration;

public class CheckManager {
    private static CheckManager instance;
    private List<Check> checks;
    private Map<String, Boolean> autoban;
    private Map<String, Boolean> enabled;

    private void lambda$loadChecks$0(YamlConfiguration yamlConfiguration, Check check) {
        this.enabled.put(check.identifier(), yamlConfiguration.getBoolean(check.getType().getName() + "." + check.getSubType() + ".enabled", true));
        this.autoban.put(check.identifier(), yamlConfiguration.getBoolean(check.getType().getName() + "." + check.getSubType() + ".autoban", true));
    }

    public void disable() {
        this.checks = null;
        this.enabled = null;
        this.autoban = null;
    }

    private void saveChecks() {
        VerusConfiguration verusConfiguration = StorageEngine.getInstance().getVerusConfig();
        YamlConfiguration yamlConfiguration = verusConfiguration.getCheckConfiguration();
        this.checks.forEach(arg_0 -> this.saveChecks(yamlConfiguration, arg_0));
        verusConfiguration.saveConfig(yamlConfiguration, verusConfiguration.getCheckFile());
    }

    public void setEnabled(Check check, boolean bl) {
        this.enabled.put(check.identifier(), bl);
        this.saveChecks();
    }

    public boolean isAutoban(Check check) {
        return this.autoban.getOrDefault(check.identifier(), true);
    }

    public boolean isEnabled(Check check) {
        return this.enabled.getOrDefault(check.identifier(), true);
    }

    private void saveChecks(YamlConfiguration yamlConfiguration, Check check) {
        yamlConfiguration.set(check.getType().getName() + "." + check.getSubType() + ".enabled", (Object)this.isEnabled(check));
        yamlConfiguration.set(check.getType().getName() + "." + check.getSubType() + ".autoban", (Object)this.isAutoban(check));
    }

    public static CheckManager getInstance() {
        CheckManager checkManager;
        if (instance == null) {
            checkManager = instance = new CheckManager();
             return checkManager;
            
        }
        checkManager = instance;
        return checkManager;
    }

    private void loadChecks() {
        YamlConfiguration yamlConfiguration = StorageEngine.getInstance().getVerusConfig().getCheckConfiguration();
        this.checks.forEach(arg_0 -> this.lambda$loadChecks$0(yamlConfiguration, arg_0));
    }

    public void setAutoban(Check check, boolean bl) {
        this.autoban.put(check.identifier(), bl);
        this.saveChecks();
    }

    public void enable(VerusPlugin verusPlugin) {
        this.checks = verusPlugin.getTypeLoader().loadChecks();
        this.enabled = new ConcurrentHashMap();
        this.autoban = new ConcurrentHashMap();
        this.loadChecks();
        GUIManager.getInstance().enable(verusPlugin);
    }
}
