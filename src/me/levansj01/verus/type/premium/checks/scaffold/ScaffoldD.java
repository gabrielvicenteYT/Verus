package me.levansj01.verus.type.premium.checks.scaffold;

import java.util.Objects;
import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;

@CheckInfo(type=CheckType.SCAFFOLD, subType="D", friendlyName="Scaffold", version=CheckVersion.RELEASE, minViolations=-1.0, maxViolations=2, schematica=true)
public class ScaffoldD extends PacketCheck {
    private Integer lastSlot;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInHeldItemSlot) {
            VPacketPlayInHeldItemSlot vPacketPlayInHeldItemSlot = (VPacketPlayInHeldItemSlot)vPacket;
            if (this.lastSlot != null && Objects.equals(vPacketPlayInHeldItemSlot.getSlot(), this.lastSlot) && this.playerData.getTotalTicks() > 200) {
                this.handleViolation();
            } else {
                this.decreaseVL(0.025);
            }
            this.lastSlot = vPacketPlayInHeldItemSlot.getSlot();
        }
    }
}
