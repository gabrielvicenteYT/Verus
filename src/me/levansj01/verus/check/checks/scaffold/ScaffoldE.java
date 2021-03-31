package me.levansj01.verus.check.checks.scaffold;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.storage.StorageEngine;

@CheckInfo(type = CheckType.SCAFFOLD, subType = "E", friendlyName = "Scaffold", version = CheckVersion.RELEASE, schematica = true, maxViolations = 1)
public class ScaffoldE extends PacketCheck {

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInBlockPlace
                && StorageEngine.getInstance().getVerusConfig().isSchemAlerts()) {
            VPacketPlayInBlockPlace vPacketPlayInBlockPlace = (VPacketPlayInBlockPlace) vPacket;
            float f = vPacketPlayInBlockPlace.getBlockX();
            float f2 = vPacketPlayInBlockPlace.getBlockY();
            float f3 = vPacketPlayInBlockPlace.getBlockZ();
            if (vPacketPlayInBlockPlace.getFace() != 255 && (f > 1.0f || f2 > 1.0f || f3 > 1.0f)) {
                this.handleViolation(String.format((String) "X: %.2f Y: %.2f Z: %.2f", (Object[]) new Object[] {
                        Float.valueOf((float) f), Float.valueOf((float) f2), Float.valueOf((float) f3) }));
            }
            this.decreaseVL(0.05);
        }
    }
}
