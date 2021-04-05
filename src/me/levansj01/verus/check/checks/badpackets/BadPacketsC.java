package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "C", friendlyName = "KillAura", version = CheckVersion.RELEASE, unsupportedAtleast = ClientVersion.VERSION1_9, unsupportedServers = {ServerVersion.v1_11_R1, ServerVersion.v1_12_R1, ServerVersion.v1_14_R1, ServerVersion.v1_15_R1, ServerVersion.v1_16_R1, ServerVersion.v1_16_R2, ServerVersion.v1_16_R3})
public class BadPacketsC extends PacketCheck {

    private boolean received;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            this.received = false;
        } else if (vPacket instanceof VPacketPlayInArmAnimation) {
            this.received = true;
        } else if (vPacket instanceof VPacketPlayInUseEntity && ((VPacketPlayInUseEntity) vPacket).getAction() == VPacketPlayInUseEntity.EntityUseAction.ATTACK) {
            if (!this.received && this.playerData.isSurvival()) {
                this.handleViolation();
            }
            this.received = false;
        }
    }
}
