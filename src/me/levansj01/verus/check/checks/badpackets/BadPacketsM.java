package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.MovementCheck2;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.verus2.data.player.PacketLocation;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "M", version = CheckVersion.DEVELOPMENT, friendlyName = "NoSlowdown", minViolations = -2.0, unsupportedVersions = {ClientVersion.VERSION1_7, ClientVersion.VERSION1_9, ClientVersion.VERSION1_13})
public class BadPacketsM extends MovementCheck2 {
    private int threshold = 0;

    public void handle(PacketLocation packetLocation, PacketLocation packetLocation2, long l) {
        if (this.playerData.isBlocking() && this.playerData.isSprinting(true)) {
            if (this.threshold++ > this.playerData.getMaxPingTicks()) {
                this.handleViolation();
                this.threshold = 0;
            }
        } else {
            this.threshold = 0;
            this.decreaseVL(0.025);
        }
    }
}

