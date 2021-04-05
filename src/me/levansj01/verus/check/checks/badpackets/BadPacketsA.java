package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "A", friendlyName = "Timer", version = CheckVersion.RELEASE, maxViolations = 5, logData = true)
public class BadPacketsA extends PacketCheck {
    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            int packets = this.playerData.getPackets();
            int pingTicks = this.playerData.getMaxPingTicks();
            if (packets > 20) {
                double vl = packets > 22 ? 2 : 0.2;
                this.handleViolation(debug(packets, pingTicks), vl);
            } else {
                this.decreaseVL(1.0E-5);
            }
        }
    }

    private static String debug(int packets, int pingTicks) {
        return String.format("P: %s | %s", packets, pingTicks);
    }
}
