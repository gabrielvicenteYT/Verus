package me.levansj01.verus.check.checks.crasher;


import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(type=CheckType.SERVER_CRASHER, subType="A", friendlyName="Server Crasher", version=CheckVersion.RELEASE, maxViolations=5, logData=true)
public class ServerCrasherA extends PacketCheck {
    private int threshold = 0;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInCustomPayload) {
            VPacketPlayInCustomPayload vPacketPlayInCustomPayload = (VPacketPlayInCustomPayload)vPacket;
            String string = vPacketPlayInCustomPayload.getChannel();
            if ((string.equals("MC|BOpen") || string.equals("MC|BEdit")) && (this.threshold += 2) > 4) {
                this.handleViolation(String.format("C: %s", string), this.threshold / 4);
                if (this.violations > (double)this.getMaxViolation()) {
                    this.playerData.fuckOff();
                }
            }
        } else if (vPacket instanceof VPacketPlayInFlying) {
            this.threshold -= Math.min(this.threshold, 1);
        }
    }
}
