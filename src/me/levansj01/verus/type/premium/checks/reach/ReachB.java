package me.levansj01.verus.type.premium.checks.reach;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;
import org.bukkit.entity.Player;

@CheckInfo(type=CheckType.REACH, subType="B", friendlyName="Reach", version=CheckVersion.RELEASE, unsupportedAtleast=ClientVersion.VERSION1_9, maxViolations=4, logData=true)
public class ReachB
        extends PacketCheck {
    public static final double MAX_YL = -0.1001001001001001;
    public static final double MAX_X = 0.4004004004004004;
    public static final double V = 0.999;
    public static final double MAX_YH = 1.901901901901902;

    public void handle(VPacket vPacket, long l) {
        VPacketPlayInUseEntity vPacketPlayInUseEntity;
        if (vPacket instanceof VPacketPlayInUseEntity && (vPacketPlayInUseEntity = (VPacketPlayInUseEntity)vPacket).getAction() == VPacketPlayInUseEntity.EntityUseAction.INTERACT_AT) {
            double d = vPacketPlayInUseEntity.getBodyX();
            double d2 = vPacketPlayInUseEntity.getBodyY();
            double d3 = vPacketPlayInUseEntity.getBodyZ();
            if ((Math.abs(d) > 0.4004004004004004 || Math.abs(d3) > 0.4004004004004004 || d2 > 1.901901901901902 || d2 < -0.1001001001001001) && vPacketPlayInUseEntity.getEntity(this.player.getWorld()) instanceof Player) {
                this.handleViolation(String.format("X: %s Y: %s Z: %s", d, d2, d3), 1.0, true);
            }
        }
    }
}
