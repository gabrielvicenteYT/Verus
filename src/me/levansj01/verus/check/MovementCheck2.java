package me.levansj01.verus.check;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.verus2.data.player.PacketLocation;

public abstract class MovementCheck2
extends Check {
    public abstract void handle(PacketLocation var1, PacketLocation var2, long var3);

    public void handleTeleport() {
    }

    public MovementCheck2() {
    }

    public MovementCheck2(CheckType checkType, String string, String string2, CheckVersion checkVersion, ClientVersion ... arrclientVersion) {
        super(checkType, string, string2, checkVersion, arrclientVersion);
    }

    public void handleSprint(boolean bl) {
    }

    public void handleAttack(VPacketPlayInUseEntity vPacketPlayInUseEntity) {
    }
}
