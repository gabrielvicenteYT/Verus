package me.levansj01.verus.command;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.command.BaseArgumentCommand;

import me.levansj01.verus.command.BaseCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class BaseArgumentCommand extends BaseCommand {
    private Map<String, CommandArgument> arguments = new ConcurrentHashMap<String, CommandArgument>();

    public BaseArgumentCommand(String string, String string2, String string3, List<String> list) {
        super(string, string2, string3, list);
    }

    public void execute(CommandSender commandSender, String[] stringArray) {
        if (stringArray.length == 0) {
            this.sendHelp(commandSender);
            return;
        }
        CommandArgument commandArgument = (CommandArgument) this.arguments.get((Object) stringArray[0]);
        if (commandArgument == null) {
            this.sendHelp(commandSender);
            return;
        }
        commandArgument.run(commandSender, stringArray);
    }

    public BaseArgumentCommand(String string) {
        super(string);
    }

    protected void sendHelp(CommandSender commandSender) {
        String string = VerusPlugin.COLOR + "Help Command for /" + this.getName() + ":\n";
        Iterator<CommandArgument> iterator = this.arguments.values().iterator();
        if (iterator.hasNext()) {
            CommandArgument commandArgument = iterator.next();
            string = string + String.format((ChatColor.GRAY + "- " + VerusPlugin.COLOR + "/%s %s %s: %s\n"),
                    new Object[] { this.getName(), commandArgument.argument, CommandArgument.getUsage(commandArgument),
                            ChatColor.WHITE + commandArgument.description });
        }
        commandSender.sendMessage(string);
    }

    protected void addArgument(CommandArgument commandArgument) {
        this.arguments.put(commandArgument.argument, commandArgument);
    }

}
