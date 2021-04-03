package me.levansj01.verus.type.premium.checks.killaura;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.PacketLocation;
import me.levansj01.verus.util.PlayerLocation;
import me.levansj01.verus.util.java.AtomicCappedQueue;
import me.levansj01.verus.util.java.MathUtil;


@CheckInfo(type=CheckType.KILL_AURA, subType="Q", friendlyName="KillAura", version=CheckVersion.DEVELOPMENT, minViolations=-5.0, maxViolations=30)
public class KillAuraQ
extends AimCheck {
    private final Queue<Double> values = new ConcurrentLinkedQueue<Double>();

    @Override
    public void handle(PlayerLocation playerLocation, PlayerLocation playerLocation2, long n2) {
        AtomicCappedQueue<PacketLocation> deque;
        if (this.playerData.getLastAttackTicks() <= 1 && this.playerData.getLastAttacked() != null && (double)Math.min(Math.abs(playerLocation.getYaw() - playerLocation2.getYaw()), Math.abs(playerLocation.getPitch() - playerLocation2.getPitch())) > 0.5 && (deque = this.playerData.getRecentMoveMap().get(this.playerData.getLastAttackedId())) != null && deque.size() > 5) {
            ArrayList<PacketLocation> list = new ArrayList<PacketLocation>();
            long n3 = n2 - 125L - (long)this.playerData.getTransactionPing();
            Iterator<PacketLocation> iterator = deque.iterator();
            PacketLocation packetLocation2 = iterator.next();
            while (iterator.hasNext()) {
                PacketLocation packetLocation3 = iterator.next();
                long n4 = packetLocation3.getTimestamp() - n3;
                if (n4 > 0L) {
                    list.add(packetLocation2);
                    if (n4 > 75L) {
                        packetLocation2 = packetLocation3;
                        break;
                    }
                }
                packetLocation2 = packetLocation3;
            }
            if (list.isEmpty()) {
                list.add(packetLocation2);
            }
            Stream<Double> map = list.stream().map(packetLocation -> MathUtil.getLuckyAura(playerLocation2, packetLocation));
            if (this.values.size() > 10) {
                double average = MathUtil.average(this.values);
                Double n5 = map.min(Comparator.comparingDouble(n -> Math.abs(n - average) + Math.abs(n / 2.0))).orElse(null);
                if (n5 != null) {
                    this.values.add(n5);
                    double deviation = MathUtil.deviation(this.values);
                    if (deviation < 0.1 && average < 0.3) {
                        this.handleViolation(String.format("AVG: %.2f DEV: %.3f", average, deviation), 1.25 - deviation * 10.0);
                    } else {
                        this.decreaseVL(0.1);
                    }
                    this.values.poll();
                }
            } else {
                this.values.add(MathUtil.average(map.collect(Collectors.toList())));
            }
        }
    }
}

