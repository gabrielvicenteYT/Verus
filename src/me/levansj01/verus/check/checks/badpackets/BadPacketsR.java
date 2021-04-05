package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "R", friendlyName = "KillAura", version = CheckVersion.RELEASE, unsupportedAtleast = ClientVersion.VERSION1_9)
public class BadPacketsR extends PacketCheck {
    private boolean sent;
    private boolean interact;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            this.interact = false;
            this.sent = false;
        } else if (vPacket instanceof VPacketPlayInBlockPlace) {
            this.sent = true;
        } else if (vPacket instanceof VPacketPlayInUseEntity && ((VPacketPlayInUseEntity) vPacket).getAction().isInteract()) {
            if (this.sent && !this.interact) {
                this.handleViolation("", 1.0, true);
                this.sent = false;
            }
            this.interact = true;
        }
    }
}