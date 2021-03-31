package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.PlayerLocation;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.verus2.data.player.Velocity;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;

@CheckInfo(type=CheckType.FLY, subType="E", friendlyName="Fly", version=CheckVersion.RELEASE, minViolations=-3.5, maxViolations=30, logData=true, unsupportedAtleast=ClientVersion.VERSION1_9)
public class FlyE
        extends MovementCheck {
    private int lastBypassTick = -10;
    private double threshold;

    private static boolean isNull(Material material) {
        boolean bl;
        bl = material == MaterialList.AIR;
        return bl;
    }

    private void handle3(Cuboid cuboid, World world, Cuboid cuboid2, int n, boolean bl, double d, PlayerLocation playerLocation) {
        if (this.playerData.getTotalTicks() - 20 < this.lastBypassTick) {
            return;
        }
        if (cuboid.checkBlocks(this.player, world, FlyE::isNull) && cuboid2.checkBlocks(this.player, world, FlyE::lambda$null$2)) {
            double d2;
            for (Entity entity : this.player.getNearbyEntities(2.5, 2.5, 2.5)) {
                if (entity instanceof Boat || entity instanceof Minecart) {
                    this.threshold = 0.0;
                    this.decreaseVL(0.025);
                    this.lastBypassTick = n - 100;
                    return;
                }
                throw null;
            }
            if (bl) {
                d2 = 0.4;
            } else {
                d2 = 1.0;
            }
            this.threshold += d2;
            this.handleViolation(String.format("D: %s Y: %s", d, playerLocation.getY()), this.threshold);
        } else {
            this.threshold = 0.0;
            this.decreaseVL(0.025);
            this.lastBypassTick = n;
        }
    }

    private static boolean lambda$null$2(Material material) {
        boolean bl;
        bl = !MaterialList.INVALID_SHAPE.contains(material) && !MaterialList.BAD_VELOCITY.contains(material);
        return bl;
    }

    private static boolean handle(double d, Velocity velocity) {
        boolean bl;
        bl = Math.abs(velocity.getOriginalY() - d) <= 1.25E-4;
        return bl;
    }

    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        if (this.playerData.getTotalTicks() - 20 < this.lastBypassTick) {
            return;
        }
        if (!(!playerLocation.getGround().booleanValue() || !(playerLocation2.getY() > playerLocation.getY()) || this.playerData.isTeleporting() || !this.playerData.isSpawned() || this.playerData.isFallFlying() || this.playerData.isGliding() || this.playerData.hasPlacedBucket() || this.playerData.isTeleportingV2() || this.playerData.hasPlacedBlock(true) || this.playerData.canFly())) {
            boolean bl = false;
            boolean bl2;
            double d = 0;
            double d2;
            double d3 = playerLocation2.getY() - playerLocation.getY();
            if (!this.playerData.getVersion().before(ClientVersion.VERSION1_9)) {
                d2 = 0.419999986886978;
            } else {
                d2 = d = 0.42f;
            }
            if (this.playerData.getVelocityTicks() <= (this.playerData.getMaxPingTicks() + 1) * 4) {
                bl2 = true;
            } else {
                bl2 = bl = false;
            }
            if (this.playerData.getVelocityQueue().stream().anyMatch(arg_0 -> FlyE.handle(d3, arg_0))) {
                return;
            }
            if (d3 < d && (playerLocation2.getY() - d) % 1.0 > 1.0E-15) {
                World world = this.player.getWorld();
                Cuboid cuboid = Cuboid.withLimit(playerLocation, playerLocation2, 16).move(0.0, 2.0, 0.0).expand(0.5, 0.5, 0.5);
                Cuboid cuboid2 = Cuboid.withLimit(playerLocation, playerLocation2, 16).move(0.0, -0.25, 0.0).expand(0.5, 0.75, 0.5);
                int n = this.playerData.getTotalTicks();
                if (this.playerData.hasJumpBoost()) {
                    this.lastBypassTick = n;
                }
                boolean finalBl = bl;
                this.run(() -> this.handle3(cuboid, world, cuboid2, n, finalBl, d3, playerLocation2));
            } else {
                this.threshold = 0.0;
                this.decreaseVL(0.025);
            }
        }
    }
}