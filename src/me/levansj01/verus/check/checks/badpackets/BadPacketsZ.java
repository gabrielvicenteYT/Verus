package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "Z", friendlyName = "Nuker", version = CheckVersion.RELEASE, unsupportedVersions = {ClientVersion.VERSION1_7, ClientVersion.VERSION1_9, ClientVersion.VERSION1_13}, logData = true, minViolations = -1.0)
public class BadPacketsZ extends PacketCheck {
    private int invalid = 0;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInArmAnimation) {
            this.invalid = 0;
        } else if (vPacket instanceof VPacketPlayInBlockDig) {
            VPacketPlayInBlockDig vPacketPlayInBlockDig = (VPacketPlayInBlockDig) vPacket;
            if ((vPacketPlayInBlockDig.getType().equals(VPacketPlayInBlockDig.PlayerDigType.START_DESTROY_BLOCK) || vPacketPlayInBlockDig.getType().equals(VPacketPlayInBlockDig.PlayerDigType.STOP_DESTROY_BLOCK)) && !this.playerData.hasLag() && !this.playerData.hasFast() && l - this.playerData.getLastClientTransaction() < 500L) {
                if (this.invalid++ > 2) {
                    this.handleViolation(String.format("I: %s", this.invalid));
                    if (this.violations > 100000.0) {
                        AlertManager.getInstance().handleBan(this.playerData, this, false);
                        this.playerData.fuckOff();
                    }
                }
            } else {
                this.decreaseVL(1.0);
            }
        }
    }
}