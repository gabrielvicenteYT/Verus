package me.levansj01.verus.type.premium.checks.speed;


import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.PlayerLocation;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import org.bukkit.Material;
import org.bukkit.World;

@CheckInfo(type=CheckType.SPEED, subType="E", friendlyName="OmniSprint", version=CheckVersion.RELEASE, minViolations=-1.0, maxViolations=20, logData=true)
public class SpeedE extends MovementCheck {
    private Double lastAngle = null;
    private int threshold = 0;

    private void lambda$handle$1(Cuboid cuboid, World world, double d, double d2) {
        if (cuboid.checkBlocks(this.player, world, SpeedE::isNull)) {
            this.handleViolation(String.format("D: %s LD: %s", d, d2));
        } else {
            this.threshold = -20;
        }
    }

    private static boolean isNull(Material material) {
        boolean bl;
        bl = !MaterialList.BAD_VELOCITY.contains(material);
        return bl;
    }

    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        if (this.playerData.isSprinting(true) && playerLocation2.getGround() && playerLocation.getGround() && !this.playerData.hasLag() && !this.playerData.isTeleporting(3) && this.playerData.getVelocityTicks() > this.playerData.getPingTicks()) {
            double d = Math.toDegrees(-Math.atan2(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ()));
            double d2 = Math.min(MathUtil.getDistanceBetweenAngles360(d, playerLocation2.getYaw()), MathUtil.getDistanceBetweenAngles360(d, playerLocation.getYaw()));
            if (this.lastAngle != null) {
                double d3 = MathUtil.getDistanceBetweenAngles360(this.lastAngle, d2);
                if (d2 > 50.0) {
                    if (d3 < 5.0 && this.threshold++ > 4) {
                        World world = this.player.getWorld();
                        Cuboid cuboid = new Cuboid(this.playerData.getLocation()).expand(0.5, 0.5, 0.5);
                        this.run(() -> this.lambda$handle$1(cuboid, world, d2, d3));
                        this.threshold = 0;
                    }
                } else {
                    this.threshold = 0;
                }
            }
            this.lastAngle = d2;
        } else {
            this.lastAngle = null;
            this.threshold = 0;
        }
    }
}