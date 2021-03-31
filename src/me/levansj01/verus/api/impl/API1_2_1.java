package me.levansj01.verus.api.impl;

import me.levansj01.verus.api.events.PlayerInitEvent;
import me.levansj01.verus.api.impl.API1_2;
import me.levansj01.verus.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class API1_2_1 extends API1_2 {
    public boolean fireInitEvent(PlayerData playerData) {
        PlayerInitEvent playerInitEvent = new PlayerInitEvent(playerData.getPlayer());
        Bukkit.getPluginManager().callEvent((Event) playerInitEvent);
        return playerInitEvent.isCancelled();
    }
}
