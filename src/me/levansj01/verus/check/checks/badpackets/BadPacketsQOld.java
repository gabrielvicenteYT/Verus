package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

public class BadPacketsQOld extends PacketCheck {
    private boolean sent = false;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            this.sent = false;
        } else if (vPacket instanceof VPacketPlayInUseEntity) {
            VPacketPlayInUseEntity vPacketPlayInUseEntity = (VPacketPlayInUseEntity)vPacket;
            if (vPacketPlayInUseEntity.getAction().isInteract()) {
                if (this.playerData.getVersion() == ClientVersion.VERSION1_8 && !this.sent) {
                    this.handleViolation("", 1.0, true);
                }
            } else if (vPacketPlayInUseEntity.getAction().isInteractAt()) {
                this.sent = true;
            }
        }
    }

    public BadPacketsQOld() {
        super(CheckType.BAD_PACKETS, "Q", "KillAura", CheckVersion.EXPERIMENTAL);
    }
}
