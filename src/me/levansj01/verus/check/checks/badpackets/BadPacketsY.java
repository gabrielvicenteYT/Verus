package me.levansj01.verus.check.checks.badpackets;


import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import me.levansj01.verus.compat.packets.VPacketPlayInTransaction;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type=CheckType.BAD_PACKETS, subType="Y", friendlyName="Blink", version=CheckVersion.RELEASE, logData=true, minViolations=-2.5, maxViolations=15)
public class BadPacketsY extends PacketCheck {
    private Long lastTickDelay;
    private Long lastKeepAlive = 0L;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInKeepAlive) {
            this.lastKeepAlive = l;
        } else if (vPacket instanceof VPacketPlayInTransaction && this.playerData.shouldHaveReceivedPing() && !this.player.isDead() && !this.playerData.isVehicle() && this.playerData.isSurvival() && this.playerData.getTotalTicks() > 800) {
            long l2 = Math.max(50, Math.max(this.playerData.getTransactionPing(), this.playerData.getPing()));
            long l3 = l - this.playerData.getLastFlying();
            if (this.lastTickDelay != null && this.playerData.isSurvival()) {
                long l4;
                int n;
                long l5 = l3 - this.lastTickDelay;
                long l6 = l - this.lastKeepAlive;
                if (this.playerData.getVersion().after(ClientVersion.VERSION1_8)) {
                    n = 2000;
                } else {
                    n = 500;
                }
                if (l3 > (l4 = (long)n + l2 * 2L) && l5 > Math.max(250L, l2) && l5 < 500L && l6 < 1000L + l2 * 2L) {
                    this.handleViolation(() -> BadPacketsY.lambda$handle$0(l3, l5, l4, l6), 0.5);
                } else {
                    this.decreaseVL(0.05);
                }
            }
            this.lastTickDelay = l3;
        }
    }

    private static String lambda$handle$0(long l, long l2, long l3, long l4) {
        return String.format("T: %s D: %s L: %s O: %s K: %s", l, l2, l3, Math.abs(l - l3), l4);
    }
}
