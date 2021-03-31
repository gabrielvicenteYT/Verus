package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import org.bukkit.Material;
import org.bukkit.World;

@CheckInfo(type=CheckType.FLY, subType="D", friendlyName="Fly", version=CheckVersion.RELEASE, minViolations=-5.0, maxViolations=25)
public class FlyD extends PacketCheck {
    private int lastBypassTick = -10;
    private int jumped;
    private Double lastY = null;
    private boolean jumping = false;

    private static boolean lambda$null$0(Material material) {
        boolean bl;
        bl = !MaterialList.BAD_VELOCITY.contains(material);
        return bl;
    }

    private void handle(Cuboid cuboid, World world, Cuboid cuboid2, int n) {
        ClientVersion clientVersion = this.playerData.getVersion();
        ServerVersion serverVersion = NMSManager.getInstance().getServerVersion();
        if (cuboid.checkBlocks(this.player, world, FlyD::lambda$null$0) && (clientVersion.before(ClientVersion.VERSION1_13) || serverVersion.before(ServerVersion.v1_14_R1) || cuboid2.checkBlocks(this.player, world, FlyD::isNull))) {
            this.handleViolation("", this.jumped - 1);
        } else {
            this.jumped = 0;
            this.violations -= Math.min(this.violations + 1.0, 0.05);
            this.lastBypassTick = n;
        }
    }

    private static boolean isNull(Material material) {
        boolean bl;
        bl = !MaterialList.BOBBLE.contains(material);
        return bl;
    }

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            VPacketPlayInFlying vPacketPlayInFlying = (VPacketPlayInFlying)vPacket;
            if (vPacketPlayInFlying.isGround() || this.playerData.getVelocityTicks() <= this.playerData.getPingTicks() * 2 || this.playerData.getTeleportTicks() <= this.playerData.getPingTicks() || this.playerData.isTeleportingV2() || this.playerData.isFlying() || !this.playerData.isSurvival() || this.playerData.getTotalTicks() - 40 <= this.lastBypassTick || this.playerData.hasPlacedBucket() || this.playerData.isFallFlying() || this.playerData.isGliding() || this.playerData.isRiptiding() || this.playerData.isLevitating()) {
                if (MathUtil.onGround(this.playerData.getLocation().getY()) || (this.playerData.getLocation().getY() - (double)0.42f) % 1.0 > 1.0E-15) {
                    this.jumped = 0;
                    this.jumping = false;
                }
            } else if (this.lastY != null) {
                if (this.jumping && vPacketPlayInFlying.getY() < this.lastY) {
                    if (this.jumped++ > 1 && !this.playerData.hasJumpBoostExpired()) {
                        World world = this.player.getWorld();
                        Cuboid cuboid = Cuboid.withLimit(this.playerData.getLastLocation(), this.playerData.getLocation(), 16).add(-0.5, 0.5, -0.5, 2.0, -0.5, 0.5);
                        Cuboid cuboid2 = Cuboid.withLimit(this.playerData.getLastLocation(), this.playerData.getLocation(), 16).add(-0.5, 0.5, -2.0, 0.0, -0.5, 0.5);
                        int n = this.playerData.getTotalTicks();
                        this.run(() -> this.handle(cuboid, world, cuboid2, n));
                    }
                    this.jumping = false;
                } else if (vPacketPlayInFlying.getY() > this.lastY) {
                    this.jumping = true;
                }
            }
            this.violations -= Math.min(this.violations + 5.0, 0.025);
            if (vPacketPlayInFlying.isPos()) {
                this.lastY = vPacketPlayInFlying.getY();
            }
        }
    }
}