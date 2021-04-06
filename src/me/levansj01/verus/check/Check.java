package me.levansj01.verus.check;

import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.PacketManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.java.CachedSupplier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.ServerOperator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class Check {
    protected PlayerData playerData;
    protected Player player;
    protected double violations;
    protected double minViolation;
    private CheckType type;
    private String subType;
    private boolean logData;
    private boolean pullback;
    private int maxViolation = Integer.MAX_VALUE;
    private boolean heavy;
    private String friendlyName;
    private CheckVersion checkVersion;
    private int priority = 1;
    private List<ClientVersion> unsupportedVersions;
    private int lastViolation;
    private List<ServerVersion> unsupportedServers = Collections.emptyList();

    public Check() {
        if (this.getClass().isAnnotationPresent(CheckInfo.class)) {
            final CheckInfo checkInfo = this.getClass().getAnnotation(CheckInfo.class);
            this.setType(checkInfo.type());
            this.setSubType(checkInfo.subType());
            this.setFriendlyName(checkInfo.friendlyName());
            this.setCheckVersion(checkInfo.version());
            final ClientVersion unsupportedAtleast = checkInfo.unsupportedAtleast();
            if (unsupportedAtleast != ClientVersion.NONE) {
                final ClientVersion[] array = new ClientVersion[ClientVersion.VERSION_UNSUPPORTED.ordinal() - unsupportedAtleast.ordinal()];
                System.arraycopy(ClientVersion.values(), unsupportedAtleast.ordinal(), array, 0, array.length);
                this.setUnsupportedVersions(Arrays.asList(array));
            } else {
                this.setUnsupportedVersions(Arrays.asList(checkInfo.unsupportedVersions()));
            }
            this.setUnsupportedServers(checkInfo.unsupportedServers());
            this.setMinViolation(checkInfo.minViolations());
            this.setPriority(checkInfo.priority());
            this.setHeavy(checkInfo.heavy());
            if (checkInfo.schematica() || checkInfo.butterfly()) {
                final VerusConfiguration verusConfig = StorageEngine.getInstance().getVerusConfig();
                if (checkInfo.schematica() && verusConfig.isSchemBans()) {
                    this.setMaxViolation(checkInfo.maxViolations());
                }
                if (checkInfo.butterfly() && verusConfig.isButterflyBans()) {
                    this.setMaxViolation(checkInfo.maxViolations());
                }
            } else {
                this.setMaxViolation(checkInfo.maxViolations());
            }
            this.setLogData(checkInfo.logData());
        }
    }

    public Check(CheckType checkType, String subType, String friendlyName, CheckVersion checkVersion, ClientVersion... unsupportedVersions) {
        this.type = checkType;
        this.subType = subType;
        this.friendlyName = friendlyName;
        this.checkVersion = checkVersion;
        this.unsupportedVersions = Arrays.asList(unsupportedVersions);
    }


    public String getSubType() {
        return this.subType;
    }

    public void setSubType(String string) {
        this.subType = string;
    }

    public void handleViolation(Supplier<String> supplier, double d) {
        this.handleViolation(supplier, d, false);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Check)) {
            return false;
        }
        Check check = (Check) object;
        if (!check.canEqual(this)) {
            return false;
        }
        CheckType checkType = this.getType();
        CheckType checkType2 = check.getType();
        if (checkType == null) {
            if (checkType2 != null) {
                return false;
            }
        } else if (!checkType.equals(checkType2)) {
            return false;
        }
        String string = this.getSubType();
        String string2 = check.getSubType();
        if (string == null) {
            return string2 == null;
        }
        return string.equals(string2);
    }

    public PlayerData getPlayerData() {
        return this.playerData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public void setFriendlyName(String string) {
        this.friendlyName = string;
    }

    public void run(Runnable runnable) {
        PacketManager.getInstance().postToMainThread(() -> {
            if (!this.playerData.isEnabled()) return;
            runnable.run();
        });
    }

    public void handleViolation(String string, double d) {
        this.handleViolation(string, d, false);
    }

    public double getViolations() {
        return this.violations;
    }

    public void setViolations(double d) {
        this.violations = d;
    }

    public void debug(String string) {
        this.debug(() -> string);
    }

    public int hashCode() {
        int n;
        int n2;
        int n3 = 59;
        int n4 = 1;
        CheckType checkType = this.getType();
        if (checkType == null) {
            n2 = 43;
        } else {
            n2 = checkType.hashCode();
        }
        n4 = n4 * 59 + n2;
        String string = this.getSubType();
        if (string == null) {
            n = 43;
            return n4 * 59 + n;
        }
        n = string.hashCode();
        return n4 * 59 + n;
    }

    public List<ServerVersion> getUnsupportedServers() {
        return this.unsupportedServers;
    }

    public void setUnsupportedServers(ServerVersion... versions) {
        this.unsupportedServers = Arrays.asList(versions);
    }

    public int getLastViolation() {
        return this.lastViolation;
    }

    public void setLastViolation(int n) {
        this.lastViolation = n;
    }

    public void handleViolation() {
        this.handleViolation("");
    }

    public void debug(Supplier<String> supplier) {
        CachedSupplier<?> cachedSupplier = CachedSupplier.of(supplier);
        if (!VerusTypeLoader.isDev()) return;
        Bukkit.getOnlinePlayers().stream().filter(ServerOperator::isOp).forEach(player -> this.debug(cachedSupplier, player));
    }

    public boolean supported() {
        if (this.unsupportedVersions.contains(this.playerData.getVersion())) return false;
        if (this.playerData.getVersion() == ClientVersion.VERSION_UNSUPPORTED) {
            if (!this.unsupportedVersions.isEmpty()) return false;
        }
        if (this.heavy) {
            return StorageEngine.getInstance().getVerusConfig().isHeavyChecks();
        }
        return true;
    }

    public double getMinViolation() {
        return this.minViolation;
    }

    public void setMinViolation(double d) {
        this.minViolation = this.violations = d;
    }

    public List<ClientVersion> getUnsupportedVersions() {
        return this.unsupportedVersions;
    }

    public void setUnsupportedVersions(List<ClientVersion> list) {
        this.unsupportedVersions = list;
    }

    public CheckType getType() {
        return this.type;
    }

    public void setType(CheckType checkType) {
        this.type = checkType;
    }

    public CheckVersion getCheckVersion() {
        return this.checkVersion;
    }

    public void setCheckVersion(CheckVersion checkVersion) {
        this.checkVersion = checkVersion;
    }

    public String name() {
        return this.type.getName() + " " + this.subType;
    }

    public void handleViolation(String string) {
        this.handleViolation(string, 1.0);
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void handleViolation(Supplier<String> supplier) {
        this.handleViolation(supplier, 1.0);
    }

    private void debug(CachedSupplier<?> cachedSupplier, Player player) {
        player.sendMessage(ChatColor.GRAY + "(DEBUG) " + ChatColor.RESET + this.playerData.getName() + ": " + cachedSupplier.get());
    }

    protected boolean canEqual(Object object) {
        return object instanceof Check;
    }

    public String identifier() {
        return this.type.ordinal() + "" + this.subType;
    }

    public boolean isHeavy() {
        return this.heavy;
    }

    public void setHeavy(boolean heavy) {
        this.heavy = heavy;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int n) {
        this.priority = n;
    }

    public boolean isLogData() {
        return this.logData;
    }

    public void setLogData(boolean logData) {
        this.logData = logData;
    }

    public boolean isPullback() {
        return this.pullback;
    }

    public void setPullback(boolean pullback) {
        this.pullback = pullback;
    }

    public void decreaseVL(double amount) {
        this.violations -= Math.min(this.violations - this.minViolation, amount);
    }

    public void handleViolation(Supplier<String> supplier, double d, boolean bl) {
        AlertManager.getInstance().handleViolation(this.playerData, this, supplier, d, bl);
    }

    public int getMaxViolation() {
        return this.maxViolation;
    }

    public void setMaxViolation(int n) {
        this.maxViolation = n;
    }

    public void handleViolation(String string, double d, boolean bl) {
        this.handleViolation(() -> string, d, bl);
    }
}
