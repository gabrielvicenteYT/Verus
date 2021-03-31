package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.PlayerLocation;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

@CheckInfo(type=CheckType.FLY, subType="G", friendlyName="Fly", version=CheckVersion.RELEASE, minViolations=-2.5, maxViolations=20, logData=true)
public class FlyG
        extends MovementCheck {
    private boolean ignoring = false;
    private int jump = 0;

    private void handle1(Cuboid cuboid, World world, double d) {
        if (cuboid.checkBlocks(this.player, world, FlyG::isNull)) {
            double d2;
            String string = String.format("D: %s", new Object[]{d});
            if (this.playerData.isTeleporting()) {
                d2 = 0.25;
            } else {
                d2 = Math.min(10.0, 0.5 + d);
            }
            this.handleViolation(string, d2);
        } else {
            this.ignoring = true;
        }
    }

    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        if (this.ignoring) {
            if (playerLocation2.getGround()) {
                this.ignoring = false;
            }
        } else if (!(!(playerLocation2.getY() > playerLocation.getY()) || this.playerData.getVelocityTicks() <= (this.playerData.getPingTicks() + 1) * 2 || this.playerData.isVehicle() || this.playerData.canFly() || this.playerData.isFallFlying() || this.playerData.isGliding() || this.playerData.isRiptiding() || this.playerData.isHooked() || this.playerData.isTeleportingV2() || !this.playerData.isSurvival() || !this.playerData.isSpawned() || this.playerData.isLevitating() || this.playerData.hasPlacedBlock(true) || this.playerData.hasJumpBoostExpired())) {
            double d;
            double d2 = playerLocation2.getY() - Math.max(0.0, playerLocation.getY());
            if (d2 > 100000.0 || this.violations > 300.0) {
                AlertManager.getInstance().handleBan(this.playerData, this, false);
                this.playerData.fuckOff();
            }
            int n = BukkitUtil.getPotionLevel(this.player, PotionEffectType.JUMP);
            double d3 = 0.41999998688699;
            if (!playerLocation2.getGround()) {
                d = d3;
            } else {
                d = 0.5;
            }
            double d4 = Math.max(d, d3 + (double)Math.max(this.jump, n) * 0.2);
            double d5 = d2 - d4;
            if (playerLocation.getGround()) {
                this.jump = n;
            }
            if (this.playerData.getVersion() != ClientVersion.VERSION1_7 && playerLocation2.getGround() && playerLocation.getGround() && (d5 == 0.0625 || d5 == 0.10000002384185791)) {
                return;
            }
            if (d2 > d4 && Math.abs(d2 - 0.5) > 1.0E-12) {
                if (this.playerData.isTeleporting(2) && !this.playerData.isTeleportingV2()) {
                    this.playerData.setLastTransactionID((short)(ThreadLocalRandom.current().nextInt(65532) + Short.MIN_VALUE));
                    this.playerData.setSpawned(Integer.MAX_VALUE);
                }
                World world = this.player.getWorld();
                Cuboid cuboid = new Cuboid(playerLocation).move(0.0, -1.5, 0.0).expand(0.5, 2.0, 0.5);
                this.run(() -> this.handle1(cuboid, world, d5));
            } else {
                this.violations -= Math.min(this.violations + 2.5, 0.025);
            }
        }
    }

    private static boolean isNull(Material material) {
        boolean bl;
        bl = !MaterialList.INVALID_JUMP.contains(material) && !MaterialList.SHULKER_BOX.contains(material);
        return bl;
    }
}