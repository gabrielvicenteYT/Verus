package me.levansj01.verus.check.checks.speed;


import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.MutableBlockLocation;
import me.levansj01.verus.util.PlayerLocation;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.Material;
import org.bukkit.World;

@CheckInfo(type=CheckType.SPEED, subType="B", friendlyName="FastLadder", version=CheckVersion.RELEASE, maxViolations=25, logData=true)
public class SpeedB
        extends MovementCheck {
    private int threshold;
    private double lastDiff;
    private int lastLadderCheck;
    private boolean ladder = false;

    private void lambda$handle$0(PlayerLocation playerLocation, World world, double d) {
        MutableBlockLocation mutableBlockLocation = new MutableBlockLocation((int) Math.floor(playerLocation.getX()), (int) Math.floor(playerLocation.getY() + 1.0), (int) Math.floor(playerLocation.getZ()));
        Material material = NMSManager.getInstance().getType(this.player, world, mutableBlockLocation);
        if (MaterialList.CLIMBABLE.contains(material)) {
            this.handleViolation(String.format("D: %s", d));
        } else {
            this.ladder = false;
        }
    }

    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        if (playerLocation2.getY() > playerLocation.getY() && !playerLocation2.getGround() && !playerLocation.getGround() && !this.playerData.isFlying() && this.playerData.getVelocityTicks() > this.playerData.getPingTicks() * 2) {
            if (this.ladder) {
                double d = playerLocation2.getY() - playerLocation.getY();
                if (d > 0.118) {
                    if (this.threshold++ > 1 && d >= this.lastDiff * 0.95) {
                        this.threshold = 0;
                        World world = this.player.getWorld();
                        this.run(() -> this.lambda$handle$0(playerLocation2, world, d));
                    }
                } else {
                    this.threshold = 0;
                }
                this.lastDiff = d;
            } else if (this.lastLadderCheck++ > 9) {
                this.lastLadderCheck = 0;
                World world = this.player.getWorld();
                this.run(() -> this.handle(playerLocation2, world));
            }
        } else {
            this.ladder = false;
        }
    }

    public SpeedB() {
        super(CheckType.SPEED, "B", "FastLadder", CheckVersion.RELEASE);
        this.setMaxViolation(25);
    }

    private void handle(PlayerLocation playerLocation, World world) {
        MutableBlockLocation mutableBlockLocation = new MutableBlockLocation((int) Math.floor(playerLocation.getX()), (int) Math.floor(playerLocation.getY() + 1.0), (int) Math.floor(playerLocation.getZ()));
        Material material = NMSManager.getInstance().getType(this.player, world, mutableBlockLocation);
        if (MaterialList.CLIMBABLE.contains(material)) {
            this.ladder = true;
        }
    }
}
