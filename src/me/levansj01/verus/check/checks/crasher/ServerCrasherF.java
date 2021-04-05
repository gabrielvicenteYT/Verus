package me.levansj01.verus.check.checks.crasher;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.inventory.ItemStack;

@CheckInfo(type = CheckType.SERVER_CRASHER, subType = "F", friendlyName = "Server Crasher", version = CheckVersion.DEVELOPMENT, maxViolations = 1)
public class ServerCrasherF extends PacketCheck {

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInCustomPayload) {
            switch (((VPacketPlayInCustomPayload) vPacket).getChannel()) {
                case "MC|BOpen":
                case "MC|BEdit":
                case "MC|BSign":
                    if(!MaterialList.BOOKS.contains(this.player.getItemInHand().getType())) {
                        this.handleViolation();
                        this.playerData.fuckOff();
                    }
            }
        }
    }
}