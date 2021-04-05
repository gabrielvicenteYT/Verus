package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "U", friendlyName = "Invalid Inventory", version = CheckVersion.RELEASE, minViolations = -1.5, maxViolations = 5, logData = true, schematica = true)
public class BadPacketsU extends PacketCheck {
    private Long lastFlying;
    private Long swapped;

    public void handle(VPacket vPacket, long l) {
        if (StorageEngine.getInstance().getVerusConfig().isSchemAlerts()) {
            if (vPacket instanceof VPacketPlayInFlying) {
                if (this.lastFlying != null && this.swapped != null) {
                    if (this.playerData.getVersion().after(ClientVersion.VERSION1_8) && l - this.lastFlying > 60L) {
                        return;
                    }
                    if (l - this.playerData.getLastLastFlying() > 45L && l - this.swapped > 40L) {
                        this.handleViolation(String.format("S: %s F: %s", l - this.swapped, l - this.lastFlying), 0.5);
                    } else {
                        this.decreaseVL(0.05);
                    }
                }
                this.lastFlying = this.playerData.getLastFlying();
                this.swapped = null;
            } else if (vPacket instanceof VPacketPlayInHeldItemSlot && this.playerData.isSurvival()) {
                if (l - this.playerData.getLastFlying() < 5L) {
                    this.swapped = l;
                } else {
                    this.decreaseVL(0.1);
                }
            }
        }
    }
}
