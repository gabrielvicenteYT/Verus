package me.levansj01.verus.check.checks.crasher;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;

@CheckInfo(type = CheckType.SERVER_CRASHER, subType = "D", friendlyName = "Server Crasher", version = CheckVersion.DEVELOPMENT, maxViolations = 1)
public class ServerCrasherD extends PacketCheck {

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInCustomPayload && ((VPacketPlayInCustomPayload) vPacket).getData().length > 15000) {
            this.handleViolation();
            this.playerData.fuckOff();
        }
    }
}