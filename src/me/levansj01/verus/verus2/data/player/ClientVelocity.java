package me.levansj01.verus.verus2.data.player;

public class ClientVelocity {
    private final long timestamp;
    private final double z;
    private final double x;
    private final int ticks;
    private final double y;

    public double getZ() {
        return this.z;
    }

    public ClientVelocity(int n, long l, double d, double d2, double d3) {
        this.ticks = n;
        this.timestamp = l;
        this.x = d;
        this.y = d2;
        this.z = d3;
    }

    public double getX() {
        return this.x;
    }

    public double getRoundedY() {
        return this.round(this.getY());
    }

    public double getRoundedX() {
        return this.round(this.getX());
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public double getY() {
        return this.y;
    }

    public double getRoundedZ() {
        return this.round(this.getZ());
    }

    public int getTicks() {
        return this.ticks;
    }

    public String toString() {
        return "ClientVelocity(ticks=" + this.getTicks() + ", timestamp=" + this.getTimestamp() + ", x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ")";
    }

    private double round(double d) {
        return (double)((int)(d * 8000.0)) / 8000.0;
    }
}
