package me.levansj01.verus.util;

import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.util.java.Vector3d;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class MutableBlockLocation {
    private int z;
    private int y;
    private int x;

    public void setZ(int n) {
        this.z = n;
    }

    public int getZ() {
        return this.z;
    }

    public MutableBlockLocation incrementX() {
        return this.add(1, 0, 0);
    }

    public float getFrictionFactor(Player player, World world) {
        return NMSManager.getInstance().getFrictionFactor(player, world, this);
    }

    public MutableBlockLocation(int n, int n2, int n3) {
        this.x = n;
        this.y = n2;
        this.z = n3;
    }

    public MutableBlockLocation andThen(Vector3d vector3d) {
        this.x = (int) Math.floor((double) vector3d.getX());
        this.y = (int) Math.floor((double) vector3d.getY());
        this.z = (int) Math.floor((double) vector3d.getZ());
        return this;
    }

    public Block getBlock(World world) {
        return world.getBlockAt(this.x, this.y, this.z);
    }

    public Material getType(Player player, World world) {
        return NMSManager.getInstance().getType(player, world, this);
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        n2 = n2 * 59 + this.getX();
        n2 = n2 * 59 + this.getY();
        n2 = n2 * 59 + this.getZ();
        return n2;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int n) {
        this.x = n;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof MutableBlockLocation)) {
            return false;
        }
        MutableBlockLocation mutableBlockLocation = (MutableBlockLocation) object;
        if (!mutableBlockLocation.canEqual(this)) {
            return false;
        }
        if (this.getX() != mutableBlockLocation.getX()) {
            return false;
        }
        if (this.getY() != mutableBlockLocation.getY()) {
            return false;
        }
        return this.getZ() == mutableBlockLocation.getZ();
    }

    public MutableBlockLocation incrementZ() {
        return this.add(0, 0, 1);
    }

    public MutableBlockLocation andThen(int n, int n2, int n3) {
        this.x = n;
        this.y = n2;
        this.z = n3;
        return this;
    }

    protected boolean canEqual(Object object) {
        return object instanceof MutableBlockLocation;
    }

    public String toString() {
        return "MutableBlockLocation(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ")";
    }

    public MutableBlockLocation incrementY() {
        return this.add(0, 1, 0);
    }

    public void setY(int n) {
        this.y = n;
    }

    public static MutableBlockLocation from(Block block) {
        return new MutableBlockLocation(block.getX(), block.getY(), block.getZ());
    }

    public MutableBlockLocation add(int n, int n2, int n3) {
        this.x += n;
        this.y += n2;
        this.z += n3;
        return this;
    }

    public int getX() {
        return this.x;
    }

    public static MutableBlockLocation from(Vector3d vector3d) {
        return new MutableBlockLocation((int) Math.floor(vector3d.getX()), (int) Math.floor((double) vector3d.getY()),
                (int) Math.floor((double) vector3d.getZ()));
    }

    public MaterialData getTypeAndData(Player player, World world) {
        return NMSManager.getInstance().getTypeAndData(player, world, this);
    }
}
