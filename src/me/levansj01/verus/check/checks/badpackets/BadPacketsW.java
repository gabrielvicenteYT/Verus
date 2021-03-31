package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

public class BadPacketsW
        extends PacketCheck {
    private int placed = 0;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            this.placed = 0;
            this.decreaseVL(2.0);
        } else if (vPacket instanceof VPacketPlayInBlockPlace && ((VPacketPlayInBlockPlace)vPacket).isItem() && this.placed++ > this.playerData.getMaxPingTicks()) {
            this.handleViolation(String.format("V: %s | %s", this.placed, this.playerData.getMaxPingTicks()), this.placed - 1);
        }
    }
}

