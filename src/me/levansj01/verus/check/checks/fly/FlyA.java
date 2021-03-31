package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.PlayerLocation;
import me.levansj01.verus.verus2.data.player.PacketLocation;
import org.bukkit.Material;
import org.bukkit.World;

@CheckInfo(
        type = CheckType.FLY,
        subType = "A",
        friendlyName = "Fly",
        version = CheckVersion.RELEASE,
        minViolations = -2.5D,
        maxViolations = 50,
        logData = true,
        heavy = true
)
public class FlyA extends PacketCheck {
    private boolean ground;
    private Cuboid cuboid = null;
    private int delay;
    private int threshold;

    public void handle(VPacket var1, long var2) {
        if (var1 instanceof VPacketPlayInFlying) {
            VPacketPlayInFlying var4 = (VPacketPlayInFlying)var1;
            PacketLocation var5 = this.playerData.getCurrentLocation2();
            if (this.ground && var5.isGround() && this.playerData.isSurvival()) {
                World var6 = this.player.getWorld();
                boolean var7 = var4.isPos();
                if (this.cuboid == null || this.delay++ > 9 && var7 && !this.cuboid.containsBlock(var6, var5)) {
                    PlayerLocation var8 = this.playerData.getLastLocation();
                    PlayerLocation var9 = this.playerData.getLocation();
                    this.cuboid = Cuboid.withLimit(var8, var9, 16).move(0.0D, -0.5D, 0.0D).expand(0.4D, 0.5D, 0.4D);
                    this.run(() -> this.handle(var8, var9, var6, var7));
                    this.delay = 0;
                }
            } else {
                this.threshold = 0;
                this.delay = 10;
            }

            this.ground = var5.isGround();
        }

    }

    private void handle(PlayerLocation var1, PlayerLocation var2, World var3, boolean var4) {
        if (this.cuboid == null) {
            this.cuboid = Cuboid.withLimit(var1, var2, 16).move(0.0D, -0.5D, 0.0D).expand(0.4D, 0.5D, 0.4D);
        }

        if (!this.playerData.isTeleporting(5) && this.cuboid.checkBlocks(this.player, var3, FlyA::isFullyPassable)) {
            this.cuboid = null;
            if (this.threshold++ > this.playerData.getMaxPingTicks()) {
                String var10001 = String.format("%s > %s | l=%s p=%s", this.threshold, this.playerData.getMaxPingTicks(), this.playerData.hasLag(), var4);
                double var10002;
                if (!var4 && this.playerData.hasLag()) {
                    var10002 = 0.1D;
                } else {
                    var10002 = 1.0D;
                }

                this.handleViolation(var10001, var10002);
            }

            this.delay = 10;
        } else {
            this.threshold = 0;
            this.decreaseVL(2.5D);
        }
    }

    private static boolean isFullyPassable(Material material) {
        switch (material) {
            case AIR:
            case WATER:
            case STATIONARY_WATER:
            case LAVA:
            case STATIONARY_LAVA: {
                return true;
            }
            default:
                return false;
        }
    }
}
