package me.levansj01.verus.check.checks.badpackets;


import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;

@CheckInfo(type=CheckType.BAD_PACKETS, subType="K", friendlyName="Invalid Interact", version=CheckVersion.RELEASE, maxViolations=1, logData=true)
public class BadPacketsK extends PacketCheck {
    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInUseEntity && ((VPacketPlayInUseEntity)vPacket).getId() == this.player.getEntityId() && this.playerData.isSurvival()) {
            this.handleViolation(this::handle);
        }
    }

    private String handle() {
        return String.format("E: %s", this.player.getEntityId());
    }
}
