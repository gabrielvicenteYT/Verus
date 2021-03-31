package me.levansj01.verus.api.impl;

import me.levansj01.verus.api.API;
import me.levansj01.verus.api.events.BanEvent;
import me.levansj01.verus.api.events.ViolationEvent;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class API1_1 extends API {
    public boolean fireBanEvent(PlayerData playerData, Check check) {
        BanEvent banEvent = new BanEvent(playerData.getPlayer(), check.getType().getName(), check.getSubType());
        Bukkit.getPluginManager().callEvent((Event) banEvent);
        return banEvent.isCancelled();
    }

    public boolean fireViolationEvent(PlayerData playerData, Check check, int n) {
        ViolationEvent violationEvent = new ViolationEvent(playerData.getPlayer(), check.getType().getName(),
                check.getSubType(), n);
        Bukkit.getPluginManager().callEvent((Event) violationEvent);
        return violationEvent.isCancelled();
    }
}
