package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInEntityAction;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "E", friendlyName = "Invalid Move", version = CheckVersion.RELEASE, unsupportedAtleast = ClientVersion.VERSION1_9)
public class BadPacketsE extends PacketCheck {
    private int threshold = 0;
    private boolean sentSprint;
    private boolean sentSneak;

    public void handle(VPacket vPacket, long l) {
        if (StorageEngine.getInstance().getVerusConfig().isSchemAlerts()) {
            if (vPacket instanceof VPacketPlayInFlying) {
                this.sentSprint = false;
                this.sentSneak = false;
                this.threshold = 0;
            } else if (vPacket instanceof VPacketPlayInEntityAction && !this.playerData.isVehicle() && this.playerData.isSurvival() && !this.playerData.isTeleportingV2() && !this.playerData.hasLag()) {
                VPacketPlayInEntityAction vPacketPlayInEntityAction = (VPacketPlayInEntityAction) vPacket;
                VPacketPlayInEntityAction.PlayerAction playerAction = vPacketPlayInEntityAction.getAction();
                if (playerAction.isSprint()) {
                    if (this.sentSprint) {
                        this.handleViolation("Sprint");
                    }
                    this.sentSprint = true;
                } else if (playerAction.isSneak()) {
                    if (this.sentSneak) {
                        this.handleViolation("Sneak");
                    }
                    this.sentSneak = true;
                }
            }
        }
    }

    public void handle(String string) {
        if (this.threshold++ > this.playerData.getMaxPingTicks()) {
            this.handleViolation(string);
        }
    }
}