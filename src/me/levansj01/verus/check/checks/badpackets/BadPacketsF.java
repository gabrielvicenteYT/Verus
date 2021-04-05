package me.levansj01.verus.check.checks.badpackets;


import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "F", friendlyName = "KillAura", version = CheckVersion.RELEASE, minViolations = -1.0, maxViolations = 3, unsupportedAtleast = ClientVersion.VERSION1_9)
public class BadPacketsF extends PacketCheck {

    private boolean place = false;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            this.place = false;
        } else if (vPacket instanceof VPacketPlayInBlockPlace) {
            VPacketPlayInBlockPlace vPacketPlayInBlockPlace = (VPacketPlayInBlockPlace) vPacket;
            if (vPacketPlayInBlockPlace.isItem()) {
                this.place = true;
            }
        } else if (vPacket instanceof VPacketPlayInBlockDig && ((VPacketPlayInBlockDig) vPacket).getType() == VPacketPlayInBlockDig.PlayerDigType.RELEASE_USE_ITEM && this.place && !this.playerData.isTeleporting() && !this.playerData.isVehicle()) {
            this.handleViolation("", 1.0, false);
        }
    }
}
