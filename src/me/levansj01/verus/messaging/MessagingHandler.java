package me.levansj01.verus.messaging;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;

public class MessagingHandler {

    private static String brandChannel;
    private static MessagingHandler instance;
    private VerusPlugin plugin;
    private static final String verusChannel;

    private void registerChannels() {
        Messenger messenger = this.plugin.getServer().getMessenger();
        VerusLauncher verusLauncher = this.plugin.getPlugin();
        messenger.registerOutgoingPluginChannel(verusLauncher, "BungeeCord");
        messenger.registerOutgoingPluginChannel(verusLauncher, verusChannel);
    }

    private void unregisterChannels() {
        this.plugin.getServer().getMessenger().unregisterOutgoingPluginChannel((Plugin)this.plugin.getPlugin());
        this.plugin.getServer().getMessenger().unregisterIncomingPluginChannel((Plugin)this.plugin.getPlugin());
    }

    public void disable() {
        if (this.plugin == null) {
            return;
        }
        this.unregisterChannels();
    }

    public void enable(VerusPlugin verusPlugin) {
        this.plugin = verusPlugin;
        this.registerChannels();
    }

    public void handleBan(Player player, String string) {
        ByteArrayDataOutput byteArrayDataOutput = ByteStreams.newDataOutput();
        byteArrayDataOutput.writeUTF("HandleBan");
        byteArrayDataOutput.writeUTF(string);
        player.sendPluginMessage((Plugin)this.plugin.getPlugin(), "BungeeCord", byteArrayDataOutput.toByteArray());
    }

    public static MessagingHandler getInstance() {
        MessagingHandler messagingHandler;
        if (instance == null) {
            messagingHandler = instance = new MessagingHandler();
        } else {
            messagingHandler = instance;
        }
        return messagingHandler;
    }

    static {
        String string;
        String string2;
        if (NMSManager.getInstance().getServerVersion().afterOrEq(ServerVersion.v1_14_R1)) {
            string2 = "bungeecord:brand";
        } else {
            string2 = brandChannel = "MC|Brand";
        }
        if (NMSManager.getInstance().getServerVersion().afterOrEq(ServerVersion.v1_14_R1)) {
            string = "bungeecord:verus";
        } else {
            string = "VerusBungee";
        }
        verusChannel = string;
        MessagingHandler.brandChannel = string2;
    }
}
