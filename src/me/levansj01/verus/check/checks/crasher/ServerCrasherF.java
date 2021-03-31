package me.levansj01.verus.check.checks.crasher;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.inventory.ItemStack;

@CheckInfo(type=CheckType.SERVER_CRASHER, subType="F", friendlyName="Server Crasher", version=CheckVersion.DEVELOPMENT, maxViolations=1)
public class ServerCrasherF extends PacketCheck {
    public void handle(VPacket vPacket, long l) {
        ItemStack itemStack;
        String string;
        if (vPacket instanceof VPacketPlayInCustomPayload && ((string = ((VPacketPlayInCustomPayload)vPacket).getChannel()).equals("MC|BOpen") || string.equals("MC|BEdit") || string.equals("MC|BSign")) && (itemStack = this.player.getItemInHand()) != null && !MaterialList.BOOKS.contains(itemStack.getType())) {
            this.handleViolation();
            this.playerData.fuckOff();
        }
    }
}