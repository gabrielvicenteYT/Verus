package me.levansj01.verus.check.checks.velocity;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.PlayerLocation;

@CheckInfo(type=CheckType.VELOCITY, subType="C", friendlyName="NoVelocity", version=CheckVersion.RELEASE, minViolations=-2.0, logData=true)
public class VelocityC
        extends PacketCheck {

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            PlayerLocation playerLocation = this.playerData.getLastLastLocation();
            PlayerLocation playerLocation2 = this.playerData.getLocation();
            if (this.playerData.getVerticalVelocityTicks() > this.playerData.getMoveTicks() - 1 && this.playerData.getLastVelY() > 0.0 && this.playerData.getVerticalVelocityTicks() > this.playerData.getMaxPingTicks()) {
                double d = playerLocation2.getY() - playerLocation.getY();
                if (d > 0.0) {
                    this.decreaseVL(1.0);
                } else {
                    this.handleViolation(String.format("T: %s | %s", this.playerData.getVerticalVelocityTicks(),  this.playerData.getMaxPingTicks()));
                }
                this.playerData.setResetVelocity(true);
            }
        }
    }
}
