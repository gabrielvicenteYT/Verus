package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;

@CheckInfo(friendlyName="Ping Spoof", type=CheckType.BAD_PACKETS, subType="G6", version=CheckVersion.RELEASE, maxViolations=200, minViolations=-2.0)
public class BadPacketsG6 extends PacketCheck {

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInUseEntity) {
            if (this.playerData.getTotalTicks() > 900 && this.playerData.isSentTransaction() && !this.playerData.isReceivedTransaction()) {
                VPacketPlayInUseEntity vPacketPlayInUseEntity = (VPacketPlayInUseEntity)vPacket;
                if (vPacketPlayInUseEntity.getEntity(this.player.getWorld()) != null) {
                    this.handleViolation("", 0.25);
                }
            } else {
                this.decreaseVL(0.01);
            }
        }
    }
}
