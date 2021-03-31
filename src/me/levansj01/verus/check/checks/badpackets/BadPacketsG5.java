package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;

@CheckInfo(type=CheckType.BAD_PACKETS, subType="G5", friendlyName="Ping Spoof", version=CheckVersion.RELEASE, minViolations=-1.0, maxViolations=50)
public class BadPacketsG5 extends PacketCheck {
    private long lastKeepAlive;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInKeepAlive) {
            VPacketPlayInKeepAlive vPacketPlayInKeepAlive = (VPacketPlayInKeepAlive)vPacket;
            if (this.playerData.shouldHaveReceivedPing() && this.lastKeepAlive > vPacketPlayInKeepAlive.getId() && this.playerData.getTotalTicks() > 160) {
                long l2 = this.lastKeepAlive - vPacketPlayInKeepAlive.getId();
                this.handleViolation(() -> BadPacketsG5.handle(l2));
            }
            this.lastKeepAlive = vPacketPlayInKeepAlive.getId();
        }
    }

    private static String handle(long l) {
        return String.format("D: %s", l);
    }
}
