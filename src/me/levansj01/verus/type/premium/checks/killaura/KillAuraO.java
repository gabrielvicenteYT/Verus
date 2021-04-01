package me.levansj01.verus.type.premium.checks.killaura;

import java.util.Objects;
import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type=CheckType.KILL_AURA, subType="O", friendlyName="MultiAura", version=CheckVersion.RELEASE, maxViolations=5, unsupportedAtleast=ClientVersion.VERSION1_9)
public class KillAuraO extends PacketCheck {
    private int entityId;
    private boolean ticked;

    public void handle(VPacket vPacket, long l) {
        VPacketPlayInUseEntity vPacketPlayInUseEntity;
        if (vPacket instanceof VPacketPlayInFlying) {
            this.ticked = true;
            this.decreaseVL(0.025);
        } else if (vPacket instanceof VPacketPlayInUseEntity && (vPacketPlayInUseEntity = (VPacketPlayInUseEntity)vPacket).getAction().equals(VPacketPlayInUseEntity.EntityUseAction.ATTACK) && this.playerData.isSurvival() && this.playerData.getTotalTicks() > 200) {
            if (!this.ticked && !Objects.equals(this.entityId, vPacketPlayInUseEntity.getId())) {
                this.handleViolation();
            } else {
                this.decreaseVL(0.25);
            }
            this.entityId = vPacketPlayInUseEntity.getId();
            this.ticked = false;
        }
    }
}
