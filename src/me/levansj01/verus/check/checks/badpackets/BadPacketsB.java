package me.levansj01.verus.check.checks.badpackets;


import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(type=CheckType.BAD_PACKETS, subType="B", friendlyName="Invalid Direction", version=CheckVersion.RELEASE, maxViolations=1)
public class BadPacketsB extends PacketCheck {
    public void handle(VPacket vPacket, long l) {
        float f;
        if (vPacket instanceof VPacketPlayInFlying && !this.playerData.isTeleportingV2() && !this.playerData.isTeleporting() && Math.abs(f = ((VPacketPlayInFlying)vPacket).getPitch()) > 90.0f) {
            this.handleViolation(() -> BadPacketsB.lambda$handle$0(f));
        }
    }

    private static String lambda$handle$0(float f) {
        return String.format("P: %.2f", f);
    }
}
