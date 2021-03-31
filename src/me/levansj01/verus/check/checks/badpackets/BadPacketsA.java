package me.levansj01.verus.check.checks.badpackets;

import java.util.function.Supplier;
import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(type=CheckType.BAD_PACKETS, subType="A", friendlyName="Timer", version=CheckVersion.RELEASE, maxViolations=5, logData=true)
public class BadPacketsA extends PacketCheck {

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            int n = this.playerData.getPackets();
            int n2 = this.playerData.getMaxPingTicks();
            if (n > 20) {
                double d;
                Supplier supplier = () -> BadPacketsA.handle(n, n2);
                if (n > 22) {
                    d = 2.0;
                } else {
                    d = 0.2;
                }
                this.handleViolation(supplier, d);
            } else {
                this.decreaseVL(1.0E-5);
            }
        }
    }

    private static String handle(int n, int n2) {
        return String.format("P: %s | %s", n, n2);
    }
}
