package me.levansj01.verus.check.checks.inventory;


import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type=CheckType.INVENTORY, subType="G", friendlyName="Inventory Cleaner", version=CheckVersion.DEVELOPMENT, minViolations=-1.0, unsupportedAtleast=ClientVersion.VERSION1_9)
public class InventoryG extends PacketCheck {
    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInWindowClick && !this.playerData.isInventoryOpen() && ((VPacketPlayInWindowClick)vPacket).getSlot() == -1) {
            this.handleViolation();
        }
    }
}
