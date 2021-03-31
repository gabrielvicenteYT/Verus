package me.levansj01.verus.type.premium.checks.aim;

import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.PlayerLocation;

@CheckInfo(type = CheckType.AIM_ASSIST, subType = "H", friendlyName = "Aim", version = CheckVersion.RELEASE, minViolations = -1.0, logData = true)
public class AimH extends AimCheck {
    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        float f = Math.abs(playerLocation.getYaw() - playerLocation2.getYaw());
        float f2 = Math.abs(playerLocation.getPitch() - playerLocation2.getPitch());
        if (this.playerData.getLastAttackTicks() < 3 && f > 0.0f && f < 0.8 && f2 > 0.279 && f2 < 0.28090858) {
            this.handleViolation(String.format("Y: %s P: %s", new Object[] { Float.valueOf(f), Float.valueOf(f2) }));
        }
        this.decreaseVL(1.0E-4);
    }
}
