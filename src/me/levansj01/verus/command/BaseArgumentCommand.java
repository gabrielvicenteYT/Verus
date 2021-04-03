package me.levansj01.verus.command;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.command.BaseArgumentCommand;

import me.levansj01.verus.command.BaseCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class BaseArgumentCommand extends BaseCommand
{
    private Map<String, CommandArgument> arguments;
    
    public BaseArgumentCommand(final String s, final String s2, final String s3, final List<String> list) {
        super(s, s2, s3, list);
        this.arguments = new ConcurrentHashMap<String, CommandArgument>();
    }
    
    public BaseArgumentCommand(final String s) {
        super(s);
        this.arguments = new ConcurrentHashMap<String, CommandArgument>();
    }
    
    @Override
    public void execute(final CommandSender commandSender, final String[] array) {
        if (array.length == 0) {
            this.sendHelp(commandSender);
            return;
        }
        final CommandArgument commandArgument = this.arguments.get(array[0]);
        if (commandArgument == null) {
            this.sendHelp(commandSender);
            return;
        }
        commandArgument.run(commandSender, array);
    }
    
    protected void addArgument(final CommandArgument commandArgument) {
        this.arguments.put(commandArgument.argument, commandArgument);
    }
    
    protected void sendHelp(final CommandSender commandSender) {
        final StringBuilder s = new StringBuilder(VerusPlugin.COLOR + "Help Command for /" + this.getName() + ":\n");
        for (final CommandArgument commandArgument : this.arguments.values()) {
            s.append(String.format(ChatColor.GRAY + "- " + VerusPlugin.COLOR + "/%s %s %s: %s\n", this.getName(), commandArgument.argument, commandArgument.usage, ChatColor.WHITE + commandArgument.description));
        }
        commandSender.sendMessage(s.toString());
    }
    
    public class CommandArgument
    {
        protected final String description;
        protected final String argument;
        private final BiConsumer<CommandSender, String[]> consumer;
        private String usage;
        
        public CommandArgument(final String argument, final String description, final String usage, final BiConsumer<CommandSender, String[]> consumer) {
            this.usage = "";
            this.argument = argument;
            this.description = description;
            this.usage = usage;
            this.consumer = consumer;
        }
        
        public CommandArgument(final String argument, final String description, final BiConsumer<CommandSender, String[]> consumer) {
            this.usage = "";
            this.argument = argument;
            this.description = description;
            this.consumer = consumer;
        }
        
        public void run(final CommandSender commandSender, final String[] array) {
            if (this.consumer != null) {
                this.consumer.accept(commandSender, array);
            }
        }
    }

}

