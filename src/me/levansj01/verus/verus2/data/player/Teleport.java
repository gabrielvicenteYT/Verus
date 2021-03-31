package me.levansj01.verus.verus2.data.player;

import java.util.Set;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;
import me.levansj01.verus.verus2.data.player.PacketLocation;

public class Teleport {
    private final double y;
    private final float yaw;
    private final Set<VPacketPlayOutPosition.TeleportFlag> flags;
    private final double z;
    private final double x;
    private final long time;
    private final int ticks;
    private final float pitch;

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getTicks() {
        return this.ticks;
    }

    public Teleport(double d, double d2, double d3, float f, float f2, Set<VPacketPlayOutPosition.TeleportFlag> set,
            int n, long l) {
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.yaw = f;
        this.pitch = f2;
        this.flags = set;
        this.ticks = n;
        this.time = l;
    }

    public float getPitch() {
        return this.pitch;
    }

    public PacketLocation toLocation() {
        return new PacketLocation(this.x, this.y, this.z, this.yaw, this.pitch, false, true, true);
    }

    public long getTime() {
        return this.time;
    }

    public Set<VPacketPlayOutPosition.TeleportFlag> getFlags() {
        return this.flags;
    }

    public boolean matches(VPacketPlayInFlying vPacketPlayInFlying) {
        boolean bl;
        if (!vPacketPlayInFlying.isGround() && vPacketPlayInFlying.isPos() && vPacketPlayInFlying.isLook()
                && vPacketPlayInFlying.getX() == this.x && vPacketPlayInFlying.getY() == this.y
                && vPacketPlayInFlying.getZ() == this.z && vPacketPlayInFlying.getYaw() == this.yaw
                && vPacketPlayInFlying.getPitch() == this.pitch) {
            bl = true;
        } else {
            bl = false;
        }
        return bl;
    }

    public double getZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }
}
