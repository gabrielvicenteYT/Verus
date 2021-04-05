package me.levansj01.verus.check;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.ServerOperator;

import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.type.VerusTypeLoader;

public class Check
{
    protected Player player;
    protected PlayerData playerData;
    protected double minViolation;
    protected double violations;
    private int maxViolation;
    private List<ClientVersion> unsupportedVersions;
    private String friendlyName;
    private CheckVersion checkVersion;
    private int priority;
    private boolean pullback;
    private List<ServerVersion> unsupportedServers;
    private CheckType type;
    private int lastViolation;
    private String subType;
    private boolean logData;
    
    public Check(final CheckType type, final String subType, final String friendlyName, final CheckVersion checkVersion, final ClientVersion... array) {
        this.maxViolation = Integer.MAX_VALUE;
        this.priority = 1;
        this.unsupportedServers = Collections.emptyList();
        this.type = type;
        this.subType = subType;
        this.friendlyName = friendlyName;
        this.checkVersion = checkVersion;
        this.unsupportedVersions = Arrays.asList(array);
    }
    
    public Check() {
        this.maxViolation = Integer.MAX_VALUE;
        this.priority = 1;
        this.unsupportedServers = Collections.emptyList();
        if (this.getClass().isAnnotationPresent(CheckInfo.class)) {
            final CheckInfo checkInfo = this.getClass().getAnnotation(CheckInfo.class);
            this.setType(checkInfo.type());
            this.setSubType(checkInfo.subType());
            this.setFriendlyName(checkInfo.friendlyName());
            this.setCheckVersion(checkInfo.version());
            this.setUnsupportedVersions(Arrays.asList(checkInfo.unsupportedVersions()));
            this.setUnsupportedServers(checkInfo.unsupportedServers());
            this.setMinViolation(checkInfo.minViolations());
            this.setPriority(checkInfo.priority());
            if (checkInfo.schematica() || checkInfo.butterfly() || checkInfo.phase()) {
                final VerusConfiguration verusConfig = StorageEngine.getInstance().getVerusConfig();
                if (checkInfo.schematica() && verusConfig.isSchemBans()) {
                    this.setMaxViolation(checkInfo.maxViolations());
                }
                if (checkInfo.butterfly() && verusConfig.isButterflyBans()) {
                    this.setMaxViolation(checkInfo.maxViolations());
                }
                if (checkInfo.phase() && verusConfig.isPhaseEnabled()) {
                    this.setMaxViolation(checkInfo.maxViolations());
                }
            }
            else {
                this.setMaxViolation(checkInfo.maxViolations());
            }
            this.setLogData(checkInfo.logData());
        }
    }
    
    public void handleViolation(final String s) {
        this.handleViolation(s, 1.0);
    }
    
    public void debug(final String s) {
        if (VerusTypeLoader.isDev()) {
            Bukkit.getOnlinePlayers().stream().filter(ServerOperator::isOp).forEach(player -> player.sendMessage(ChatColor.GRAY + "(DEBUG) " + ChatColor.RESET + this.playerData.getName() + ": " + s));
        }
    }
    
    @Override
    public int hashCode() {
        final int n = 1;
        final CheckType type = this.getType();
        final int n2 = 59 + ((type == null) ? 43 : type.hashCode());
        final String subType = this.getSubType();
        return n2 * 59 + ((subType == null) ? 43 : subType.hashCode());
    }
    
    public boolean isLogData() {
        return this.logData;
    }
    
    public void setLogData(final boolean logData) {
        this.logData = logData;
    }
    
    public PlayerData getPlayerData() {
        return this.playerData;
    }
    
    public void setPlayerData(final PlayerData playerData) {
        this.playerData = playerData;
    }
    
    public void decreaseVL(final double n) {
        this.violations -= Math.min(this.violations - this.minViolation, n);
    }
    
    public List<ClientVersion> getUnsupportedVersions() {
        return this.unsupportedVersions;
    }
    
    public void setUnsupportedVersions(final List<ClientVersion> unsupportedVersions) {
        this.unsupportedVersions = unsupportedVersions;
    }
    
    public CheckType getType() {
        return this.type;
    }
    
    public void setType(final CheckType type) {
        this.type = type;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Check)) {
            return false;
        }
        final Check check = (Check)o;
        if (!check.canEqual(this)) {
            return false;
        }
        final CheckType type = this.getType();
        final CheckType type2 = check.getType();
        Label_0068: {
            if (type == null) {
                if (type2 == null) {
                    break Label_0068;
                }
            }
            else if (type.equals(type2)) {
                break Label_0068;
            }
            return false;
        }
        final String subType = this.getSubType();
        final String subType2 = check.getSubType();
        if (subType == null) {
            return subType2 == null;
        }
        return subType.equals(subType2);
    }
    
    public List<ServerVersion> getUnsupportedServers() {
        return this.unsupportedServers;
    }
    
    public void setUnsupportedServers(final ServerVersion... array) {
        this.unsupportedServers = Arrays.asList(array);
    }
    
    public double getMinViolation() {
        return this.minViolation;
    }
    
    public void setMinViolation(final double n) {
        this.violations = n;
        this.minViolation = n;
    }
    
    public double getViolations() {
        return this.violations;
    }
    
    public void setViolations(final double violations) {
        this.violations = violations;
    }
    
    public String identifier() {
        return this.type.ordinal() + "" + this.subType;
    }
    
    public String getSubType() {
        return this.subType;
    }
    
    public void setSubType(final String subType) {
        this.subType = subType;
    }
    
    protected boolean canEqual(final Object o) {
        return o instanceof Check;
    }
    
    public String getFriendlyName() {
        return this.friendlyName;
    }
    
    public void setFriendlyName(final String friendlyName) {
        this.friendlyName = friendlyName;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public void setPriority(final int priority) {
        this.priority = priority;
    }
    
    public int getMaxViolation() {
        return this.maxViolation;
    }
    
    public void setMaxViolation(final int maxViolation) {
        this.maxViolation = maxViolation;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public void setPlayer(final Player player) {
        this.player = player;
    }
    
    public CheckVersion getCheckVersion() {
        return this.checkVersion;
    }
    
    public void setCheckVersion(final CheckVersion checkVersion) {
        this.checkVersion = checkVersion;
    }
    
    public int getLastViolation() {
        return this.lastViolation;
    }
    
    public void setLastViolation(final int lastViolation) {
        this.lastViolation = lastViolation;
    }
    
    public void handleViolation() {
        this.handleViolation("");
    }
    
    public void handleViolation(final String s, final double n) {
        this.handleViolation(s, n, false);
    }
    
    public void handleViolation(final String s, final double n, final boolean b) {
        AlertManager.getInstance().handleViolation(this.playerData, this, s, n, b);
    }
    
    public boolean supported() {
        return !this.unsupportedVersions.contains(this.playerData.getVersion());
    }
    
    public boolean isPullback() {
        return this.pullback;
    }
    
    public void setPullback(final boolean pullback) {
        this.pullback = pullback;
    }
    
    public void run(final Runnable runnable) {
        PacketManager.getInstance().postToMainThread(() -> {
            if (this.playerData.isEnabled()) {
                runnable.run();
            }
        });
    }
    
    public String name() {
        return this.type.getName() + " " + this.subType;
    }
}