package me.levansj01.verus.check.checks.timer;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type=CheckType.TIMER, subType="A", friendlyName="Timer", version=CheckVersion.RELEASE, minViolations=-1.0, maxViolations=40, logData=true, unsupportedAtleast=ClientVersion.VERSION1_9)
public class TimerA
        extends PacketCheck {
    private int released;
    private static final long CHECK_TIME_LINEAR = TimerA.toNanos(45L);
    private Long lastPacket = null;
    private long lastFlag;
    private long offset = -100L;
    private boolean place;
    private long lastBow;

    public static long fromNanos(long l) {
        return TimeUnit.NANOSECONDS.toMillis(l);
    }

    public static long toNanos(long l) {
        return TimeUnit.MILLISECONDS.toNanos(l);
    }

    private String lambda$handle$0(long l, long l2, long l3, long l4, boolean bl) {
        long l5;
        long l6;
        Object[] objectArray = new Object[6];
        objectArray[0] = TimerA.fromNanos(this.offset);
        objectArray[1] = TimerA.fromNanos(l - this.lastFlag);
        objectArray[2] = TimerA.fromNanos(l - this.lastBow);
        if (l2 == 0L) {
            l6 = -1L;
        } else {
            l6 = l3 - l2;
        }
        objectArray[3] = l6;
        if (l4 == 0L) {
            l5 = -1L;
        } else {
            l5 = l3 - l4;
        }
        objectArray[4] = l5;
        objectArray[5] = bl;
        return String.format("O: %sms L: %sms R: %sms T: %sms M: %s B:%s", objectArray);
    }

    public void handle(VPacket vPacket, long l) {
        VPacketPlayInBlockPlace vPacketPlayInBlockPlace;
        if (vPacket instanceof VPacketPlayInFlying) {
            long l2 = System.nanoTime();
            if (this.place) {
                this.lastBow = l2;
                this.place = false;
            }
            if (this.lastPacket != null) {
                long l3 = l2 - this.lastPacket;
                long l4 = TimerA.toNanos(50L) - l3;
                this.offset += l4;
                if (this.offset > CHECK_TIME_LINEAR) {
                    boolean bl = false;
                    boolean bl2;
                    if (TimerA.fromNanos(l2 - this.lastBow) < 10L) {
                        bl2 = true;
                    } else {
                        bl2 = bl = false;
                    }
                    if ((TimerA.fromNanos(l2 - this.lastFlag) > 1L || bl) && this.playerData.shouldHaveReceivedPing() && this.playerData.getTotalTicks() > 400 && !this.playerData.isTeleporting(3) && !this.playerData.isTeleportingV2() && this.playerData.isSpawned() && !this.playerData.isVehicle()) {
                        double d;
                        boolean bl3;
                        long l5 = this.playerData.getLastRespawn();
                        long l6 = this.playerData.getLastTeleport();
                        bl3 = this.playerData.isMoved() || this.released <= 1 || bl;
                        boolean bl4 = bl3;
                        Supplier supplier = () -> this.lambda$handle$0(l2, l5, l, l6, bl4);
                        if (bl4) {
                            d = 1.0;
                        } else {
                            d = 0.3;
                        }
                        this.handleViolation(supplier, d);
                    }
                    this.lastFlag = l2;
                    this.offset = 0L;
                } else {
                    this.decreaseVL(0.0025);
                }
            }
            ++this.released;
            this.lastPacket = l2;
        } else if (vPacket instanceof VPacketPlayInBlockDig) {
            VPacketPlayInBlockDig vPacketPlayInBlockDig = (VPacketPlayInBlockDig)vPacket;
            if (vPacketPlayInBlockDig.getType() == VPacketPlayInBlockDig.PlayerDigType.RELEASE_USE_ITEM) {
                this.released = 0;
            }
        } else if (vPacket instanceof VPacketPlayInBlockPlace && (vPacketPlayInBlockPlace = (VPacketPlayInBlockPlace)vPacket).isItem()) {
            this.place = true;
        }
    }
}
