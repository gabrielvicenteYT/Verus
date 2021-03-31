package me.levansj01.verus.check;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.PlayerLocation;

public abstract class MovementCheck
extends Check {
    public abstract void handle(PlayerLocation var1, PlayerLocation var2, long var3);

    public MovementCheck() {
    }

    public MovementCheck(CheckType checkType, String string, String string2, CheckVersion checkVersion, ClientVersion ... arrclientVersion) {
        super(checkType, string, string2, checkVersion, arrclientVersion);
    }
}
