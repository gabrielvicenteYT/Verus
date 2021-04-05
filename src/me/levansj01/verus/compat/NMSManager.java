package me.levansj01.verus.compat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import me.levansj01.verus.compat.BlockPosition;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.MutableBlockLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public abstract class NMSManager {
    private static NMSManager nmsManager = null;
    private ServerVersion serverVersion;

    public abstract boolean setInWater(Player var1, boolean var2);

    public void setServerVersion(ServerVersion serverVersion) {
        this.serverVersion = serverVersion;
    }

    public abstract double getMovementSpeed(Player var1);

    public abstract ClientVersion getVersion(Player var1);

    public abstract void setPing(Player var1, int var2);

    public abstract MaterialData getTypeAndData(Player var1, World var2, MutableBlockLocation var3);

    public abstract Material getType(Player var1, World var2, MutableBlockLocation var3);

    public abstract int getCurrentTick();

    public abstract void sendTransaction(Player var1, short var2);

    private static NMSManager newInstance() {
        try {
            String string = Bukkit.getServer().getClass().getName().split("\\.")[3];
            NMSManager nMSManager = (NMSManager)Class.forName((String)NMSManager.class.getName().replace((CharSequence)".NMSManager", (CharSequence)("." + string + ".NMSManager"))).newInstance();
            nMSManager.setServerVersion(ServerVersion.valueOf((String)string));
            return nMSManager;
        }
        catch (Throwable throwable) {
            throw new IllegalArgumentException(throwable);
        }
    }

    public abstract float getFrictionFactor(Player var1, World var2, MutableBlockLocation var3);

    public <K, V> Map<K, V> createCache(Long l, Long l2) {
        return new ConcurrentHashMap<K, V>();
    }

    public abstract float getFrictionFactor(Block var1);

    public boolean isEmpty(World world, Entity entity, Cuboid cuboid, MutableBlockLocation mutableBlockLocation) {
        throw new UnsupportedOperationException();
    }

    public boolean rayTrace(World world, double d, double d2, double d3, double d4, double d5, double d6, boolean bl, boolean bl2, boolean bl3) {
        throw new UnsupportedOperationException();
    }

    public abstract boolean setOnGround(Player var1, boolean var2);

    public abstract void sendBlockUpdate(Player var1, Location var2);

    public boolean isGliding(Player player) {
        return false;
    }

    public abstract int sendFakeEntity(PlayerData var1, PlayerData var2);

    public ServerVersion getServerVersion() {
        return this.serverVersion;
    }

    public void setAsyncPotionEffects(Player player) {
    }

    public abstract double getItemModifier(ItemStack var1);

    public boolean isRiptiding(Player player) {
        return false;
    }

    public abstract Cuboid getBoundingBox(Player var1, World var2, MutableBlockLocation var3);

    public abstract boolean isLoaded(World var1, int var2, int var3);

    public abstract float getDamage(Player var1, World var2, BlockPosition var3);

    public static NMSManager getInstance() {
        return nmsManager == null ? (nmsManager = NMSManager.newInstance()) : nmsManager;
    }

    public ItemStack getOffHand(Player player) {
        return null;
    }
}
