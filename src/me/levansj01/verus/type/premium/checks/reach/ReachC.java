package me.levansj01.verus.type.premium.checks.reach;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import me.levansj01.verus.check.ReachCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.reach.DistanceData;
import me.levansj01.verus.data.reach.ReachBase;

@CheckInfo(type=CheckType.REACH, subType="C", friendlyName="Reach", version=CheckVersion.RELEASE, minViolations=-1.5, maxViolations=15, logData=true)
public class ReachC
        extends ReachCheck {

    public void handle(List<ReachBase> list, long l) {
        Optional optional = list.stream().max(Comparator.comparingDouble(ReachBase::getUncertaintyReachValue));
        if (optional.isPresent()) {
            ReachBase reachBase = (ReachBase)optional.get();
            DistanceData distanceData = reachBase.getDistanceData();
            double d = reachBase.getUncertaintyReachValue();
            double d2 = distanceData.getHorizontal();
            double d3 = distanceData.getExtra();
            if (d > 3.0 && d2 < 6.0 && d3 < 5.0) {
                this.handleViolation(String.format("R: %.2f H: %.2f E: %.2f", distanceData.getReach(), d2, d3), Math.max(Math.ceil(d2 - 3.0), 1.0));
            } else {
                this.decreaseVL(0.035);
            }
        }
    }
}
