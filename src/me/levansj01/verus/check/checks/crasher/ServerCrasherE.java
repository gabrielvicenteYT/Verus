package me.levansj01.verus.check.checks.crasher;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInSetCreativeSlot;

@CheckInfo(type = CheckType.SERVER_CRASHER, subType = "E", friendlyName = "Server Crasher", version = CheckVersion.DEVELOPMENT, maxViolations = 1, minViolations = -2.0)
public class ServerCrasherE extends PacketCheck {
    private int threshold = 0;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            this.decreaseVL(0.5);
        } else if (vPacket instanceof VPacketPlayInSetCreativeSlot) {
            if (this.playerData.isSurvival()) {
                if (this.threshold++ > 2) {
                    this.handleViolation();
                }
            } else {
                this.decreaseVL(0.5);
            }
        }
    }
}
