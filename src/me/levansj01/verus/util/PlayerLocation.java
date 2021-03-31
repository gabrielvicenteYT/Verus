package me.levansj01.verus.util;

import java.util.ArrayList;
import me.levansj01.verus.compat.BlockPosition;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.PacketLocation;
import me.levansj01.verus.util.java.MathHelper;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.java.Vector3d;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class PlayerLocation {

    private Boolean ground;
    private double y;
    private int tickTime;
    private double x;
    private float yaw;
    private long timestamp;
    private float pitch;
    private double z;

    public PlayerLocation(long l, int n, double d, double d2, double d3, float f, float f2, Boolean bl) {
        this.timestamp = l;
        this.tickTime = n;
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.yaw = f;
        this.pitch = f2;
        this.ground = bl;
    }

    public PlayerLocation clone() {
        return new PlayerLocation(this.timestamp, this.tickTime, this.x, this.y, this.z, this.yaw, this.pitch, this.ground);
    }

    public void setYaw(float f) {
        this.yaw = f;
    }

    public Boolean getGround() {
        return this.ground;
    }

    public PlayerLocation midpoint(PlayerLocation playerLocation) {
        return new PlayerLocation((this.timestamp + playerLocation.timestamp) / 2L, (this.tickTime + playerLocation.tickTime) / 2, (this.x + playerLocation.x) / 2.0, (this.y + playerLocation.y) / 2.0, (this.z + playerLocation.z) / 2.0, (this.yaw + playerLocation.yaw) / 2.0f, (this.pitch + playerLocation.pitch) / 2.0f, this.ground != false && playerLocation.ground != false);
    }

    public BlockPosition getBlockPosition() {
        return new BlockPosition((int)Math.floor(this.x), (int)Math.floor(this.y), (int)Math.floor(this.z));
    }

    public int getTickTime() {
        return this.tickTime;
    }

    public double getY() {
        return this.y;
    }

    public void setGround(Boolean bl) {
        this.ground = bl;
    }

    public boolean sameBlock(PlayerLocation playerLocation) {
        return (int)Math.floor(this.x) == (int)Math.floor(playerLocation.x) && (int)Math.floor(this.y) == (int)Math.floor(playerLocation.y) && (int)Math.floor(this.z) == (int)Math.floor(playerLocation.z);
    }

    public void setZ(double d) {
        this.z = d;
    }

    public double distanceXZ(PacketLocation packetLocation) {
        return MathHelper.sqrt_double(this.distanceXZSquared(packetLocation));
    }

    public Cuboid to(PlayerLocation playerLocation) {
        return new Cuboid(Math.min(this.x, playerLocation.x), Math.max(this.x, playerLocation.x), Math.min(this.y, playerLocation.y), Math.max(this.y, playerLocation.y), Math.min(this.z, playerLocation.z), Math.max(this.z, playerLocation.z));
    }

    public void setTickTime(int n) {
        this.tickTime = n;
    }

    public void setY(double d) {
        this.y = d;
    }

    public Vector3d getDirection() {
        double d = Math.cos(Math.toRadians(this.pitch));
        return new Vector3d(-d * Math.sin(Math.toRadians(this.yaw)), -Math.sin(Math.toRadians(this.pitch)), d * Math.cos(Math.toRadians(this.yaw)));
    }

    public void setTimestamp(long l) {
        this.timestamp = l;
    }

    public boolean sameLocationAndDirection(PlayerLocation playerLocation) {
        return this.x == playerLocation.x && this.y == playerLocation.y && this.z == playerLocation.z && this.yaw == playerLocation.yaw && this.pitch == playerLocation.pitch;
    }

    public Vector toVector() {
        return new Vector(this.x, this.y, this.z);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        PlayerLocation playerLocation = (PlayerLocation)object;
        if (this.timestamp != playerLocation.timestamp) {
            return false;
        }
        if (this.tickTime != playerLocation.tickTime) {
            return false;
        }
        if (Double.compare(playerLocation.x, this.x) != 0) {
            return false;
        }
        if (Double.compare(playerLocation.y, this.y) != 0) {
            return false;
        }
        if (Double.compare(playerLocation.z, this.z) != 0) {
            return false;
        }
        if (Float.compare(playerLocation.yaw, this.yaw) != 0) {
            return false;
        }
        return Float.compare(playerLocation.pitch, this.pitch) == 0;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public double distanceXZSquared(PlayerLocation playerLocation) {
        return Math.pow(this.x - playerLocation.x, 2.0) + Math.pow(this.z - playerLocation.z, 2.0);
    }

    public void setX(double d) {
        this.x = d;
    }

    public PlayerLocation up() {
        return this.add(0.0, 1.0, 0.0);
    }

    public double getZ() {
        return this.z;
    }

    public PlayerLocation spectator() {
        return new PlayerLocation(this.timestamp, this.tickTime, MathUtil.relEntityRoundPos(this.x), MathUtil.relEntityRoundPos(this.y), MathUtil.relEntityRoundPos(this.z), MathUtil.relEntityRoundLook(this.yaw), MathUtil.relEntityRoundLook(this.pitch), this.ground);
    }

    public Location toLocation(World world) {
        return new Location(world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public Vector3d toVector3d() {
        return new Vector3d(this.x, this.y, this.z);
    }

    public int hashCode() {
        int n = (int)(this.timestamp ^ this.timestamp >>> 32);
        n = 31 * n + this.tickTime;
        long l = Double.doubleToLongBits(this.x);
        n = 31 * n + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.y);
        n = 31 * n + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.z);
        n = 31 * n + (int)(l ^ l >>> 32);
        n = 31 * n + (this.yaw != 0.0f ? Float.floatToIntBits(this.yaw) : 0);
        n = 31 * n + (this.pitch != 0.0f ? Float.floatToIntBits(this.pitch) : 0);
        return n;
    }

    public Vector3d toEyeVector(boolean bl) {
        return new Vector3d(this.x, this.y + (bl ? 1.54 : 1.62), this.z);
    }

    public double distanceSquared(PlayerLocation playerLocation) {
        return MathUtil.hypotSquared(this.x - playerLocation.x, this.y - playerLocation.y, this.z - playerLocation.z);
    }

    public double distanceXZSquared(PacketLocation packetLocation) {
        return Math.pow(this.x - packetLocation.x, 2.0) + Math.pow(this.z - packetLocation.z, 2.0);
    }

    public boolean sameDirection(PlayerLocation playerLocation) {
        return this.yaw == playerLocation.yaw && this.pitch == playerLocation.pitch;
    }

    public double distanceXZ(PlayerLocation playerLocation) {
        return MathHelper.sqrt_double(this.distanceXZSquared(playerLocation));
    }

    public boolean sameLocation(PlayerLocation playerLocation) {
        return this.x == playerLocation.x && this.y == playerLocation.y && this.z == playerLocation.z;
    }

    public double getX() {
        return this.x;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float f) {
        this.pitch = f;
    }

    public PlayerLocation add(double d, double d2, double d3) {
        return new PlayerLocation(this.timestamp, this.tickTime, this.x + d, this.y + d2, this.z + d3, this.yaw, this.pitch, this.ground);
    }

    public float getYaw() {
        return this.yaw;
    }

    public PlayerLocation[] interpolateIncluding(PlayerLocation playerLocation, int n) {
        if (this.sameLocationAndDirection(playerLocation)) {
            return new PlayerLocation[]{this, playerLocation};
        }
        long l = (playerLocation.timestamp - this.timestamp) / (long)(++n);
        int n2 = (playerLocation.tickTime - this.tickTime) / n;
        double d = (playerLocation.x - this.x) / (double)n;
        double d2 = (playerLocation.y - this.y) / (double)n;
        double d3 = (playerLocation.z - this.z) / (double)n;
        float f = (playerLocation.yaw - this.yaw) / (float)n;
        float f2 = (playerLocation.pitch - this.pitch) / (float)n;
        boolean bl = playerLocation.ground != false && this.ground != false;
        ArrayList<PlayerLocation> arrayList = new ArrayList<PlayerLocation>(n + 1);
        arrayList.add(this);
        for (int i = 1; i < n; ++i) {
            arrayList.add(new PlayerLocation(this.timestamp + (long)i * l, this.tickTime + i * n2, this.x + (double)i * d, this.y + (double)i * d2, this.z + (double)i * d3, this.yaw + (float)i * f, this.pitch + (float)i * f2, bl));
        }
        arrayList.add(playerLocation);
        return arrayList.toArray(new PlayerLocation[0]);
    }
}

