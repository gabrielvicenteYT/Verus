package me.levansj01.verus.type.premium.checks.aim;

import java.util.Objects;
import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.PlayerLocation;

@CheckInfo(type=CheckType.AIM_ASSIST, subType="A1", friendlyName="Aimbot", version=CheckVersion.RELEASE, maxViolations=10, logData=true)
public class AimA1
extends AimCheck {
    private float lastYawChange;

    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long l) {
        if (this.playerData.getLastAttackTicks() < 3) {
            int n;
            float f = Math.abs(playerLocation.getYaw() - playerLocation2.getYaw());
            if (f > 1.0f && (n = Math.round(f)) == f) {
                if (Objects.equals(Float.valueOf(f), Float.valueOf((float)this.lastYawChange))) {
                    this.handleViolation(String.format("Y: %s", new Object[]{Float.valueOf((float)f)}));
                }
                this.lastYawChange = n;
            }
            this.lastYawChange = 0.0f;
        }
    }
}
