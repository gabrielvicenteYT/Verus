package me.levansj01.verus.check.checks.reach;

import me.levansj01.verus.check.ReachCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.reach.DistanceData;
import me.levansj01.verus.data.reach.ReachBase;

@CheckInfo(type = CheckType.REACH, subType = "A", friendlyName = "Reach", version = CheckVersion.RELEASE, maxViolations = 30, minViolations = -1.5, logData = true)
public class ReachA extends ReachCheck {
    private long lastFlag;

    public void handle(ReachBase reachBase, long l) {
        DistanceData distanceData = reachBase.getDistanceData();
        double d = distanceData.getReach();
        double d2 = distanceData.getExtra();
        double d3 = distanceData.getVertical();
        double d4 = distanceData.getHorizontal();
        if (d > 3.0 && d4 < 6.0 && d2 < 6.0) {
            if (l - this.lastFlag < 50L) {
                return;
            }
            this.lastFlag = l;
            this.handleViolation(
                    String.format("R: %.2f H: %.2f V: %.2f E: %.2f S: %s C: %.2f P: %s",
                            d, d4, d3, d2, reachBase.getDistanceList().size(), reachBase.getCertainty(),
                            this.playerData.getTransactionPing()),
                    Math.min(d, 4.5) - 3.0 + reachBase.getCertainty());
        } else {
            this.violations -= Math.min(this.violations + 1.5, 0.005);
        }
    }

    public ReachA() {
        if (NMSManager.getInstance().getServerVersion().after(ServerVersion.v1_8_R3)) {
            this.setMaxViolation(100);
        }
    }
}
