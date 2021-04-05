package me.levansj01.verus.check.checks.crasher;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(type = CheckType.SERVER_CRASHER, subType = "C", friendlyName = "Server Crasher", version = CheckVersion.RELEASE, maxViolations = 1)
public class ServerCrasherC extends PacketCheck {

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying && Math.abs(((VPacketPlayInFlying) vPacket).getY()) > 1.0E9) {
            this.handleViolation();
            this.playerData.fuckOff();
        }
    }
}
