package me.levansj01.verus.check.checks.badpackets;


import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type=CheckType.BAD_PACKETS, subType="X", friendlyName="FastBreak", version=CheckVersion.RELEASE, minViolations=-1.0, maxViolations=10)
public class BadPacketsX extends PacketCheck {
    private int ticks = 0;
    private int stage = 0;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            if (this.stage == 2) {
                this.violations -= Math.min(this.violations + 1.0, 0.01);
            }
            if (this.stage == 1) {
                ++this.ticks;
                this.stage = 2;
            } else {
                this.stage = 0;
            }
        } else if (vPacket instanceof VPacketPlayInBlockDig) {
            VPacketPlayInBlockDig vPacketPlayInBlockDig = (VPacketPlayInBlockDig)vPacket;
            if (vPacketPlayInBlockDig.getType() == VPacketPlayInBlockDig.PlayerDigType.STOP_DESTROY_BLOCK) {
                this.stage = 1;
                this.decreaseVL(1.0E-4);
            } else if (vPacketPlayInBlockDig.getType() == VPacketPlayInBlockDig.PlayerDigType.START_DESTROY_BLOCK) {
                if (this.stage == 2 && (this.ticks != 1 || this.playerData.getVersion().before(ClientVersion.VERSION1_9))) {
                    this.handleViolation(String.format("T: %s", this.ticks), 0.25);
                }
                this.stage = 0;
                this.ticks = 0;
            }
        }
    }
}
