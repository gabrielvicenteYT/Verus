package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.storage.StorageEngine;

@CheckInfo(type=CheckType.BAD_PACKETS, subType="O", friendlyName="Invalid Interact", version=CheckVersion.RELEASE, minViolations=-1.5, maxViolations=5, schematica=true)
public class BadPacketsO extends PacketCheck {
    private boolean placed;

    public void handle(VPacket vPacket, long l) {
        if (StorageEngine.getInstance().getVerusConfig().isSchemAlerts()) {
            if (vPacket instanceof VPacketPlayInFlying) {
                if (this.placed) {
                    if (l - this.playerData.getLastLastFlying() > 40L) {
                        this.handleViolation("", 0.5);
                    } else {
                        this.violations -= Math.min(this.violations + 0.5, 0.05);
                    }
                    this.placed = false;
                }
            } else if (vPacket instanceof VPacketPlayInBlockPlace && this.playerData.isSurvival()) {
                if (l - this.playerData.getLastFlying() < 5L) {
                    this.placed = true;
                } else {
                    this.decreaseVL(0.1);
                }
            }
        }
    }
}
