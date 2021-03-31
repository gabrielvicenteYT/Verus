package me.levansj01.verus.check.checks.fly;

import java.util.function.Supplier;
import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.PingHandler;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.PlayerLocation;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.verus2.data.player.TickerType;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(type=CheckType.FLY, subType="I", friendlyName="Fly", version=CheckVersion.RELEASE, minViolations=-2.5, maxViolations=50, logData=true)
public class FlyI
        extends MovementCheck
        implements PingHandler {
    private int lastBypassTick;
    private int lastTPTick;
    private Double lastYDiff = null;
    private double threshold;

    private static boolean isNull(Material material) {
        boolean bl;
        bl = !MaterialList.BAD_VELOCITY.contains(material) && !MaterialList.INVALID_SHAPE.contains(material);
        return bl;
    }

    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        if (this.playerData.getTickerMap().get(TickerType.TELEPORT) <= 1) {
            this.lastYDiff = null;
            this.threshold = 0.0;
            return;
        }
        if (!playerLocation.getGround() || !playerLocation2.getGround()) {
            double d = playerLocation2.getY() - playerLocation.getY();
            ClientVersion clientVersion = this.playerData.getVersion();
            if (this.lastYDiff != null && this.playerData.getTotalTicks() - 40 > this.lastBypassTick && !this.playerData.hasPlacedBucket() && !this.playerData.hasPlacedBlock(true) && !this.playerData.isFallFlying() && !this.playerData.isGliding() && !this.playerData.canFly() && this.playerData.isSpawned() && this.playerData.getVelocityTicks() > (2 + this.playerData.getMaxPingTicks()) * 2 && !this.playerData.isLevitating() && !this.playerData.isRiptiding() && !this.playerData.hasJumpBoostExpired() && (NMSManager.getInstance().getServerVersion().before(ServerVersion.v1_14_R1) || playerLocation2.getY() < playerLocation.getY() && !BukkitUtil.hasEffect(this.player, 28)) && Math.abs(d / (double)0.98f + 0.08) > 1.0E-11 && Math.abs(d + (double)0.98f) > 1.0E-11 && Math.abs(d + 0.09800000190735147) > 1.0E-11 && Math.abs(d - 0.019999999105930755) > 1.0E-9 && Math.abs(d - 0.0030162615090425504) > 1.0E-9 && (clientVersion.before(ClientVersion.VERSION1_9) || Math.abs(d + 0.15233518685055714) > 1.0E-11 && Math.abs(d + 0.07242780368044421) > 1.0E-11) && (clientVersion.before(ClientVersion.VERSION1_13) || Math.max(playerLocation2.getY(), playerLocation.getY()) < 255.0)) {
                boolean bl;
                bl = playerLocation.getX() != playerLocation2.getX() && playerLocation.getZ() != playerLocation2.getZ();
                boolean bl2 = bl;
                double d2 = (this.lastYDiff - 0.08) * (double)0.98f;
                if (playerLocation2.getGround() && d < 0.0 && d2 < d && MathUtil.onGround(playerLocation2.getY()) || playerLocation.distanceXZSquared(playerLocation2) < 0.0025 && this.player.hasPotionEffect(PotionEffectType.JUMP)) {
                    d2 = d;
                } else if ((VerusTypeLoader.isDev() || this.playerData.getVersion() != ClientVersion.VERSION1_9 && !this.player.hasPotionEffect(PotionEffectType.JUMP)) && Math.abs(d2) < 0.005) {
                    d2 = 0.0;
                }
                double d3 = Math.abs(d2 - d);
                double d4 = (d2 - d) / d2;
                int n = this.playerData.getTotalTicks();
                if (d3 > 2.0 && Math.abs(d4) > 300.0) {
                    double d5;
                    String string = String.format("%s %s %.3f, %.3f%s", this.playerData.getTeleportTicks(), playerLocation.getGround(), d3, d4 * 100.0, "%");
                    if (n - this.lastTPTick <= 20 && n - this.lastTPTick > 1) {
                        d5 = 1.0;
                    } else {
                        d5 = 0.5;
                    }
                    this.handleViolation(string, d5);
                    this.lastTPTick = n;
                }
                if (!this.playerData.isTeleporting() && !playerLocation.getGround()) {
                    if (d3 > 1.0E-7) {
                        double d6;
                        World world = this.player.getWorld();
                        Cuboid cuboid = new Cuboid(playerLocation2).add(-0.5, 0.5, -1.0, 1.5, -0.5, 0.5);
                        if (VerusTypeLoader.isDev()) {
                            d6 = 0.29999;
                        } else {
                            d6 = 0.5;
                        }
                        double d7 = d6;
                        double d8 = this.playerData.getLocation().getY();
                        Cuboid cuboid2 = Cuboid.withLimit(playerLocation, playerLocation2, 16).move(0.0, 2.0, 0.0).add(-d7, d7, -0.5, 0.5, -d7, d7);
                        this.run(() -> this.handle3(cuboid, world, cuboid2, n, bl2, d3, d, playerLocation2));
                    } else {
                        this.decreaseVL(0.025);
                        this.threshold = 0.0;
                    }
                }
            }
        }
        if (!playerLocation2.getGround() || !playerLocation.getGround()) {
            this.lastYDiff = playerLocation2.getY() - playerLocation.getY();
        } else {
            this.lastYDiff = null;
        }
    }

    private static boolean lambda$null$1(Material material) {
        boolean bl;
        bl = material == MaterialList.AIR;
        return bl;
    }

    public void handleTransaction(long l, long l2) {
    }

    private void handle3(Cuboid cuboid, World world, Cuboid cuboid2, int n, boolean bl, double d, double d2, PlayerLocation playerLocation) {
        if (cuboid.checkBlocks(this.player, world, FlyI::isNull) && cuboid2.checkBlocks(this.player, world, FlyI::lambda$null$1)) {
            double d3;
            boolean bl2;
            for (Entity entity : this.player.getNearbyEntities(2.5, 2.5, 2.5)) {
                if (entity instanceof Boat || entity instanceof Minecart) {
                    this.threshold = 0.0;
                    this.lastBypassTick = n - 100;
                    return;
                }
            }
            this.threshold += 1.0;
            bl2 = this.playerData.hasLag() || this.playerData.hasFast() || !bl;
            boolean bl3 = bl2;
            Supplier supplier = () -> FlyI.lambda$null$2(d, d2, playerLocation, bl);
            if (bl3) {
                d3 = 0.1;
            } else {
                d3 = 1.0;
            }
            this.handleViolation(supplier, d3 * this.threshold);
        } else {
            this.decreaseVL(0.1);
            this.lastBypassTick = n;
            this.threshold = 0.0;
        }
    }

    private static String lambda$null$2(double d, double d2, PlayerLocation playerLocation, boolean bl) {
        return String.format("D: %s D2: %s P: %s V: %s", d, d2, playerLocation.getY() % 1.0, bl);
    }

    public void handleKeepAlive(long l, long l2) {
    }
}