package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.PlayerLocation;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.verus2.data.player.TickerType;
import org.bukkit.Material;
import org.bukkit.World;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@CheckInfo(type=CheckType.FLY, subType="F", friendlyName="Fly", version=CheckVersion.RELEASE, minViolations=-5.0, maxViolations=50, logData=true)
public class FlyF
        extends MovementCheck {
    private int threshold;
    private Double lastYChange = null;
    private int lastBypassTick = -10;

    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        if (!(this.playerData.canFly() || !this.playerData.isSurvival() || playerLocation2.getGround().booleanValue() || playerLocation.getGround().booleanValue() || this.playerData.getTotalTicks() - 10 <= this.lastBypassTick || this.playerData.isTeleporting(3) || this.playerData.getTickerMap().get(TickerType.TELEPORT) <= 2 || this.playerData.isVehicle() || !this.playerData.isSpawned() || this.playerData.isLevitating() || this.playerData.isFallFlying() || this.playerData.isGliding())) {
            double d = Math.abs(playerLocation.getY() - playerLocation2.getY());
            if (this.lastYChange != null && d > 0.0 && playerLocation.getY() > 0.0 && playerLocation2.getY() > 0.0 && !this.playerData.hasFast()) {
                if (d == this.lastYChange && (d < 0.098 || d > 0.09800001) && Math.abs(d % 0.5) - 0.15523200451660557 > 1.0E-12 && Math.abs(d % 0.5) - 0.23052736891296366 > 1.0E-12) {
                    World world = this.player.getWorld();
                    Cuboid cuboid = new Cuboid(playerLocation2).add(-0.5, 0.5, -0.5, 1.5, -0.5, 0.5);
                    Cuboid cuboid2 = Cuboid.withLimit(playerLocation, playerLocation2, 16).move(0.0, 2.0, 0.0).expand(0.29999, 0.5, 0.29999);
                    int n = this.playerData.getTotalTicks();
                    if (d == 1.0999999999999943) {
                        this.lastBypassTick = n;
                        return;
                    }
                    this.run(() -> this.handle2(cuboid, world, cuboid2, d, n));
                } else {
                    this.violations -= Math.min(this.violations + 5.0, 0.01);
                    this.threshold = 0;
                }
            }
            this.lastYChange = d;
        }
    }

    private static boolean isNull1(Material material) {
        boolean bl;
        bl = material == MaterialList.AIR;
        return bl;
    }

    private void handle2(Cuboid cuboid, World world, Cuboid cuboid2, double d, int n) {
        if (cuboid.checkBlocks(this.player, world, FlyF::isNull) && cuboid2.checkBlocks(this.player, world, FlyF::isNull1)) {
            if (d % 0.01 == 0.0) {
                ++this.threshold;
            }
            if (this.threshold++ > 1) {
                this.handleViolation(String.format("D: %s", d), (double)this.threshold / 2.0);
            }
        } else {
            this.threshold = 0;
            this.violations -= Math.min(this.violations + 5.0, 0.01);
            this.lastBypassTick = n;
        }
    }

    private static boolean isNull(Material material) {
        boolean bl;
        if (!MaterialList.BAD_VELOCITY.contains(material)) {
            bl = true;
        } else {
            bl = false;
        }
        return bl;
    }
}
