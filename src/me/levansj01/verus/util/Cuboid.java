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

public class Cuboid
{
    private double z1;
    private double z2;
    private double y1;
    private double x1;
    private double x2;
    private double y2;
    
    public Cuboid(final PacketLocation packetLocation, final PacketLocation packetLocation2) {
        this(Math.min(packetLocation.getX(), packetLocation2.getX()), Math.max(packetLocation.getX(), packetLocation2.getX()), Math.min(packetLocation.getY(), packetLocation2.getY()), Math.max(packetLocation.getY(), packetLocation2.getY()), Math.min(packetLocation.getZ(), packetLocation2.getZ()), Math.max(packetLocation.getZ(), packetLocation2.getZ()));
    }
    
    public Cuboid(final PlayerLocation playerLocation) {
        this(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());
    }
    
    public Cuboid(final BlockPosition blockPosition) {
        this(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    }
    
    public Cuboid(final MutableBlockLocation mutableBlockLocation) {
        this(mutableBlockLocation.getX(), mutableBlockLocation.getY(), mutableBlockLocation.getZ());
    }
    
    public Cuboid(final PacketLocation packetLocation) {
        this(packetLocation.getX(), packetLocation.getY(), packetLocation.getZ());
    }
    
    public Cuboid(final double n, final double n2, final double n3) {
        this(n, n, n2, n2, n3, n3);
    }
    
    public Cuboid(final double x1, final double x2, final double y1, final double y2, final double z1, final double z2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
    }
    
    public Cuboid(final PlayerLocation playerLocation, final PlayerLocation playerLocation2) {
        this(Math.min(playerLocation.getX(), playerLocation2.getX()), Math.max(playerLocation.getX(), playerLocation2.getX()), Math.min(playerLocation.getY(), playerLocation2.getY()), Math.max(playerLocation.getY(), playerLocation2.getY()), Math.min(playerLocation.getZ(), playerLocation2.getZ()), Math.max(playerLocation.getZ(), playerLocation2.getZ()));
    }

    public static Cuboid withLimit(PacketLocation packetLocation, PacketLocation packetLocation2, int range) {
        return packetLocation.distanceSquared(packetLocation2) < (double)range ? new me.levansj01.verus.util.Cuboid(packetLocation, packetLocation2) : new me.levansj01.verus.util.Cuboid(packetLocation2);
    }
    
    @Deprecated
    public static boolean checkBlocks(final Player player, final World world, final Iterable<MutableBlockLocation> iterable, final Predicate<Material> predicate) {
        final Iterator<MutableBlockLocation> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            if (!predicate.test(iterator.next().getType(player, world))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean checkBlocksInternal(final Player player, final World world, final Predicate<MutableBlockLocation> predicate) {
        final int n = (int)Math.floor(this.x1);
        final int n2 = (int)Math.ceil(this.x2);
        final int max = Math.max((int)Math.floor(this.y1), 0);
        final int min = Math.min((int)Math.ceil(this.y2), world.getMaxHeight());
        final int z = (int)Math.floor(this.z1);
        final int n3 = (int)Math.ceil(this.z2);
        final int n4 = (n2 - n) * (n3 - z) * Math.max(1, min - max);
        if (n4 > 50000) {
            VerusLauncher.getPlugin().getLogger().severe(String.format("Tried to check %s blocks for %s in %s (%s, %s, %s) -> (%s, %s, %s) ", n4, player.getName(), world.getName(), n, max, z, n2, min, n3));
            return false;
        }
        final MutableBlockLocation mutableBlockLocation = new MutableBlockLocation(n, max, z);
        while (mutableBlockLocation.getX() < n2) {
            while (mutableBlockLocation.getZ() < n3) {
                while (mutableBlockLocation.getY() < min) {
                    if (!predicate.test(mutableBlockLocation)) {
                        return false;
                    }
                    mutableBlockLocation.incrementY();
                }
                mutableBlockLocation.setY(max);
                mutableBlockLocation.incrementZ();
            }
            mutableBlockLocation.setZ(z);
            mutableBlockLocation.setY(max);
            mutableBlockLocation.incrementX();
        }
        return true;
    }
    
    public Cuboid shrink(final double n, final double n2, final double n3) {
        this.x1 += n;
        this.x2 -= n;
        this.y1 += n2;
        this.y2 -= n2;
        this.z1 += n3;
        this.z2 -= n3;
        return this;
    }
    
    public double cX() {
        return (this.x1 + this.x2) * 0.5;
    }
    
    public double cZ() {
        return (this.z1 + this.z2) * 0.5;
    }
    
    public Cuboid copy() {
        return new Cuboid(this.x1, this.x2, this.y1, this.y2, this.z1, this.z2);
    }
    
    public boolean contains(final double n, final double n2, final double n3) {
        return this.x1 <= n && this.x2 >= n && this.y1 <= n2 && this.y2 >= n2 && this.z1 <= n3 && this.z2 >= n3;
    }
    
    public boolean overlaps(final Cuboid cuboid) {
        return this.x1 <= cuboid.x2 && cuboid.x1 <= this.x1 && this.z1 <= cuboid.z2 && cuboid.z1 <= this.z1;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Deprecated
    public Iterator<MutableBlockLocation> getBlockIterator() {
        return (Iterator<MutableBlockLocation>)new Iterator() {
            final int val$y1 = (int)Math.floor(Cuboid.this.y1);
            final int val$x1 = (int)Math.floor(Cuboid.this.x1);
            final int val$z2 = (int)Math.ceil(Cuboid.this.z2);
            final int val$y2 = (int)Math.ceil(Cuboid.this.y2);
            final int val$x2 = (int)Math.ceil(Cuboid.this.x2);
            final int val$z1 = (int)Math.floor(Cuboid.this.z1);
            int x = this.val$x1;
            int z = this.val$z1;
            int y = this.val$y1;
            boolean next = true;
            
            @Override
            public boolean hasNext() {
                return this.next;
            }
            
            @Override
            public MutableBlockLocation next() {
                try {
                    return new MutableBlockLocation(this.x, this.y, this.z);
                }
                finally {
                    if (this.z < this.val$z2 - 1) {
                        ++this.z;
                    }
                    else if (this.y < this.val$y2 - 1) {
                        ++this.y;
                        this.z = this.val$z1;
                    }
                    else if (this.x < this.val$x2 - 1) {
                        ++this.x;
                        this.y = this.val$y1;
                        this.z = this.val$z1;
                    }
                    else {
                        this.next = false;
                    }
                }
            }
        };
    }
    
    public double getX2() {
        return this.x2;
    }
    
    public void setX2(final double x2) {
        this.x2 = x2;
    }
    
    public double distanceXZ(final double n, final double n2) {
        if (this.containsXZ(n, n2)) {
            return 0.0;
        }
        return MathHelper.sqrt_double(Math.min(Math.pow(n - this.x1, 2.0), Math.pow(n - this.x2, 2.0)) + Math.min(Math.pow(n2 - this.z1, 2.0), Math.pow(n2 - this.z2, 2.0)));
    }
    
    public Cuboid move(final double n, final double n2, final double n3) {
        this.x1 += n;
        this.x2 += n;
        this.y1 += n2;
        this.y2 += n2;
        this.z1 += n3;
        this.z2 += n3;
        return this;
    }
    
    public List<BlockPosition> getPositions() {
        final int n = (int)Math.floor(this.x1);
        final int n2 = (int)Math.ceil(this.x2);
        final int n3 = (int)Math.floor(this.y1);
        final int n4 = (int)Math.ceil(this.y2);
        final int n5 = (int)Math.floor(this.z1);
        final int n6 = (int)Math.ceil(this.z2);
        final ArrayList<BlockPosition> list = new ArrayList<BlockPosition>();
        list.add(new BlockPosition(n, n3, n5));
        for (int i = n; i < n2; ++i) {
            for (int j = n3; j < n4; ++j) {
                for (int k = n5; k < n6; ++k) {
                    list.add(new BlockPosition(i, j, k));
                }
            }
        }
        return list;
    }
    
    public double getZ2() {
        return this.z2;
    }
    
    public void setZ2(final double z2) {
        this.z2 = z2;
    }
    
    public Cuboid expand(final double n, final double n2, final double n3) {
        this.x1 -= n;
        this.x2 += n;
        this.y1 -= n2;
        this.y2 += n2;
        this.z1 -= n3;
        this.z2 += n3;
        return this;
    }
    
    @Deprecated
    public boolean _checkBlocks(final Player player, final World world, final Predicate<Material> predicate) {
        return checkBlocks(player, world, this.getBlocks(), predicate);
    }
    
    public double getY2() {
        return this.y2;
    }
    
    public void setY2(final double y2) {
        this.y2 = y2;
    }
    
    public double distanceXZ(final PlayerLocation playerLocation) {
        return this.distanceXZ(playerLocation.getX(), playerLocation.getZ());
    }
    
    public Cuboid fixY() {
        this.y1 = Math.max(0.0, this.y1);
        this.y2 = Math.min(256.0, this.y2);
        return this;
    }
    
    @Deprecated
    public List<Block> _getBlocks(final World world) {
        final int n = (int)Math.floor(this.x1);
        final int n2 = (int)Math.ceil(this.x2);
        final int n3 = (int)Math.floor(this.y1);
        final int n4 = (int)Math.ceil(this.y2);
        final int n5 = (int)Math.floor(this.z1);
        final int n6 = (int)Math.ceil(this.z2);
        final ArrayList<Block> list = new ArrayList<Block>();
        list.add(world.getBlockAt(n, n3, n5));
        for (int i = n; i < n2; ++i) {
            for (int j = n3; j < n4; ++j) {
                for (int k = n5; k < n6; ++k) {
                    list.add(world.getBlockAt(i, j, k));
                }
            }
        }
        return list;
    }
    
    public double cY() {
        return (this.y1 + this.y2) * 0.5;
    }
    
    public boolean containsXZ(final double n, final double n2) {
        return this.x1 <= n && this.x2 >= n && this.z1 <= n2 && this.z2 >= n2;
    }
    
    public boolean checkBlocks(final Player player, final World world, final Predicate<Material> predicate) {
        return this.checkBlocksInternal(player, world, mutableBlockLocation -> predicate.test(mutableBlockLocation.getType(player, world)));
    }
    
    public double getX1() {
        return this.x1;
    }
    
    public void setX1(final double x1) {
        this.x1 = x1;
    }
    
    public boolean contains(final PlayerLocation playerLocation) {
        return this.contains(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());
    }
    
    public Cuboid combine(final Cuboid cuboid) {
        return new Cuboid(Math.min(this.x1, cuboid.x1), Math.max(this.x2, cuboid.x2), Math.min(this.y1, cuboid.y1), Math.max(this.y2, cuboid.y2), Math.min(this.z1, cuboid.z1), Math.max(this.z2, cuboid.z2));
    }
    
    public boolean containsBlock(final World world, final PacketLocation packetLocation) {
        final int n = (int)Math.floor(packetLocation.getX());
        final int n2 = (int)Math.floor(packetLocation.getY());
        final int n3 = (int)Math.floor(packetLocation.getZ());
        final int n4 = (int)Math.floor(this.x1);
        final int n5 = (int)Math.ceil(this.x2);
        final int max = Math.max((int)Math.floor(this.y1), 0);
        final int min = Math.min((int)Math.ceil(this.y2), world.getMaxHeight());
        final int n6 = (int)Math.floor(this.z1);
        final int n7 = (int)Math.ceil(this.z2);
        return n4 <= n && n5 > n && max <= n2 && min > n2 && n6 <= n3 && n7 > n3;
    }
    
    public double getZ1() {
        return this.z1;
    }
    
    public void setZ1(final double z1) {
        this.z1 = z1;
    }
    
    public boolean containsXZ(final PlayerLocation playerLocation) {
        return this.containsXZ(playerLocation.getX(), playerLocation.getZ());
    }
    
    public boolean rayTraceInternal(final Player player, final World world, final Predicate<MutableBlockLocation> predicate) {
        final double hypot = MathUtil.hypot(this.x2 - this.x1, this.y2 - this.y1, this.z2 - this.z1);
        final double lowest = MathUtil.lowest((this.x2 - this.x1) / hypot, (this.y2 - this.y1) / hypot, (this.z2 - this.z1) / hypot);
        int n = (int)Math.ceil(lowest / hypot);
        if (n > 500) {
            VerusLauncher.getPlugin().getLogger().severe(String.format("Tried to raytrace %s blocks for %s in %s (%s, %s, %s) -> (%s, %s, %s) ", n, player.getName(), world.getName(), this.x1, this.y1, this.z1, this.x2, this.y2, this.z2));
            return false;
        }
        final Vector3d vector3d = new Vector3d(this.x1, this.y1, this.z1);
        final MutableBlockLocation from = MutableBlockLocation.from(vector3d);
        while (n-- >= 0) {
            if (!predicate.test(from)) {
                return false;
            }
            vector3d.add(lowest);
            from.andThen(vector3d);
        }
        return true;
    }
    
    public Cuboid add(final Cuboid cuboid) {
        this.x1 += cuboid.x1;
        this.x2 += cuboid.x2;
        this.y1 += cuboid.y1;
        this.y2 += cuboid.y2;
        this.z1 += cuboid.z1;
        this.z2 += cuboid.z2;
        return this;
    }
    
    public double getY1() {
        return this.y1;
    }
    
    public void setY1(final double y1) {
        this.y1 = y1;
    }
    
    @Deprecated
    public Iterable<MutableBlockLocation> getBlocks() {
        return this::getBlockIterator;
    }
    
    public boolean phase(final Cuboid cuboid) {
        return (this.x1 <= cuboid.x1 && this.x2 >= cuboid.x2) || (this.z1 <= cuboid.z1 && this.z2 >= cuboid.z2);
    }
}

