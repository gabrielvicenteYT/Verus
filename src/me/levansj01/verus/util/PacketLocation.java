package me.levansj01.verus.util;

public class PacketLocation {

    private long nextOffset;
    protected double x;
    protected double z;
    private long timestamp;
    protected double y;

    public void setY(double d) {
        this.y = d;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public PacketLocation add(double d, double d2, double d3) {
        return new PacketLocation(this.timestamp, this.nextOffset, this.x + d, this.y + d2, this.z + d3);
    }

    public void setX(double d) {
        this.x = d;
    }

    public long getTime() {
        return this.nextOffset - this.timestamp;
    }

    public PacketLocation(long l, long l2, double d, double d2, double d3) {
        this.timestamp = l;
        this.nextOffset = l2;
        this.x = d;
        this.y = d2;
        this.z = d3;
    }

    protected boolean canEqual(Object object) {
        return object instanceof PacketLocation;
    }

    public void setNextOffset(long l) {
        this.nextOffset = l;
    }

    public double getZ() {
        return this.z;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof PacketLocation)) {
            return false;
        }
        PacketLocation packetLocation = (PacketLocation)object;
        if (!packetLocation.canEqual(this)) {
            return false;
        }
        if (this.getTimestamp() != packetLocation.getTimestamp()) {
            return false;
        }
        if (this.getNextOffset() != packetLocation.getNextOffset()) {
            return false;
        }
        if (Double.compare(this.getX(), packetLocation.getX()) != 0) {
            return false;
        }
        if (Double.compare(this.getY(), packetLocation.getY()) != 0) {
            return false;
        }
        return Double.compare(this.getZ(), packetLocation.getZ()) == 0;
    }

    public long getNextOffset() {
        return this.nextOffset;
    }

    public double getY() {
        return this.y;
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        long l = this.getTimestamp();
        n2 = n2 * 59 + (int)(l >>> 32 ^ l);
        long l2 = this.getNextOffset();
        n2 = n2 * 59 + (int)(l2 >>> 32 ^ l2);
        long l3 = Double.doubleToLongBits(this.getX());
        n2 = n2 * 59 + (int)(l3 >>> 32 ^ l3);
        long l4 = Double.doubleToLongBits(this.getY());
        n2 = n2 * 59 + (int)(l4 >>> 32 ^ l4);
        long l5 = Double.doubleToLongBits(this.getZ());
        n2 = n2 * 59 + (int)(l5 >>> 32 ^ l5);
        return n2;
    }

    public Cuboid hitbox() {
        return new Cuboid(this.x, this.y, this.z).add(new Cuboid(-0.3, 0.3, 0.0, 1.8, -0.3, 0.3)).expand(0.1, 0.1, 0.1);
    }

    public double getX() {
        return this.x;
    }

    public void setTimestamp(long l) {
        this.timestamp = l;
    }

    public void setZ(double d) {
        this.z = d;
    }

    public PacketLocation clone() {
        return new PacketLocation(this.timestamp, this.nextOffset, this.x, this.y, this.z);
    }
}

