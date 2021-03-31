package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type=CheckType.BAD_PACKETS, subType="H", friendlyName="Invalid Direction", version=CheckVersion.RELEASE, minViolations=-1.0, maxViolations=15, unsupportedAtleast=ClientVersion.VERSION1_9)
public class BadPacketsH extends PacketCheck {
    private Float lastPitch = null;
    private Float lastYaw = null;
    private Float oldPitch = null;
    private Float oldYaw = null;

    public void handle(VPacket vPacket, long l) {
        VPacketPlayInFlying vPacketPlayInFlying;
        if (vPacket instanceof VPacketPlayInFlying && (vPacketPlayInFlying = (VPacketPlayInFlying)vPacket).isLook()) {
            float f = vPacketPlayInFlying.getYaw();
            float f2 = vPacketPlayInFlying.getPitch();
            if (!(this.playerData.isTeleportingV2() || this.playerData.isTeleporting(5) || this.playerData.isVehicle() || this.playerData.hasFast(l - (long)this.playerData.getTransactionPing()) || !this.playerData.isSpawned() || this.playerData.isSuffocating())) {
                if (this.oldYaw == null || this.oldYaw != f || this.oldPitch == null || this.oldPitch != f2) {
                    this.oldYaw = null;
                    this.oldPitch = null;
                    if (this.lastYaw != null && this.lastPitch != null && this.lastYaw == f && this.lastPitch == f2) {
                        this.handleViolation(String.format("%f %f", this.lastYaw, this.lastPitch));
                    } else {
                        this.decreaseVL(0.05);
                    }
                }
                this.lastYaw = f;
                this.lastPitch = f2;
            } else {
                this.oldYaw = f;
                this.oldPitch = f2;
            }
        }
    }
}

