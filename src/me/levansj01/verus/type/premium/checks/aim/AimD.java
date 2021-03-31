package me.levansj01.verus.type.premium.checks.aim;

import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.PlayerLocation;

public class AimD extends AimCheck {
    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        if (this.playerData.getLastAttackTicks() > 100) {
            return;
        }
        float f = Math.abs(playerLocation2.getYaw() - playerLocation.getYaw());
        float f2 = Math.abs(playerLocation2.getPitch() - playerLocation.getPitch());
        if (f2 > 0.0f && f2 < 1.0E-5 && f > 5.0f) {
            this.handleViolation(String.format("Y: %s P: %s", new Object[] { Float.valueOf(f), Float.valueOf(f2) }));
        }
        this.violations -= Math.min(this.violations + 1.0, 0.005);
    }

    public AimD() {
        super(CheckType.AIM_ASSIST, "D", "Aim", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
}
