package me.levansj01.verus.check;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.PlayerLocation;

public abstract class AimCheck
extends Check {
    public abstract void handle(PlayerLocation var1, PlayerLocation var2, long var3);

    public AimCheck() {
    }

    public AimCheck(CheckType checkType, String string, String string2, CheckVersion checkVersion) {
        super(checkType, string, string2, checkVersion, new ClientVersion[0]);
    }
}
