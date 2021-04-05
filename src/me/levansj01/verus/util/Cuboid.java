package me.levansj01.verus.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.compat.BlockPosition;
import me.levansj01.verus.compat.Direction;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.util.Intersection;
import me.levansj01.verus.util.MutableBlockLocation;
import me.levansj01.verus.util.PlayerLocation;
import me.levansj01.verus.util.java.MathHelper;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.java.Vector3d;
import me.levansj01.verus.verus2.data.player.PacketLocation;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Cuboid {
    private double x1;
    private double y1;
    private double z2;
    private double y2;
    private double x2;
    private double z1;

    @Deprecated
    public Iterator<MutableBlockLocation> getBlockIterator() {
        int var1 = (int)Math.floor(this.x1);
        int var2 = (int)Math.ceil(this.x2);
        int var3 = (int)Math.floor(this.y1);
        int var4 = (int)Math.ceil(this.y2);
        int var5 = (int)Math.floor(this.z1);
        int var6 = (int)Math.ceil(this.z2);
        return new Cuboid1(this, var1, var3, var5, var6, var4, var2);
    }

    public double getZ2() {
        return this.z2;
    }

    @Deprecated
    public boolean _checkBlocks(Player player, World world, Predicate<Material> predicate) {
        return Cuboid.checkBlocks(player, world, this.getBlocks(), predicate);
    }

    public List<BlockPosition> getPositions() {
        int n = (int) Math.floor((double) this.x1);
        int n2 = (int) Math.ceil((double) this.x2);
        int n3 = (int) Math.floor((double) this.y1);
        int n4 = (int) Math.ceil((double) this.y2);
        int n5 = (int) Math.floor((double) this.z1);
        int n6 = (int) Math.ceil((double) this.z2);
        ArrayList arrayList = new ArrayList();
        arrayList.add((Object) new BlockPosition(n, n3, n5));
        for (int i = n; i < n2; ++i) {
            for (int j = n3; j < n4; ++j) {
                for (int k = n5; k < n6; ++k) {
                    arrayList.add((Object) new BlockPosition(i, j, k));
                }
            }
        }
        return arrayList;
    }

    public boolean phase(Cuboid cuboid) {
        return this.x1 <= cuboid.x1 && this.x2 >= cuboid.x2 || this.z1 <= cuboid.z1 && this.z2 >= cuboid.z2;
    }

    public Cuboid(double d, double d2, double d3, double d4, double d5, double d6) {
        this.x1 = d;
        this.x2 = d2;
        this.y1 = d3;
        this.y2 = d4;
        this.z1 = d5;
        this.z2 = d6;
    }

    public boolean containsXZ(PlayerLocation playerLocation) {
        return this.containsXZ(playerLocation.getX(), playerLocation.getZ());
    }

    public boolean contains(Vector3d vector3d) {
        if (vector3d == null) {
            return false;
        }
        return this.contains(vector3d.getX(), vector3d.getY(), vector3d.getZ());
    }

    public double cX() {
        return (this.x1 + this.x2) * 0.5;
    }

    public Cuboid copy() {
        return new Cuboid(this.x1, this.x2, this.y1, this.y2, this.z1, this.z2);
    }

    public Cuboid add(double d, double d2, double d3, double d4, double d5, double d6) {
        this.x1 += d;
        this.x2 += d2;
        this.y1 += d3;
        this.y2 += d4;
        this.z1 += d5;
        this.z2 += d6;
        return this;
    }

    public Cuboid setValues(PlayerLocation playerLocation) {
        return this.setValues(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());
    }

    public void setX2(double d) {
        this.x2 = d;
    }

    public double getX2() {
        return this.x2;
    }

    public double cY() {
        return (this.y1 + this.y2) * 0.5;
    }

    public Cuboid _withLimit(PlayerLocation playerLocation, PlayerLocation playerLocation2, int n) {
        return playerLocation.distanceSquared(playerLocation2) < (double) n
                ? this.setValues(playerLocation, playerLocation2)
                : this.setValues(playerLocation2);
    }

    public double getY1() {
        return this.y1;
    }

    private static boolean lambda$checkBlocks$0(Predicate predicate, Player player, World world,
            MutableBlockLocation mutableBlockLocation) {
        return predicate.test((Object) mutableBlockLocation.getType(player, world));
    }

    public boolean checkBlocksInternal(Player player, World world, Predicate<MutableBlockLocation> predicate) {
        int n = (int) Math.floor((double) this.x1);
        int n2 = (int) Math.ceil((double) this.x2);
        int n3 = Math.max((int) ((int) Math.floor((double) this.y1)), (int) 0);
        int n4 = Math.min((int) ((int) Math.ceil((double) this.y2)), (int) world.getMaxHeight());
        int n5 = (int) Math.floor((double) this.z1);
        int n6 = (int) Math.ceil((double) this.z2);
        int n7 = (n2 - n) * (n6 - n5) * Math.max((int) 1, (int) (n4 - n3));
        if (n7 > 250) {
            VerusLauncher.getPlugin().getLogger()
                    .severe(String.format(
                            (String) "Tried to check %s blocks for %s in %s (%s, %s, %s) -> (%s, %s, %s) ",
                            (Object[]) new Object[] { n7, player.getName(), world.getName(), n, n3, n5, n2, n4, n6 }));
            return false;
        }
        boolean bl = n4 - n3 > 2;
        MutableBlockLocation mutableBlockLocation = new MutableBlockLocation(n, n3, n5);
        while (mutableBlockLocation.getX() < n2) {
            while (mutableBlockLocation.getZ() < n6) {
                if (NMSManager.getInstance().isLoaded(world, mutableBlockLocation.getX(),
                        mutableBlockLocation.getZ())) {
                    while (mutableBlockLocation.getY() < n4) {
                        if (!predicate.test(mutableBlockLocation)) {
                            return false;
                        }
                        mutableBlockLocation.incrementY();
                    }
                }
                mutableBlockLocation.setY(n3);
                mutableBlockLocation.incrementZ();
            }
            mutableBlockLocation.setZ(n5);
            mutableBlockLocation.setY(n3);
            mutableBlockLocation.incrementX();
        }
        return true;
    }

    private Cuboid(PacketLocation packetLocation, PacketLocation packetLocation2) {
        this(Math.min((double) packetLocation.getX(), (double) packetLocation2.getX()),
                Math.max((double) packetLocation.getX(), (double) packetLocation2.getX()),
                Math.min((double) packetLocation.getY(), (double) packetLocation2.getY()),
                Math.max((double) packetLocation.getY(), (double) packetLocation2.getY()),
                Math.min((double) packetLocation.getZ(), (double) packetLocation2.getZ()),
                Math.max((double) packetLocation.getZ(), (double) packetLocation2.getZ()));
    }

    public double cZ() {
        return (this.z1 + this.z2) * 0.5;
    }

    public Cuboid(BlockPosition blockPosition) {
        this(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    }

    public boolean contains(PlayerLocation playerLocation) {
        return this.contains(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());
    }

    public double getY2() {
        return this.y2;
    }

    public void setZ2(double d) {
        this.z2 = d;
    }

    public Cuboid shrink(double d, double d2, double d3) {
        this.x1 += d;
        this.x2 -= d;
        this.y1 += d2;
        this.y2 -= d2;
        this.z1 += d3;
        this.z2 -= d3;
        return this;
    }

    public Cuboid move(double d, double d2, double d3) {
        this.x1 += d;
        this.x2 += d;
        this.y1 += d2;
        this.y2 += d2;
        this.z1 += d3;
        this.z2 += d3;
        return this;
    }

    public double getZ1() {
        return this.z1;
    }

    public double distanceXZ(double d, double d2) {
        if (this.containsXZ(d, d2)) {
            return 0.0;
        }
        double d3 = Math.min((double) Math.pow((double) (d - this.x1), (double) 2.0),
                (double) Math.pow((double) (d - this.x2), (double) 2.0));
        double d4 = Math.min((double) Math.pow((double) (d2 - this.z1), (double) 2.0),
                (double) Math.pow((double) (d2 - this.z2), (double) 2.0));
        return MathHelper.sqrt_double((double) (d3 + d4));
    }

    public Cuboid fixY() {
        this.y1 = Math.max((double) 0.0, (double) this.y1);
        this.y2 = Math.min((double) 256.0, (double) this.y2);
        return this;
    }

    public Intersection calculateIntercept(Vector3d vector3d, Vector3d vector3d2) {
        Vector3d vector3d3 = vector3d.getIntermediateWithXValue(vector3d2, this.x1);
        Vector3d vector3d4 = vector3d.getIntermediateWithXValue(vector3d2, this.x2);
        Vector3d vector3d5 = vector3d.getIntermediateWithYValue(vector3d2, this.y1);
        Vector3d vector3d6 = vector3d.getIntermediateWithYValue(vector3d2, this.y2);
        Vector3d vector3d7 = vector3d.getIntermediateWithZValue(vector3d2, this.z1);
        Vector3d vector3d8 = vector3d.getIntermediateWithZValue(vector3d2, this.z2);
        if (!this.containsYZ(vector3d3)) {
            vector3d3 = null;
        }
        if (!this.containsYZ(vector3d4)) {
            vector3d4 = null;
        }
        if (!this.containsXZ(vector3d5)) {
            vector3d5 = null;
        }
        if (!this.containsXZ(vector3d6)) {
            vector3d6 = null;
        }
        if (!this.containsXY(vector3d7)) {
            vector3d7 = null;
        }
        if (!this.containsXY(vector3d8)) {
            vector3d8 = null;
        }
        Vector3d vector3d9 = null;
        if (vector3d3 != null) {
            vector3d9 = vector3d3;
        }
        if (vector3d4 != null
                && (vector3d9 == null || vector3d.distanceSquared(vector3d4) < vector3d.distanceSquared(vector3d9))) {
            vector3d9 = vector3d4;
        }
        if (vector3d5 != null
                && (vector3d9 == null || vector3d.distanceSquared(vector3d5) < vector3d.distanceSquared(vector3d9))) {
            vector3d9 = vector3d5;
        }
        if (vector3d6 != null
                && (vector3d9 == null || vector3d.distanceSquared(vector3d6) < vector3d.distanceSquared(vector3d9))) {
            vector3d9 = vector3d6;
        }
        if (vector3d7 != null
                && (vector3d9 == null || vector3d.distanceSquared(vector3d7) < vector3d.distanceSquared(vector3d9))) {
            vector3d9 = vector3d7;
        }
        if (vector3d8 != null
                && (vector3d9 == null || vector3d.distanceSquared(vector3d8) < vector3d.distanceSquared(vector3d9))) {
            vector3d9 = vector3d8;
        }
        if (vector3d9 == null) {
            return null;
        }
        Direction direction = vector3d9 == vector3d3 ? Direction.WEST
                : (vector3d9 == vector3d4 ? Direction.EAST
                        : (vector3d9 == vector3d5 ? Direction.DOWN
                                : (vector3d9 == vector3d6 ? Direction.UP
                                        : (vector3d9 == vector3d7 ? Direction.NORTH : Direction.SOUTH))));
        return new Intersection(vector3d9, vector3d9.copy().subtract(vector3d), direction);
    }

    public void setY2(double d) {
        this.y2 = d;
    }

    public boolean contains(double d, double d2, double d3) {
        return this.x1 <= d && this.x2 >= d && this.y1 <= d2 && this.y2 >= d2 && this.z1 <= d3 && this.z2 >= d3;
    }

    public Cuboid() {
        this(0.0, 0.0, 0.0);
    }

    public boolean containsXY(Vector3d vector3d) {
        if (vector3d == null) {
            return false;
        }
        return this.containsXY(vector3d.getX(), vector3d.getY());
    }

    public Cuboid(PlayerLocation playerLocation) {
        this(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());
    }

    public Cuboid(PacketLocation packetLocation) {
        this(packetLocation.getX(), packetLocation.getY(), packetLocation.getZ());
    }

    public boolean containsBlock(World world, BlockPosition blockPosition) {
        return this.containsBlock(world, blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    }

    public boolean containsYZ(Vector3d vector3d) {
        if (vector3d == null) {
            return false;
        }
        return this.containsYZ(vector3d.getY(), vector3d.getZ());
    }

    public Cuboid(double d, double d2, double d3) {
        this(d, d, d2, d2, d3, d3);
    }

    public Cuboid(MutableBlockLocation mutableBlockLocation) {
        this(mutableBlockLocation.getX(), mutableBlockLocation.getY(), mutableBlockLocation.getZ());
    }

    public boolean containsXZ(double d, double d2) {
        return this.x1 <= d && this.x2 >= d && this.z1 <= d2 && this.z2 >= d2;
    }

    public boolean rayTraceInternal(Player player, World world, Predicate<MutableBlockLocation> predicate) {
        double d = MathUtil.hypot((double[]) new double[] { this.x2 - this.x1, this.y2 - this.y1, this.z2 - this.z1 });
        double d2 = (this.x2 - this.x1) / d;
        double d3 = (this.y2 - this.y1) / d;
        double d4 = (this.z2 - this.z1) / d;
        double d5 = MathUtil.lowest((Number[]) new Number[] { d2, d3, d4 });
        int n = (int) Math.ceil((double) (d5 / d));
        if (n > 100) {
            VerusLauncher.getPlugin().getLogger()
                    .severe(String.format(
                            (String) "Tried to raytrace %s blocks for %s in %s (%s, %s, %s) -> (%s, %s, %s) ",
                            (Object[]) new Object[] { n, player.getName(), world.getName(), this.x1, this.y1, this.z1,
                                    this.x2, this.y2, this.z2 }));
            return false;
        }
        Vector3d vector3d = new Vector3d(this.x1, this.y1, this.z1);
        MutableBlockLocation mutableBlockLocation = MutableBlockLocation.from((Vector3d) vector3d);
        while (n-- >= 0) {
            if (!predicate.test(mutableBlockLocation)) {
                return false;
            }
            vector3d.add(d5);
            mutableBlockLocation.andThen(vector3d);
        }
        return true;
    }

    @Deprecated
    public Iterable<MutableBlockLocation> getBlocks() {
        return this::getBlockIterator;
    }

    public void setX1(double d) {
        this.x1 = d;
    }

    public Cuboid setValues(PlayerLocation playerLocation, PlayerLocation playerLocation2) {
        return this.setValues(Math.min((double) playerLocation.getX(), (double) playerLocation2.getX()),
                Math.max((double) playerLocation.getX(), (double) playerLocation2.getX()),
                Math.min((double) playerLocation.getY(), (double) playerLocation2.getY()),
                Math.max((double) playerLocation.getY(), (double) playerLocation2.getY()),
                Math.min((double) playerLocation.getZ(), (double) playerLocation2.getZ()),
                Math.max((double) playerLocation.getZ(), (double) playerLocation2.getZ()));
    }

    public boolean containsBlock(World world, int n, int n2, int n3) {
        int n4 = (int) Math.floor((double) this.x1);
        int n5 = (int) Math.ceil((double) this.x2);
        int n6 = Math.max((int) ((int) Math.floor((double) this.y1)), (int) 0);
        int n7 = Math.min((int) ((int) Math.ceil((double) this.y2)), (int) world.getMaxHeight());
        int n8 = (int) Math.floor((double) this.z1);
        int n9 = (int) Math.ceil((double) this.z2);
        return n4 <= n && n5 > n && n6 <= n2 && n7 > n2 && n8 <= n3 && n9 > n3;
    }

    public double getX1() {
        return this.x1;
    }

    public void setZ1(double d) {
        this.z1 = d;
    }

    public Cuboid expand(double d, double d2, double d3) {
        this.x1 -= d;
        this.x2 += d;
        this.y1 -= d2;
        this.y2 += d2;
        this.z1 -= d3;
        this.z2 += d3;
        return this;
    }

    @Deprecated
    public List<Block> _getBlocks(World world) {
        int n = (int) Math.floor((double) this.x1);
        int n2 = (int) Math.ceil((double) this.x2);
        int n3 = (int) Math.floor((double) this.y1);
        int n4 = (int) Math.ceil((double) this.y2);
        int n5 = (int) Math.floor((double) this.z1);
        int n6 = (int) Math.ceil((double) this.z2);
        ArrayList arrayList = new ArrayList();
        arrayList.add((Object) world.getBlockAt(n, n3, n5));
        for (int i = n; i < n2; ++i) {
            for (int j = n3; j < n4; ++j) {
                for (int k = n5; k < n6; ++k) {
                    arrayList.add((Object) world.getBlockAt(i, j, k));
                }
            }
        }
        return arrayList;
    }

    public boolean containsXZ(Vector3d vector3d) {
        if (vector3d == null) {
            return false;
        }
        return this.containsXZ(vector3d.getX(), vector3d.getZ());
    }

    public static Cuboid withLimit(PacketLocation packetLocation, PacketLocation packetLocation2, int n) {
        return packetLocation.distanceSquared(packetLocation2) < (double) n
                ? new Cuboid(packetLocation, packetLocation2)
                : new Cuboid(packetLocation2);
    }

    public void setY1(double d) {
        this.y1 = d;
    }

    public Cuboid combine(Cuboid cuboid) {
        return new Cuboid(Math.min((double) this.x1, (double) cuboid.x1),
                Math.max((double) this.x2, (double) cuboid.x2), Math.min((double) this.y1, (double) cuboid.y1),
                Math.max((double) this.y2, (double) cuboid.y2), Math.min((double) this.z1, (double) cuboid.z1),
                Math.max((double) this.z2, (double) cuboid.z2));
    }

    public boolean containsXY(double d, double d2) {
        return this.x1 <= d && this.x2 >= d && this.y1 <= d2 && this.y2 >= d2;
    }

    public static Cuboid withLimit(PlayerLocation playerLocation, PlayerLocation playerLocation2, int n) {
        return playerLocation.distanceSquared(playerLocation2) < (double) n
                ? new Cuboid(playerLocation, playerLocation2)
                : new Cuboid(playerLocation2);
    }

    public double distanceXZ(PlayerLocation playerLocation) {
        return this.distanceXZ(playerLocation.getX(), playerLocation.getZ());
    }

    public boolean overlaps(Cuboid cuboid) {
        return !(this.x1 > cuboid.x2 || cuboid.x1 > this.x1 || this.z1 > cuboid.z2 || cuboid.z1 > this.z1);
    }

    public Cuboid setValues(double d, double d2, double d3) {
        return this.setValues(d, d, d2, d2, d3, d3);
    }

    @Deprecated
    public static boolean checkBlocks(Player player, World world, Iterable<MutableBlockLocation> iterable,
            Predicate<Material> predicate) {
        for (MutableBlockLocation mutableBlockLocation : iterable) {
            Material material = mutableBlockLocation.getType(player, world);
            if (predicate.test(material))
                continue;
            return false;
        }
        return true;
    }

    public boolean containsBlock(World world, PacketLocation packetLocation) {
        int n = (int) Math.floor((double) packetLocation.getX());
        int n2 = (int) Math.floor((double) packetLocation.getY());
        int n3 = (int) Math.floor((double) packetLocation.getZ());
        return this.containsBlock(world, n, n2, n3);
    }

    public Cuboid add(Cuboid cuboid) {
        return this.add(cuboid.getX1(), cuboid.getX2(), cuboid.getY1(), cuboid.getY2(), cuboid.z1, cuboid.z2);
    }

    public Cuboid setValues(double d, double d2, double d3, double d4, double d5, double d6) {
        this.x1 = d;
        this.x2 = d2;
        this.y1 = d3;
        this.y2 = d4;
        this.z1 = d5;
        this.z2 = d6;
        return this;
    }

    private Cuboid(PlayerLocation playerLocation, PlayerLocation playerLocation2) {
        this(Math.min((double) playerLocation.getX(), (double) playerLocation2.getX()),
                Math.max((double) playerLocation.getX(), (double) playerLocation2.getX()),
                Math.min((double) playerLocation.getY(), (double) playerLocation2.getY()),
                Math.max((double) playerLocation.getY(), (double) playerLocation2.getY()),
                Math.min((double) playerLocation.getZ(), (double) playerLocation2.getZ()),
                Math.max((double) playerLocation.getZ(), (double) playerLocation2.getZ()));
    }

    public boolean containsYZ(double d, double d2) {
        return this.y1 <= d && this.y2 >= d && this.z1 <= d2 && this.z2 >= d2;
    }

    class Cuboid1 implements Iterator {
        final int val$z1;
        final int val$x1;
        final Cuboid this$0;
        final int val$x2;
        int z;
        final int val$z2;
        boolean next;
        int x;
        final int val$y2;
        int y;
        final int val$y1;

        Cuboid1(Cuboid cuboid, int n, int n2, int n3, int n4, int n5, int n6) {
            this.this$0 = cuboid;
            this.val$x1 = n;
            this.val$y1 = n2;
            this.val$z1 = n3;
            this.val$z2 = n4;
            this.val$y2 = n5;
            this.val$x2 = n6;
            this.x = this.val$x1;
            this.y = this.val$y1;
            this.z = this.val$z1;
            this.next = true;
        }

        public boolean hasNext() {
            return this.next;
        }

        public MutableBlockLocation next() {
            try {
                MutableBlockLocation mutableBlockLocation = new MutableBlockLocation(this.x, this.y, this.z);
                return mutableBlockLocation;
            } finally {
                if (this.z < this.val$z2 - 1) {
                    ++this.z;
                } else if (this.y < this.val$y2 - 1) {
                    ++this.y;
                    this.z = this.val$z1;
                } else if (this.x < this.val$x2 - 1) {
                    ++this.x;
                    this.y = this.val$y1;
                    this.z = this.val$z1;
                } else {
                    this.next = false;
                }
            }
        }
    }
}
