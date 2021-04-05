package me.levansj01.verus.util;

import me.levansj01.verus.compat.Direction;
import me.levansj01.verus.util.java.Vector3d;

import java.util.Objects;

public class Intersection {

    private final Vector3d displacement;
    private final Direction direction;
    private final Vector3d intercept;
    private Double distance;

    public Intersection(Vector3d vector3d, Vector3d vector3d2, Direction direction) {
        this.intercept = vector3d;
        this.displacement = vector3d2;
        this.direction = direction;
    }

    protected boolean canEqual(Object object) {
        return object instanceof Intersection;
    }

    public void setDistance(Double d) {
        this.distance = d;
    }

    public double getDistance() {
        if (this.distance == null) {
            this.distance = this.displacement.length();
        }
        return this.distance;
    }

    public String toString() {
        return "Intersection(intercept=" + this.getIntercept() + ", displacement=" + this.getDisplacement() +
                ", direction=" + this.getDirection() + ", distance=" +
                this.getDistance() + ")";
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        Vector3d vector3d = this.getIntercept();
        n2 = n2 * 59 + (vector3d == null ? 43 : vector3d.hashCode());
        Vector3d vector3d2 = this.getDisplacement();
        n2 = n2 * 59 + (vector3d2 == null ? 43 : vector3d2.hashCode());
        Direction direction = this.getDirection();
        n2 = n2 * 59 + (direction == null ? 43 : direction.hashCode());
        long l = Double.doubleToLongBits(this.getDistance());
        n2 = n2 * 59 + (int) (l >>> 32 ^ l);
        n2 = n2 * 59 + (direction == null ? 43 : ((Object)((Object)direction)).hashCode());
        return n2;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Intersection)) {
            return false;
        }
        Intersection intersection = (Intersection) object;
        if (!intersection.canEqual(this)) {
            return false;
        }
        Vector3d vector3d = this.getIntercept();
        Vector3d vector3d2 = intersection.getIntercept();
        if (!Objects.equals(vector3d, vector3d2)) {
            return false;
        }
        Vector3d vector3d3 = this.getDisplacement();
        Vector3d vector3d4 = intersection.getDisplacement();
        if (!Objects.equals(vector3d3, vector3d4)) {
            return false;
        }
        Direction direction = this.getDirection();
        Direction direction2 = intersection.getDirection();
        if (direction == null ? direction2 != null : !direction.equals(direction2)) {
            return false;
        }
        return Double.compare(this.getDistance(), intersection.getDistance()) == 0;
    }

    public Vector3d getIntercept() {
        return this.intercept;
    }

    public Vector3d getDisplacement() {
        return this.displacement;
    }
}
