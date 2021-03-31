package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.verus2.data.player.Teleport;

@CheckInfo(type=CheckType.BAD_PACKETS, subType="G4", friendlyName="Ping Spoof", version=CheckVersion.RELEASE, minViolations=-1.0)
public class BadPacketsG4 extends Check {
    private int threshold = 0;

    public void accept(long l, Teleport teleport) {
        int n;
        long l2 = l - teleport.getTime();
        if (l2 * 10L + 100L < (long)(n = this.playerData.getTransactionPing()) && this.playerData.getTotalTicks() > 100) {
            if (this.threshold++ > 3) {
                this.handleViolation(() -> this.lambda$accept$0(n, l2), (double)this.threshold / 4.0);
                this.playerData.setTransactionPing((int)l2);
            }
        } else {
            this.threshold = 0;
        }
    }

    private String lambda$accept$0(int n, long l) {
        return String.format("Tr: %s K: %s Te: %s", n, this.playerData.getPing(), l);
    }
}
