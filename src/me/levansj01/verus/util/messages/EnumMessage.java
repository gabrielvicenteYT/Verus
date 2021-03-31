/*
 * Decompiled with CFR 0.150.
 */
package me.levansj01.verus.util.messages;

public enum EnumMessage {
    PLAYER_NOT_FOUND("Player not found."),
    PLAYER_NOT_BANNED("This player has not been banned."),
    PLAYER_NOT_EXIST("This player has never joined the server."),
    PLAYER_NOT_LOGS("This player has no logs."),
    CLICK_RESET("Click to restart"),
    CLICK_REFRESH("Click to refresh data"),
    UPLOAD_FAILED("Both uploads have failed."),
    DATABASE_NOT_CONNECTED("Plugin is not connected to a database."),
    ALERTS_ON("You are now viewing alerts."),
    ALERTS_OFF("You are no longer viewing alerts.");

    private static final String[] LANG_GERMAN;
    private static final String[] LANG_SPANISH;
    private static final String[] LANG_FRENCH;
    private final String message;

    public String translate(EnumLanguage enumLanguage) {
        switch (enumLanguage) {
            case SPANISH: {
                return LANG_SPANISH[this.ordinal()];
            }
            case FRENCH: {
                return LANG_FRENCH[this.ordinal()];
            }
            case GERMAN: {
                return LANG_GERMAN[this.ordinal()];
            }
        }
        return this.message;
    }

    static {
        LANG_SPANISH = new String[EnumMessage.values().length];
        LANG_GERMAN = new String[EnumMessage.values().length];
        LANG_FRENCH = new String[EnumMessage.values().length];
        EnumMessage.LANG_SPANISH[EnumMessage.PLAYER_NOT_FOUND.ordinal()] = "Jugador no encontrado.";
        EnumMessage.LANG_SPANISH[EnumMessage.PLAYER_NOT_BANNED.ordinal()] = "Ese jugador no ha sido baneado.";
        EnumMessage.LANG_SPANISH[EnumMessage.PLAYER_NOT_EXIST.ordinal()] = "Ese jugador nunca ha entrado al servidor.";
        EnumMessage.LANG_SPANISH[EnumMessage.PLAYER_NOT_LOGS.ordinal()] = "Este jugador no tiene logs.";
        EnumMessage.LANG_SPANISH[EnumMessage.CLICK_RESET.ordinal()] = "Click para reiniciar";
        EnumMessage.LANG_SPANISH[EnumMessage.CLICK_REFRESH.ordinal()] = "Click para refrescar datos";
        EnumMessage.LANG_SPANISH[EnumMessage.ALERTS_ON.ordinal()] = "Has activado las alertas.";
        EnumMessage.LANG_SPANISH[EnumMessage.ALERTS_OFF.ordinal()] = "Has desactivado las alertas.";
    }

    EnumMessage(String message) {
        this.message = message;
    }
}

