package me.levansj01.verus.compat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.MutableBlockLocation;

public abstract class NMSManager
{
    private static NMSManager nmsManager;
    private ServerVersion serverVersion;
    
    private static NMSManager newInstance() {
        try {
            final String s = Bukkit.getServer().getClass().getName().split("\\.")[3];
            final NMSManager nmsManager = (NMSManager)Class.forName(NMSManager.class.getName().replace(".NMSManager", "." + s + ".NMSManager")).newInstance();
            nmsManager.setServerVersion(ServerVersion.valueOf(s));
            return nmsManager;
        }
        catch (Throwable t) {
            throw new IllegalArgumentException(t);
        }
    }
    
    public static NMSManager getInstance() {
        return (NMSManager.nmsManager == null) ? (NMSManager.nmsManager = newInstance()) : NMSManager.nmsManager;
    }
    
    public abstract Material getType(final Player p0, final World p1, final MutableBlockLocation p2);
    
    public abstract double getMovementSpeed(final Player p0);
    
    public <K, V> Map<K, V> createCache(final Long n, final Long n2) {
        return new ConcurrentHashMap<K, V>();
    }
    
    public ServerVersion getServerVersion() {
        return this.serverVersion;
    }
    
    public void setServerVersion(final ServerVersion serverVersion) {
        this.serverVersion = serverVersion;
    }
    
    public abstract MaterialData getTypeAndData(final Player p0, final World p1, final MutableBlockLocation p2);
    
    public abstract void sendPayload(final Player p0, final String p1);
    
    public abstract void sendDestroyPacket(final Player p0, final int p1);
    
    public abstract float getDamage(final Player p0, final World p1, final BlockPosition p2);
    
    public abstract void setPing(final Player p0, final int p1);
    
    public abstract float getFrictionFactor(final Block p0);
    
    public abstract ClientVersion getVersion(final Player p0);
    
    public abstract float getFrictionFactor(final Player p0, final World p1, final MutableBlockLocation p2);
    
    public abstract int sendFakeEntity(final PlayerData p0, final PlayerData p1);
    
    public abstract boolean isOnElytra(final Player p0);
    
    public void setAsyncPotionEffects(final Player player) {
    }
    
    public abstract int getCurrentTick();
    
    public abstract void sendTransaction(final Player p0, final short p1);
    
    static {
        NMSManager.nmsManager = null;
    }
}
