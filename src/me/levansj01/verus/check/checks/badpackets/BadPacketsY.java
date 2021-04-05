package me.levansj01.verus.check.checks.badpackets;


import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import me.levansj01.verus.compat.packets.VPacketPlayInTransaction;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "Y", friendlyName = "Blink", version = CheckVersion.RELEASE, logData = true, minViolations = -2.5, maxViolations = 15)
public class BadPacketsY extends PacketCheck {
    private Long lastTickDelay;
    private Long lastKeepAlive = 0L;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInKeepAlive) {
            this.lastKeepAlive = l;
        } else if (vPacket instanceof VPacketPlayInTransaction && this.playerData.shouldHaveReceivedPing() && !this.player.isDead() && !this.playerData.isVehicle() && this.playerData.isSurvival() && this.playerData.getTotalTicks() > 800) {
            long highestPing = Math.max(50, Math.max(this.playerData.getTransactionPing(), this.playerData.getPing()));
            long lastFlying = l - this.playerData.getLastFlying();
            if (this.lastTickDelay != null && this.playerData.isSurvival()) {
                int threshold = this.playerData.getVersion().after(ClientVersion.VERSION1_8) ? 2000 : 500;
                long moveDiff = lastFlying - this.lastTickDelay;
                long pingDiff = l - this.lastKeepAlive;

                long modifiedDelay;
                if (lastFlying > (modifiedDelay = (long) threshold + highestPing * 2L) && moveDiff > Math.max(250L, highestPing) && moveDiff < 500L && pingDiff < 1000L + highestPing * 2L) {
                    this.handleViolation(formatDebug(lastFlying, moveDiff, modifiedDelay, pingDiff), 0.5);
                } else {
                    this.decreaseVL(0.05);
                }
            }
            this.lastTickDelay = lastFlying;
        }
    }

    private static String formatDebug(long t, long d, long l, long k) {
        return String.format("T: %s D: %s L: %s O: %s K: %s", t, d, l, Math.abs(t - l), k);
    }
}
