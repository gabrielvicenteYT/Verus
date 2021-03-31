package me.levansj01.verus.command;

import java.util.function.BiConsumer;

import org.bukkit.command.CommandSender;

public class CommandArgument {
    protected final String argument;
    protected final String description;
    final BaseArgumentCommand this$0;
    private final BiConsumer<CommandSender, String[]> consumer;
    private String usage;


    public CommandArgument(BaseArgumentCommand baseArgumentCommand, String string, String string2, BiConsumer<CommandSender, String[]> biConsumer) {
        this.this$0 = baseArgumentCommand;
        this.setUsage("");
        this.argument = string;
        this.description = string2;
        this.consumer = biConsumer;
    }

    public void run(CommandSender commandSender, String[] stringArray) {
        if (this.consumer != null) {
            this.consumer.accept(commandSender, stringArray);
        }
    }

    public CommandArgument(BaseArgumentCommand baseArgumentCommand, String string, String string2, String string3, BiConsumer<CommandSender, String[]> biConsumer) {
        this.this$0 = baseArgumentCommand;
        this.setUsage("");
        this.argument = string;
        this.description = string2;
        this.setUsage(string3);
        this.consumer = biConsumer;
    }

    public static String getUsage(CommandArgument commandArgument) {
        return commandArgument.usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

}