package me.levansj01.verus.verus2.data.player;

import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.verus2.data.player.ClientVelocity;

public class Velocity {
    private boolean attenuated = false;
    private final double originalY;
    private double y;
    private double z;
    private Double hypotSquaredXZ = null;
    private final int ticks;
    private double x;
    private final long timestamp;

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Deprecated
    public boolean attenuate(boolean bl) {
        boolean bl2;
        if (bl) {
            this.y = 0.0;
        } else if (this.y > 0.0) {
            this.y -= Math.min(this.y, 0.08);
        }
        this.y *= (double) 0.98f;
        this.x *= (double) 0.91f;
        this.z *= (double) 0.91f;
        if (bl) {
            this.x *= (double) 0.6f;
            this.z *= (double) 0.6f;
        }
        this.hypotSquaredXZ = null;
        this.attenuated = true;
        Number[] numberArray = new Number[] { this.x, this.y, this.z };
        if (Math.abs(MathUtil.highestAbs(numberArray)) <= 0.001) {
            bl2 = true;
        } else {
            bl2 = false;
        }
        return bl2;
    }

    public double getHypotSquaredXZ() {
        if (this.hypotSquaredXZ == null) {
            this.hypotSquaredXZ = MathUtil.hypotSquared(new double[] { this.x, this.z });
        }
        return this.hypotSquaredXZ;
    }

    public double getZ() {
        return this.z;
    }

    public double getOriginalY() {
        return this.originalY;
    }

    public boolean isAttenuated() {
        return this.attenuated;
    }

    public ClientVelocity getClient() {
        return new ClientVelocity(this.ticks, this.timestamp, this.x, this.y, this.z);
    }

    public int getTicks() {
        return this.ticks;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public Velocity(int n, long l, double d, double d2, double d3) {
        this.ticks = n;
        this.timestamp = l;
        this.x = d;
        this.y = d2;
        this.originalY = d2;
        this.z = d3;
    }
}
