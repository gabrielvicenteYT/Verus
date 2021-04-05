package me.levansj01.verus.check.checks.inventory;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type = CheckType.INVENTORY, subType = "J", friendlyName = "KillAura", version = CheckVersion.RELEASE, unsupportedAtleast = ClientVersion.VERSION1_9)
public class InventoryJ extends PacketCheck {
    private boolean swing = false;
    private boolean click = false;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            if (this.swing && this.click) {
                this.handleViolation("", 1.0, true);
            }
            this.swing = false;
            this.click = false;
        } else if (vPacket instanceof VPacketPlayInArmAnimation) {
            this.swing = true;
        } else if (vPacket instanceof VPacketPlayInWindowClick) {
            this.click = true;
        }
    }
}
