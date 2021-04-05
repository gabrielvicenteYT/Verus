package me.levansj01.verus;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import me.levansj01.launcher.VerusLaunch;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.api.API;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.command.BaseCommandHelp;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.PacketManager;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.listener.DataListener;
import me.levansj01.verus.messaging.MessagingHandler;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.task.ReportTask;
import me.levansj01.verus.task.ServerTickTask;
import me.levansj01.verus.type.Loader;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.java.SafeReflection;
import me.levansj01.verus.util.java.WordUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.permissions.ServerOperator;
import org.bukkit.plugin.Plugin;

//TODO Fix lambdas and check Killclassloader method

public class VerusPlugin implements VerusLaunch {
    private List<Command> commands;
    public static ChatColor COLOR = ChatColor.BLUE;
    private VerusTypeLoader typeLoader;
    private final Server server = Bukkit.getServer();
    private static String name = "verus";
    private HelpMap helpMap;
    private DataListener dataListener;
    private VerusLauncher plugin;
    private ReportTask reportTask;
    private SimpleCommandMap commandMap;

    public VerusTypeLoader getTypeLoader() {
        return this.typeLoader;
    }

    public DataListener getDataListener() {
        return this.dataListener;
    }

    public List<Command> getCommands() {
        return this.commands;
    }

    public ReportTask getReportTask() {
        return this.reportTask;
    }

    private void registerListeners() {
        this.dataListener = new DataListener();
        this.getServer().getPluginManager().registerEvents((Listener) this.dataListener, (Plugin) this.plugin);
    }

    public void shutdown() {
        DataManager.disable();
        this.unregisterListeners();
        StorageEngine.getInstance().stopConfig();
        CheckManager.getInstance().disable();
        GUIManager.getInstance().disable();
        StorageEngine.getInstance().stop();
        if (StorageEngine.getInstance().getVerusConfig().isBungeeBans()) {
            MessagingHandler.getInstance().disable();
        }
        API.check();
        API aPI = API.getAPI();
        if (aPI != null) {
            aPI.disable();
        }
        if (this.reportTask != null) {
            this.reportTask.end();
        }
        if (ServerTickTask.getInstance().getBukkitTask() != null) {
            ServerTickTask.getInstance().getBukkitTask().cancel();
        }
        this.unregisterCommands();
        this.plugin.getLogger().info(VerusPlugin.getNameFormatted() + " shutdown successfully");
        this.typeLoader.setCheckClasses(null);
        this.killClassLoader();
    }

    public static int getType() {
        return 1;
    }

    private void unregisterListeners() {
        if (this.dataListener != null) {
            HandlerList.unregisterAll((Listener) this.dataListener);
        }
        HandlerList.unregisterAll((Plugin) VerusLauncher.getPlugin());
    }

    public VerusLauncher getPlugin() {
        return this.plugin;
    }

    private static void lambda$registerCommands$2(Command command) {
        command.setPermissionMessage(EnumMessage.COMMAND_PERMISSION.get());
    }

    public static String getName() {
        return name;
    }

    private void registerCommands() {
        this.commands = this.typeLoader.getCommands();
        this.commands.addAll(this.typeLoader.getBaseCommands());
        if (!StorageEngine.getInstance().getVerusConfig().isPingCommand()) {
            this.commands.removeIf(VerusPlugin::lambda$registerCommands$1);
        }
        this.commands.forEach(VerusPlugin::lambda$registerCommands$2);
        this.commandMap = SafeReflection.getCommandMap();
        this.helpMap = this.server.getHelpMap();
        Map map = SafeReflection.getKnownCommands(this.commandMap);
        this.commandMap.registerAll(name, this.commands);
        this.commands.forEach(arg_0 -> this.lambda$registerCommands$3(map, arg_0));
    }

    public void launch(VerusLauncher verusLauncher) {
        API aPI;
        long l = System.currentTimeMillis();
        NMSManager.getInstance();
        PacketManager.getInstance();
        this.plugin = verusLauncher;
        this.typeLoader = new VerusTypeLoader();
        StorageEngine.getInstance().start();
        VerusTypeLoader.loader();
        DataManager.enable((VerusPlugin) this);
        CheckManager.getInstance().enable(this);
        if (StorageEngine.getInstance().getVerusConfig().isBungeeBans()) {
            MessagingHandler.getInstance().enable(this);
        }
        this.registerListeners();
        this.registerCommands();
        ServerTickTask.getInstance().schedule();
        if (StorageEngine.getInstance().getVerusConfig().isSendStats()) {
            this.reportTask = new ReportTask();
            this.reportTask.start();
        }
        if ((aPI = API.getAPI()) != null) {
            aPI.enable(this);
        }
        long l2 = System.currentTimeMillis() - l;
        String string = String.format((String) "%s b%s launched successfully in %sms",
                new Object[] { VerusPlugin.getNameFormatted(), VerusPlugin.getBuild(), l2 });
        Bukkit.getOnlinePlayers().stream().filter(ServerOperator::isOp)
                .forEach(arg_0 -> VerusPlugin.launch1(string, arg_0));
        this.plugin.getLogger().info(string + "\nBy running this plugin, you agree to be bound by our TOS");
    }

    public HelpMap getHelpMap() {
        return this.helpMap;
    }

    private static void launch1(String string, Player player) {
        player.sendMessage(ChatColor.GREEN + string);
    }

    public static void setName(String string) {
        name = string;
    }

    private static boolean lambda$unregisterCommands$4(HelpTopic helpTopic) {
        return helpTopic instanceof BaseCommandHelp;
    }

    private static boolean lambda$registerCommands$1(Command command) {
        return command.getName().equals(Loader.getPingCommand());
    }

    private void lambda$registerCommands$3(Map map, Command command) {
        map.put(command.getName(), command);
        this.helpMap.addTopic((HelpTopic) new BaseCommandHelp(command));
    }

    public static long getBuild() {
        return 2484L;
    }

    public SimpleCommandMap getCommandMap() {
        return this.commandMap;
    }

    private void unregisterCommands() {
        if (this.commandMap != null) {
            Map map = SafeReflection.getKnownCommands(this.commandMap);
            map.values().removeAll(this.commands);
            for (Command command : this.commands) {
                command.unregister((CommandMap) this.commandMap);
            }
        }
        if (this.helpMap != null) {
            this.helpMap.getHelpTopics().removeIf(VerusPlugin::lambda$unregisterCommands$4);
        }
    }

    private void killClassLoader() {
        //TODO Fix this method
    }

    public static void restart() {
        VerusLauncher verusLauncher = VerusLauncher.getPlugin();
        Bukkit.getPluginManager().disablePlugin((Plugin) verusLauncher);
        Bukkit.getPluginManager().enablePlugin((Plugin) verusLauncher);
    }

    public Server getServer() {
        return this.server;
    }

    public static String getNameFormatted() {
        return WordUtils.capitalize((String) name);
    }
}
