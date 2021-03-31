package me.levansj01.verus.check.checks.crasher;


import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;

@CheckInfo(type=CheckType.SERVER_CRASHER, subType="B", friendlyName="Server Crasher", version=CheckVersion.RELEASE, maxViolations=1, unsupportedAtleast=ClientVersion.VERSION1_9)
public class ServerCrasherB extends PacketCheck {
    private int places;
    private int swings;
    private int switches;
    private long lastSwitch;

    public void handle(int n, int n2) {
        if (StorageEngine.getInstance().getVerusConfig().isSchemBans() && this.playerData.isSurvival()) {
            this.handleViolation(String.format("T: %s A: %s", n, n2));
            this.playerData.fuckOff();
        }
    }

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            this.places = 0;
            this.swings = 0;
        } else if (vPacket instanceof VPacketPlayInArmAnimation) {
            if (this.swings++ > 200) {
                this.handle(1, this.swings);
            }
        } else if (vPacket instanceof VPacketPlayInBlockPlace) {
            if (this.places++ > 200) {
                this.handle(2, this.places);
            }
        } else if (vPacket instanceof VPacketPlayInHeldItemSlot) {
            if (l - this.lastSwitch > 100L) {
                this.switches = 0;
                this.lastSwitch = l;
            }
            if (this.switches++ > 400) {
                this.handle(3, this.switches);
            }
        }
    }
}
