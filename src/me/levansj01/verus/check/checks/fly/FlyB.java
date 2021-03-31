package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.PlayerLocation;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;

@CheckInfo(type=CheckType.FLY, subType="B", friendlyName="Fly", version=CheckVersion.RELEASE, minViolations=-5.0, maxViolations=25, logData=true)
public class FlyB
        extends MovementCheck {
    private int lastBypassTick = -10;
    private int threshold;

    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        if (!(!playerLocation.getGround() || !playerLocation2.getGround() || playerLocation.getY() == playerLocation2.getY() || MathUtil.onGround(playerLocation.getY()) || this.playerData.getTotalTicks() <= 200 || MathUtil.onGround(playerLocation2.getY()) || !this.playerData.isSpawned() || this.playerData.getTotalTicks() - 10 <= this.lastBypassTick || this.playerData.canFly() || this.playerData.isFallFlying() || this.playerData.isGliding() || this.playerData.isLevitating() || this.playerData.isFlying() || this.playerData.hasJumpBoostExpired())) {
            World world = this.player.getWorld();
            Cuboid cuboid = new Cuboid(this.playerData.getLocation()).expand(0.5, 0.5, 0.5);
            double d = playerLocation2.getY() - playerLocation.getY();
            int n = this.playerData.getTotalTicks();
            this.run(() -> this.handle(cuboid, world, n, d));
        } else {
            this.threshold = 0;
            this.violations -= Math.min(this.violations + 4.0, 0.05);
        }
    }

    private void handle(Cuboid cuboid, World world, int n, double d) {
        if (cuboid.checkBlocks(this.player, world, FlyB::isNull)) {
            for (Entity entity : this.player.getNearbyEntities(2.5, 2.5, 2.5)) {
                if (entity instanceof Boat || entity instanceof Minecart || entity.getType().name().equalsIgnoreCase("SHULKER")) {
                    this.threshold = 0;
                    this.lastBypassTick = n - 100;
                    this.violations -= Math.min(this.violations + 4.0, 0.05);
                    return;
                }
                throw null;
            }
            ++this.threshold;
            this.handleViolation(String.format("D: %s", d), this.threshold);
        } else {
            this.threshold = 0;
            this.violations -= Math.min(this.violations + 4.0, 0.05);
            this.lastBypassTick = n;
        }
    }

    private static boolean isNull(Material material) {
        boolean bl;
        bl = (NMSManager.getInstance().getServerVersion().before(ServerVersion.v1_11_R1) || material != MaterialList.PURPLE_FUCKING_SHULKER) && !MaterialList.INVALID_SHAPE.contains(material);
        return bl;
    }
}