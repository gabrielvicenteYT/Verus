package me.levansj01.verus.verus2.data.player;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import me.levansj01.verus.verus2.data.player.TickerType;

public class TickerMap {
    private final AtomicInteger[] timers = new AtomicInteger[TickerType.values().length];

    public void reset(TickerType tickerType) {
        this.set(tickerType, 0);
    }

    public int increment(TickerType tickerType) {
        return this.timers[tickerType.ordinal()].incrementAndGet();
    }

    public int add(TickerType tickerType, int n) {
        return this.timers[tickerType.ordinal()].addAndGet(n);
    }

    public void set(TickerType tickerType, int n) {
        this.timers[tickerType.ordinal()].set(n);
    }

    public int get(TickerType tickerType) {
        return this.timers[tickerType.ordinal()].get();
    }

    public TickerMap() {
        int n = 0;
        for (TickerType tickerType : TickerType.values()) {
            this.timers[n] = new AtomicInteger(tickerType.getStarting());
            ++n;

        }
    }

    public void incrementAll() {
        Arrays.stream(TickerType.getAutos()).forEach(this::increment);
    }

}
