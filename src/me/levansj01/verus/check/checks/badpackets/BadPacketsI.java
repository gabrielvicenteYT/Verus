package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;

@CheckInfo(type=CheckType.BAD_PACKETS, subType="I", friendlyName="Invalid Inventory", version=CheckVersion.RELEASE, unsupportedAtleast=ClientVersion.VERSION1_9, minViolations=-1.5, maxViolations=5, schematica=true)
public class BadPacketsI extends PacketCheck {
    private int stage = 0;

    public void handle(VPacket vPacket, long l) {
        if (StorageEngine.getInstance().getVerusConfig().isSchemAlerts()) {
            if (vPacket instanceof VPacketPlayInFlying) {
                this.stage = 0;
            } else if (vPacket instanceof VPacketPlayInBlockPlace) {
                VPacketPlayInBlockPlace vPacketPlayInBlockPlace = (VPacketPlayInBlockPlace)vPacket;
                if (this.stage == 1) {
                    this.stage = 2;
                }
                if (vPacketPlayInBlockPlace.isItem()) {
                    this.stage = 0;
                }
            } else if (vPacket instanceof VPacketPlayInHeldItemSlot) {
                if (this.stage == 2) {
                    this.handleViolation();
                }
                this.stage = 1;
            }
        }
    }
}