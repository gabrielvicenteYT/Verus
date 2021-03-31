package me.levansj01.verus.type.premium.checks.aim;

import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.PlayerLocation;

@CheckInfo(type=CheckType.AIM_ASSIST, subType="A", friendlyName="Aimbot", version=CheckVersion.RELEASE, maxViolations=5, logData=true)
public class AimA
extends AimCheck {
    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        float f;
        float f2 = Math.abs((float)(playerLocation.getYaw() - playerLocation2.getYaw()));
        if (f2 >= 1.0f && f2 % 0.1f == 0.0f) {
            if (f2 % 1.0f == 0.0f) {
                this.violations += 1.0;
            }
            if (f2 % 10.0f == 0.0f) {
                this.violations += 1.0;
            }
            if (f2 % 30.0f == 0.0f) {
                this.violations += 1.0;
            }
            this.handleViolation(String.format((String)"Y: %s", (Object[])new Object[]{Float.valueOf((float)f2)}));
        }
        if ((f = Math.abs((float)(playerLocation.getPitch() - playerLocation2.getPitch()))) >= 1.0f && f % 0.1f == 0.0f) {
            if (f % 1.0f == 0.0f) {
                this.violations += 1.0;
            }
            if (f % 10.0f == 0.0f) {
                this.violations += 1.0;
            }
            if (f % 30.0f == 0.0f) {
                this.violations += 1.0;
            }
            this.handleViolation(String.format((String)"P: %s", (Object[])new Object[]{Float.valueOf((float)f)}));
        }
    }
}
