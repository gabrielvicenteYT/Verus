package me.levansj01.verus.type.premium.checks.killaura;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type=CheckType.KILL_AURA, subType="K", friendlyName="Auto Clicker", version=CheckVersion.RELEASE, unsupportedAtleast=ClientVersion.VERSION1_9)
public class KillAuraK extends PacketCheck {
    private boolean attack;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            this.attack = false;
        } else if (vPacket instanceof VPacketPlayInBlockDig) {
            VPacketPlayInBlockDig vPacketPlayInBlockDig = (VPacketPlayInBlockDig)vPacket;
            if ((vPacketPlayInBlockDig.getType() == VPacketPlayInBlockDig.PlayerDigType.DROP_ITEM || vPacketPlayInBlockDig.getType() == VPacketPlayInBlockDig.PlayerDigType.DROP_ALL_ITEMS) && this.attack) {
                this.handleViolation("", 1.0, true);
            }
        } else if (vPacket instanceof VPacketPlayInUseEntity && ((VPacketPlayInUseEntity)vPacket).getAction() == VPacketPlayInUseEntity.EntityUseAction.ATTACK) {
            this.attack = true;
        }
    }
}
