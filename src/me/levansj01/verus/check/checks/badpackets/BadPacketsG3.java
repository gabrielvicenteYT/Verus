package me.levansj01.verus.check.checks.badpackets;


import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import me.levansj01.verus.type.Loader;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "G3", friendlyName = "Ping Spoof", minViolations = -3.0, maxViolations = 200, version = CheckVersion.RELEASE, logData = true)
public class BadPacketsG3 extends PacketCheck {

    private boolean receivedKeepAlive;

    public BadPacketsG3() {
        switch (Loader.getUsername()) {
            case "UniversoCraft":
            case "sage":
                this.setMaxViolation(30);
        }
    }

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            if (this.receivedKeepAlive) {
                double highestTPing = MathUtil.highest(this.playerData.getLastTransactionPing(), this.playerData.getTransactionPing(), this.playerData.getAverageTransactionPing());
                double lowestKPing = MathUtil.lowest(this.playerData.getLastPing(), this.playerData.getPing(), this.playerData.getAveragePing());
                if (lowestKPing - highestTPing > 50.0 + highestTPing / 4.0) {
                    VPacketPlayInFlying vPacketPlayInFlying = (VPacketPlayInFlying) vPacket;
                    this.handleViolation(() -> BadPacketsG3.formatDebug(lowestKPing, highestTPing, vPacketPlayInFlying));
                } else {
                    this.decreaseVL(0.1);
                }
                this.receivedKeepAlive = false;
            }
        } else if (vPacket instanceof VPacketPlayInKeepAlive) {
            this.receivedKeepAlive = true;
        }
    }

    private static String formatDebug(double d, double d2, VPacketPlayInFlying vPacketPlayInFlying) {
        return String.format("K(%s) T(%s) %s", d, d2, vPacketPlayInFlying.isPos());
    }
}