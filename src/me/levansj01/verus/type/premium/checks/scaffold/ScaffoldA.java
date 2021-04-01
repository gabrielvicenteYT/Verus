package me.levansj01.verus.type.premium.checks.scaffold;


import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.BlockPosition;
import me.levansj01.verus.compat.Direction;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.World;
import org.bukkit.block.Block;

@CheckInfo(friendlyName="Scaffold", type=CheckType.SCAFFOLD, subType="A", version=CheckVersion.RELEASE, minViolations=-1.0, maxViolations=5, schematica=true)
public class ScaffoldA extends PacketCheck {

    private void handle(World world, BlockPosition blockPosition) {
        Block block = world.getBlockAt(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
        if (!MaterialList.FLAT.contains(block.getType())) {
            this.handleViolation(String.valueOf(block.getType()));
        }
    }

    public void handle(VPacket vPacket, long l) {
        VPacketPlayInBlockPlace vPacketPlayInBlockPlace;
        if (StorageEngine.getInstance().getVerusConfig().isSchemAlerts() && vPacket instanceof VPacketPlayInBlockPlace && (vPacketPlayInBlockPlace = (VPacketPlayInBlockPlace)vPacket).getFace() != 255) {
            Direction direction = Direction.values()[vPacketPlayInBlockPlace.getFace()];
            if (direction == Direction.UP && (double)vPacketPlayInBlockPlace.getBlockY() == 0.0) {
                World world = this.player.getWorld();
                BlockPosition blockPosition = vPacketPlayInBlockPlace.getPosition();
                this.run(() -> this.handle(world, blockPosition));
            } else {
                this.decreaseVL(0.05);
            }
        }
    }
}
