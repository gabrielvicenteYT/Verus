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

@CheckInfo(type=CheckType.FLY, subType="K", friendlyName="Fly", version=CheckVersion.RELEASE, minViolations=-2.0, maxViolations=10, logData=true)
public class FlyK extends MovementCheck {

    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        double d = playerLocation.getY() - playerLocation2.getY();
        if (playerLocation.getGround() && d > 0.079 && !this.playerData.isFlying() && this.playerData.isSurvival() && !this.playerData.isTeleporting(2) && this.playerData.getTickerMap().get(TickerType.TELEPORT) > 1 && this.playerData.getVelocityTicks() > this.playerData.getPingTicks() / 2 && this.playerData.isSpawned() && !this.playerData.hasLag() && !this.playerData.isFallFlying() && !this.playerData.isGliding() && Math.abs(playerLocation.getY() % 0.5 - 0.015555072702202466) > 1.0E-12 && !this.playerData.hasPlacedBlock(true)) {
            World world = this.player.getWorld();
            Cuboid cuboid = new Cuboid(this.playerData.getLocation()).add(-0.5, 0.5, -1.0, 1.5, -0.5, 0.5);
            this.run(() -> this.handle(cuboid, world, d, playerLocation2));
        } else {
            this.decreaseVL(0.005);
        }
    }

    private static boolean isNull(Material material) {
        boolean bl;
        bl = !MaterialList.BAD_VELOCITY.contains(material) && !MaterialList.INVALID_SHAPE.contains(material);
        return bl;
    }

    private void handle(Cuboid cuboid, World world, double d, PlayerLocation playerLocation) {
        if (cuboid.checkBlocks(this.player, world, FlyK::isNull)) {
            this.handleViolation(String.format("D: %s G: %s", d, playerLocation.getGround()));
        }
    }
}