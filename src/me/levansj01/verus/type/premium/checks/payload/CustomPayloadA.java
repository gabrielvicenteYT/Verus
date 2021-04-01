package me.levansj01.verus.type.premium.checks.payload;

import com.google.common.collect.ImmutableMap;
import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;

@CheckInfo(type=CheckType.PAYLOAD, subType="A", friendlyName="Hacked Client", version=CheckVersion.RELEASE, logData=true, maxViolations=1)
public class CustomPayloadA extends PacketCheck {
    private static final ImmutableMap map = new ImmutableMap.Builder().put("LOLIMAHCKER", "Cracked Vape").put("CPS_BAN_THIS_NIGGER", "Cracked Vape").put("EROUAXWASHERE", "Cracked Vape").put("EARWAXWASHERE", "Cracked Vape").put("#unbanearwax", "Cracked Vape").put("1946203560", "Vape v3").put("cock", "Reach Mod").put("lmaohax", "Reach Mod").put("reach", "Reach Mod").put("gg", "Reach Mod").put("customGuiOpenBspkrs", "Bspkrs Client").put("0SO1Lk2KASxzsd", "Bspkrs Client").put("MCnetHandler", "Misplace").put("n", "Misplace").put("CRYSTAL|KZ1LM9TO", "CrystalWare").put("CRYSTAL|6LAKS0TRIES", "CrystalWare").put("BLC|M", "Remix").build();

    public void handle(VPacket vPacket, long l) {
        if (vPacket instanceof VPacketPlayInCustomPayload) {
            VPacketPlayInCustomPayload vPacketPlayInCustomPayload = (VPacketPlayInCustomPayload)vPacket;
            String string = vPacketPlayInCustomPayload.getChannel();
            String string2 = (String)map.get(string);
            if (string2 != null) {
                this.handleViolation(String.format("T: %s", string2));
                AlertManager.getInstance().handleBan(this.playerData, this, true);
            } else if (string.startsWith("CRYSTAL|")) {
                this.handleViolation(String.format("T: %s", "CrystalWare"));
                AlertManager.getInstance().handleBan(this.playerData, this, true);
            }
        }
    }
}
