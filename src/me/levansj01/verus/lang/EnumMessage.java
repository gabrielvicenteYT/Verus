package me.levansj01.verus.lang;

import me.levansj01.verus.VerusPlugin;
import net.md_5.bungee.api.ChatColor;

public enum EnumMessage {

    PLAYER("player", "player"),
    VIOLATIONS("violations", "violations"),
    COMMAND_PERMISSION("command.permission", ChatColor.RED + "You do not have permission to do this."),
    COMMAND_PLAYER_NEVER_LOGGED_ON("command.player_never_logged_on", "This player has never logged onto the server"),
    COMMAND_PLAYER_NOT_FOUND("command.player_not_found", "Player not found"),
    TOP_DESCRIPTION("command.commands.top_description", "Gather players with the most violations"),
    RESTART_DESCRIPTION("command.commands.restart_description", "Restart and automatically update"),
    GUI_DESCRIPTION("command.commands.gui_description", "View and control " + VerusPlugin.getNameFormatted()),
    CHECK_DESCRIPTION("command.commands.check_description", "Check a players' " + VerusPlugin.getNameFormatted() + " related bans"),
    COMMANDS_DESCRIPTION("command.commands.commands_description", "View all " + VerusPlugin.getNameFormatted() + " related commands"),
    INFO_DESCRIPTION("command.commands.info_description", "View useful information about a player"),
    VIEW_PLAYER_INFO("command.top.view_player_info", "Click to view {player}'s info"),
    ALERTS_ENABLED_COMMAND("alerts.enabled", "You are now viewing alerts"),
    ALERTS_DISABLED_COMMAND("alerts.disabled", "You are no longer viewing alerts");

    private String value;
    private final String location;
    private final String fallback;

    public void setValue(String string) {
        this.value = string;
    }

    public String getFallback() {
        return this.fallback;
    }

    public String get() {
        if (this.value == null) {
            return this.fallback;
        }
        return this.value;
    }

    public String getLocation() {
        return this.location;
    }
    
    EnumMessage(String location, String fallback) {
        this.location = location;
        this.fallback = fallback;
    }
}
