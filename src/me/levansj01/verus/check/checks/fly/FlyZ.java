package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.PlayerLocation;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathHelper;
import me.levansj01.verus.util.java.MathUtil;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

@CheckInfo(type=CheckType.FLY, subType="Z", friendlyName="Elytra Fly", version=CheckVersion.RELEASE, minViolations=-7.5, unsupportedVersions={ClientVersion.VERSION1_7, ClientVersion.VERSION1_8}, unsupportedServers={ServerVersion.v1_7_R4, ServerVersion.v1_8_R3})
public class FlyZ extends MovementCheck {
    private int lastBypass;
    private static final float VALUE = (float)Math.PI / 180;
    private int ticks = 0;
    private Vector lastMovement = null;

    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        if (!(!this.playerData.isFallFlying() || this.playerData.canFly() || this.playerData.isBoosting() || playerLocation2.getGround() || this.playerData.isLevitating() || this.playerData.getVelocityTicks() <= this.playerData.getMaxPingTicks() || playerLocation.getGround() || this.playerData.getTotalTicks() - this.lastBypass <= 20 || !NMSManager.getInstance().getServerVersion().before(ServerVersion.v1_14_R1) && BukkitUtil.hasEffect(this.player, 28))) {
            if (this.ticks++ > 9) {
                float f = playerLocation2.getPitch();
                float f2 = playerLocation2.getYaw();
                float f3 = f * ((float)Math.PI / 180);
                double d = playerLocation2.getY() - playerLocation.getY();
                double d2 = this.lastMovement.getY();
                double d3 = this.lastMovement.length();
                Vector vector = FlyZ.getVectorForRotation(f, f2);
                double d4 = vector.length();
                double d5 = Math.sqrt(Math.pow(vector.getX(), 2.0) + Math.pow(vector.getZ(), 2.0));
                float f4 = (float)(Math.pow(MathHelper.cos(f3), 2.0) * Math.min(1.0, d4 / 0.4));
                double d6 = d / (double)0.99f;
                double d7 = (d2 - 0.08) * (double)0.98f;
                if (f3 < 0.0f) {
                    d6 -= d3 * (double)(-MathHelper.sin(f3)) * 0.04 * 3.2;
                }
                if (d2 < 0.0 && d5 > 0.0) {
                    d6 -= d2 * -0.1 * (double)f4;
                }
                double d8 = MathUtil.lowestAbs(d2 - (d6 -= -0.08 + (double)f4 * 0.06), d - d7);
                double d9 = MathUtil.lowestAbs((d6 - d2) / d6, (d - d7) / d7);
                if (Math.abs(d8) > 0.025 && Math.abs(d9) > 0.075) {
                    World world = this.player.getWorld();
                    Cuboid cuboid = new Cuboid(playerLocation2).expand(0.5, 1.0, 0.5).move(0.0, 1.0, 0.0);
                    int n = this.playerData.getTotalTicks();
                    this.run(() -> this.handle(cuboid, world, d, d8, n));
                } else {
                    this.decreaseVL(0.1);
                }
            }
            this.lastMovement = new Vector(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getY() - playerLocation.getY(), playerLocation2.getZ() - playerLocation.getZ());
        } else {
            this.ticks = 0;
        }
    }

    private static boolean isNull(Material material) {
        boolean bl;
        bl = !MaterialList.BAD_VELOCITY.contains(material);
        return bl;
    }

    private static Vector getVectorForRotation(float f, float f2) {
        float f3 = MathHelper.cos(-f2 * ((float)Math.PI / 180) - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * ((float)Math.PI / 180) - (float)Math.PI);
        float f5 = -MathHelper.cos(-f * ((float)Math.PI / 180));
        float f6 = MathHelper.sin(-f * ((float)Math.PI / 180));
        return new Vector(f4 * f5, f6, f3 * f5);
    }

    private void handle(Cuboid cuboid, World world, double d, double d2, int n) {
        if (this.playerData.getTotalTicks() - this.lastBypass < 20) {
            return;
        }
        if (cuboid.checkBlocks(this.player, world, FlyZ::isNull)) {
            this.handleViolation(String.format("Y: %.2f DIFF: %.2f", d, d2));
        } else {
            this.lastBypass = n;
            this.ticks = 0;
        }
    }
}
