package me.levansj01.verus.type.premium.checks.aim;

import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.PlayerLocation;

@CheckInfo(type = CheckType.AIM_ASSIST, subType = "C", friendlyName = "Aim", version = CheckVersion.RELEASE, minViolations = -2.5, logData = true)
public class AimC extends AimCheck {
    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        if (this.playerData.getLastAttackTicks() > 100) {
            return;
        }
        float f = Math.abs(playerLocation2.getYaw() - playerLocation.getYaw());
        float f2 = Math.abs(playerLocation2.getPitch() - playerLocation.getPitch());
        if (f > 0.0f && (double) f < 0.01 && (double) f2 > 0.2) {
            this.handleViolation(String.format("Y: %s P: %s", f, f2));
        }
        this.violations -= Math.min(this.violations + 2.5, 0.05);
    }
}
