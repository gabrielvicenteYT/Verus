package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.verus2.data.player.Teleport;

@CheckInfo(type = CheckType.BAD_PACKETS, subType = "G4", friendlyName = "Ping Spoof", version = CheckVersion.RELEASE, minViolations = -1.0)
public class BadPacketsG4 extends Check {
    private int threshold = 0;

    public void accept(long acceptTime, Teleport teleport) {
        int transactionPing = this.playerData.getTransactionPing();
        long teleportPing = acceptTime - teleport.getTime();

        if (teleportPing * 10L + 100L < (long) transactionPing && this.playerData.getTotalTicks() > 100) {
            if (this.threshold++ > 3) {
                this.handleViolation(() -> this.formatDebug(transactionPing, teleportPing), (double) this.threshold / 4.0);
                this.playerData.setTransactionPing((int) teleportPing);
            }
        } else {
            this.threshold = 0;
        }
    }

    private String formatDebug(int transactionPing, long teleportPing) {
        return String.format("Tr: %s K: %s Te: %s", transactionPing, this.playerData.getPing(), teleportPing);
    }
}
