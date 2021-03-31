package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.task.ServerTickTask;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.Material;
import org.bukkit.World;

@CheckInfo(type=CheckType.FLY, subType="C", friendlyName="Fly", version=CheckVersion.RELEASE, minViolations=-1.0, maxViolations=50)
public class FlyC
        extends PacketCheck {
    private int lastBypassTick = -10;
    private Double lastY = null;
    private int threshold;

    private void handle(Cuboid cuboid, World world, boolean bl, double d, int n) {
        if (cuboid.checkBlocks(this.player, world, FlyC::isNull)) {
            int n2;
            if (bl) {
                n2 = 4;
            } else {
                n2 = 1;
            }
            if (this.threshold++ > n2) {
                if (bl) {
                    this.threshold = 0;
                }
                this.handleViolation(String.format("Y: %.2f", d), this.threshold - 1);
            }
        } else {
            this.threshold = 0;
            this.decreaseVL(0.01);
            this.lastBypassTick = n;
        }
    }

    private void handle2() {
        this.threshold = 0;
        this.decreaseVL(0.01);
    }

    private static boolean isNull(Material material) {
        boolean bl;
        bl = !MaterialList.BAD_VELOCITY.contains(material);
        return bl;
    }

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            VPacketPlayInFlying vPacketPlayInFlying = (VPacketPlayInFlying)vPacket;
            if (this.lastY != null) {
                double d = 0;
                double d2;
                if (vPacketPlayInFlying.isPos()) {
                    d2 = vPacketPlayInFlying.getY();
                } else {
                    d2 = d = this.lastY;
                }
                if (!(this.lastY != d || !(d > 0.0) || this.playerData.isVehicle() || vPacketPlayInFlying.isGround() || this.playerData.canFly() || !this.playerData.isSurvival() || this.playerData.isLevitating() || this.playerData.isFallFlying() || this.playerData.isGliding() || this.playerData.isTeleporting() || this.playerData.isTeleportingV2() || this.playerData.hasPlacedBucket() || this.playerData.hasPlacedBlock(true) || this.playerData.getTotalTicks() - 20 <= this.lastBypassTick || this.playerData.getVelocityTicks() <= this.playerData.getMaxPingTicks() || !this.playerData.isSpawned() || ServerTickTask.getInstance().isLagging(l))) {
                    boolean bl;
                    World world = this.player.getWorld();
                    Cuboid cuboid = new Cuboid(this.playerData.getLocation()).add(-0.5, 0.5, 0.0, 2.0, -0.5, 0.5);
                    int n = this.playerData.getTotalTicks();
                    bl = this.playerData.hasLag() || this.playerData.hasFast();
                    boolean bl2 = bl;
                    double finalD = d;
                    this.run(() -> this.handle(cuboid, world, bl2, finalD, n));
                } else {
                    this.run(this::handle2);
                }
            }
            if (vPacketPlayInFlying.isPos()) {
                this.lastY = vPacketPlayInFlying.getY();
            }
        }
    }
}
