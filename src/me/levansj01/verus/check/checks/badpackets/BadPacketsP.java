package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(type=CheckType.BAD_PACKETS, subType="P", friendlyName="KillAura", version=CheckVersion.RELEASE, unsupportedAtleast=ClientVersion.VERSION1_9)
public class BadPacketsP extends PacketCheck {
    private boolean interactAt;
    private boolean attack;
    private boolean interact;

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInFlying) {
            this.interact = false;
            this.interactAt = false;
            this.attack = false;
        } else if (vPacket instanceof VPacketPlayInUseEntity) {
            VPacketPlayInUseEntity vPacketPlayInUseEntity = (VPacketPlayInUseEntity)vPacket;
            if (vPacketPlayInUseEntity.getAction().isAttack()) {
                if (!this.attack && (this.interact || this.interactAt)) {
                    String string;
                    String string2;
                    StringBuilder stringBuilder = new StringBuilder().append("Attack [");
                    if (this.interactAt) {
                        string2 = "Interact At ";
                    } else {
                        string2 = "";
                    }
                    StringBuilder stringBuilder2 = stringBuilder.append(string2);
                    if (this.interact) {
                        string = "Interact";
                    } else {
                        string = "";
                    }
                    this.handleViolation(stringBuilder2.append(string).append("]").toString(), 1.0, true);
                    this.interactAt = false;
                    this.interact = false;
                }
                this.attack = true;
            } else if (vPacketPlayInUseEntity.getAction().isInteract()) {
                this.interact = true;
            } else if (vPacketPlayInUseEntity.getAction().isInteractAt()) {
                if (this.interact) {
                    this.handleViolation("Interact", 1.0, true);
                    this.interact = false;
                }
                this.interactAt = true;
            }
        }
    }
}