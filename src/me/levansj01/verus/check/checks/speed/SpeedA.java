package me.levansj01.verus.check.checks.speed;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.PlayerLocation;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.AtomicDouble;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.verus2.data.player.Velocity;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

import java.util.Comparator;

@CheckInfo(type=CheckType.SPEED, subType="A", friendlyName="Speed", version=CheckVersion.RELEASE, minViolations=-5.0, maxViolations=30, logData=true)
public class SpeedA
        extends MovementCheck {
    private int bypassFallbackTicks;
    public static final double SPEED_POTION_AIR_FAST = 0.0375;
    public static final double NORMAL_AIR = 0.36;
    private int lastSpeed;
    public static final double SPEED_POTION_AIR = 0.0175;
    public static final double FAST_AIR = 0.6125;
    public static final double NON_SPRINT = 0.2325;
    private int lastAirTick;
    public static final double AIR_SLOWDOWN_TICK = 0.125;
    public static final double SPEED_CUBIC_AMOUNT = 0.0018;
    public static final double STRAIGHT_NON_SPRINT = 0.217;
    private int lastGroundTick;
    public static final double SPEED_POTION_GROUND = 0.0573;
    private int lastBypassTick = -50;
    private int lastFastAirTick;
    public static final double STRAIGHT_SPRINT = 0.281;
    private int lastBlockAboveTick = -20;
    public static final double SPRINT = 0.2865;

    private static boolean isNull2(Material material) {
        boolean bl;
        bl = !(MaterialList.ICE.contains(material) || MaterialList.SLABS.contains(material) || MaterialList.STAIRS.contains(material) || material == MaterialList.SLIME_BLOCK);
        return bl;
    }

    private static String isNull3(double d, AtomicDouble atomicDouble, PlayerLocation playerLocation, boolean bl, Velocity velocity, int n, int n2) {
        double d2;
        Object[] objectArray = new Object[6];
        objectArray[0] = d;
        objectArray[1] = atomicDouble.get();
        objectArray[2] = playerLocation.getGround();
        objectArray[3] = bl;
        if (velocity == null) {
            d2 = 0.0;
        } else {
            d2 = Math.sqrt(velocity.getHypotSquaredXZ());
        }
        objectArray[4] = d2;
        objectArray[5] = n - n2;
        return String.format("DST: %.3f LM %.3f G: %s L: %s V: %s FA: %s", objectArray);
    }

    private static boolean lambda$null$0(Material material) {
        boolean bl;
        bl = !MaterialList.LIQUIDS.contains(material);
        return bl;
    }

    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        if (!(playerLocation2.getX() == playerLocation.getX() && playerLocation2.getZ() == playerLocation.getZ() || this.playerData.isTeleportingV2() || this.playerData.isVehicle() || this.playerData.isGliding() || this.playerData.isFallFlying() || this.playerData.isHooked() || this.playerData.isRiptiding())) {
            if (this.playerData.canFly() || this.playerData.isFlying()) {
                if (this.playerData.isFlying()) {
                    this.bypassFallbackTicks = 20;
                }
                return;
            }
            double d = MathUtil.hypot(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ());
            AtomicDouble atomicDouble = new AtomicDouble(0.0);
            int n = Math.max(BukkitUtil.getPotionLevel(this.player, PotionEffectType.SPEED), 0);
            if (this.lastSpeed > n) {
                n = this.lastSpeed - 1;
            }
            if (playerLocation2.getGround()) {
                double d2;
                boolean bl;
                if (this.bypassFallbackTicks > 0) {
                    this.bypassFallbackTicks -= 10;
                }
                this.lastGroundTick = this.playerData.getTotalTicks();
                double d3 = Math.toDegrees(-Math.atan2(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ()));
                double d4 = MathUtil.getDistanceBetweenAngles360(d3, playerLocation2.getYaw());
                bl = d4 < 5.0 && d4 < 90.0;
                boolean bl2 = bl;
                atomicDouble.addAndGet((double)n * 0.0573);
                if (bl2) {
                    d2 = 0.281;
                } else {
                    d2 = 0.2865;
                }
                atomicDouble.addAndGet(d2);
                if (this.playerData.getMovementSpeed() > 0.2) {
                    atomicDouble.set(atomicDouble.get() * this.playerData.getMovementSpeed() / 0.2);
                }
                if (this.lastAirTick >= this.lastGroundTick - 10) {
                    atomicDouble.addAndGet((double)(this.lastGroundTick - this.lastAirTick) * 0.125);
                }
            } else {
                double d5;
                if (this.bypassFallbackTicks > 0) {
                    atomicDouble.addAndGet(0.1);
                    --this.bypassFallbackTicks;
                }
                this.lastAirTick = this.playerData.getTotalTicks();
                boolean bl = false;
                if (d > 0.36 && this.lastFastAirTick < this.lastGroundTick) {
                    this.lastFastAirTick = this.playerData.getTotalTicks();
                    atomicDouble.addAndGet(0.6125);
                    bl = true;
                } else {
                    atomicDouble.addAndGet(0.36);
                }
                if (this.playerData.isFallFlying()) {
                    this.bypassFallbackTicks = 100;
                    atomicDouble.set(atomicDouble.get() * 5.0);
                }
                double d6 = n;
                if (this.lastAirTick - this.lastGroundTick < 1 + (n - 1) / 2) {
                    d6 += (double)(n * n * n) * 0.0018;
                }
                if (bl) {
                    d5 = 0.0375;
                } else {
                    d5 = 0.0175;
                }
                atomicDouble.addAndGet(d6 * d5);
                if (this.playerData.getMovementSpeed() > 0.2) {
                    atomicDouble.addAndGet(atomicDouble.get() * (this.playerData.getMovementSpeed() - 0.2) * 2.0);
                }
            }
            this.lastSpeed = n;
            if (!VerusTypeLoader.isDev() && this.playerData.getHorizontalSpeedTicks() < (this.playerData.getPingTicks() + 10) * 2) {
                return;
            }
            boolean bl = this.playerData.hasLag();
            Velocity velocity = this.playerData.getVelocityQueue().stream().max(Comparator.comparingDouble(Velocity::getHypotSquaredXZ)).orElse(null);
            if (velocity != null) {
                atomicDouble.addAndGet(Math.sqrt(velocity.getHypotSquaredXZ()));
            }
            if (NMSManager.getInstance().getServerVersion().afterOrEq(ServerVersion.v1_14_R1) && BukkitUtil.hasEffect(this.player, 30)) {
                atomicDouble.set(atomicDouble.get() * 2.0);
            }
            if (d > atomicDouble.get()) {
                World world = this.player.getWorld();
                PlayerLocation playerLocation3 = this.playerData.getLocation();
                Cuboid cuboid = new Cuboid(playerLocation3).move(0.0, 2.0, 0.0).expand(0.5, 0.5, 0.5);
                Cuboid cuboid2 = new Cuboid(playerLocation3).add(-0.5, 0.5, -1.99, 0.5, -0.5, 0.5);
                int n2 = this.playerData.getTotalTicks();
                int n3 = this.lastFastAirTick;
                this.run(() -> this.handle4(cuboid2, world, atomicDouble, n2, cuboid, bl, playerLocation2, d, velocity, n3));
            } else {
                this.decreaseVL(0.02);
            }
        }
    }

    private static boolean isNull(Material material) {
        boolean bl;
        bl = material == MaterialList.AIR;
        return bl;
    }

    private void handle4(Cuboid cuboid, World world, AtomicDouble atomicDouble, int n, Cuboid cuboid2, boolean bl, PlayerLocation playerLocation, double d, Velocity velocity, int n2) {
        int n3;
        boolean bl2;
        boolean bl3;
        int n4;
        ServerVersion serverVersion = NMSManager.getInstance().getServerVersion();
        if (serverVersion.after(ServerVersion.v1_7_R4)) {
            PlayerInventory playerInventory = this.player.getInventory();
            if (BukkitUtil.hasEnchantment(playerInventory.getBoots(), "DEPTH_STRIDER") && !cuboid.checkBlocks(this.player, world, SpeedA::lambda$null$0)) {
                return;
            }
            if (serverVersion.afterOrEq(ServerVersion.v1_16_R1) && (n4 = BukkitUtil.getEnchantment(playerInventory.getBoots(), "SOUL_SPEED")) > 0) {
                atomicDouble.addAndGet(atomicDouble.get() * (0.9 + (double)n4 * 0.125));
            }
        }
        bl3 = n - 20 < this.lastBlockAboveTick;
        if (!(bl2 = bl3) && !cuboid2.checkBlocks(this.player, world, SpeedA::isNull)) {
            bl2 = true;
            this.lastBlockAboveTick = n;
        }
        if (n - 60 < this.lastBypassTick) {
            n3 = 1;
        } else {
            n3 = 0;
        }
        if ((n4 = n3) == 0 && !cuboid.checkBlocks(this.player, world, SpeedA::isNull2)) {
            n4 = 1;
            this.lastBypassTick = n;
        }
        if (bl2) {
            int n5;
            if (bl) {
                n5 = 2;
            } else {
                n5 = 1;
            }
            atomicDouble.addAndGet(0.2 * (double)n5);
        }
        if (n4 != 0) {
            int n6;
            double d2;
            if (playerLocation.getGround()) {
                d2 = 0.2;
            } else {
                d2 = 0.3;
            }
            if (bl) {
                n6 = 2;
            } else {
                n6 = 1;
            }
            atomicDouble.addAndGet(d2 * (double)n6);
            this.bypassFallbackTicks = 60;
        }
        if (d > atomicDouble.get()) {
            double d3;
            if (bl) {
                d3 = (d - atomicDouble.get()) * 0.5 + 0.1;
            } else {
                d3 = d - atomicDouble.get();
            }
            double d4 = d3 + 0.3;
            if (this.playerData.isTeleporting()) {
                d4 = 0.15;
            }
            this.handleViolation(() -> SpeedA.isNull3(d, atomicDouble, playerLocation, bl, velocity, n, n2), d4);
        } else {
            this.decreaseVL(0.02);
        }
    }
}