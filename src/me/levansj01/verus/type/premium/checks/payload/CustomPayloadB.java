package me.levansj01.verus.type.premium.checks.payload;

import com.google.common.collect.ImmutableMap;
import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;

import java.util.Arrays;
import java.util.Map;

@CheckInfo(type=CheckType.PAYLOAD, subType="B", friendlyName="Hacked Client", version=CheckVersion.RELEASE, logData=true, maxViolations=1)
public class CustomPayloadB extends PacketCheck {
    private static final Map<String, String> map = new ImmutableMap.Builder().put("Vanilla", "Jigsaw").put("\u0007Vanilla", "Jigsaw").put("Synergy", "Synergy").put("\u0007Synergy", "Synergy").put("Created By ", "Vape").build();

    private static String handle(String string) {
        return String.format("Brand: %s", string);
    }

    public void handle(VPacket vPacket, long l) {
        VPacketPlayInCustomPayload vPacketPlayInCustomPayload;
        String string;
        if (vPacket instanceof VPacketPlayInCustomPayload && (string = (vPacketPlayInCustomPayload = (VPacketPlayInCustomPayload)vPacket).getChannel()) != null && (string.equals("MC|Brand") || string.equals("minecraft:brand"))) {
            String string2 = new String(vPacketPlayInCustomPayload.getData());
            String string3 = map.get(string2);
            if (string3 != null) {
                this.handleViolation(String.format("T: %s", string3));
                AlertManager.getInstance().handleBan(this.playerData, this, true);
                return;
            }
            if (!Arrays.asList((Object[])new String[]{"vanilla", "\u0007vanilla", "fml,forge", "\u0007LiteLoader", "LiteLoader", "\u0007fml,forge"}).contains(string2)) {
                this.debug(() -> CustomPayloadB.handle(string2));
            }
        }
    }
}
