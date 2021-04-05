package me.levansj01.verus.api.impl;

import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.API;
import me.levansj01.verus.api.APIManager;
import me.levansj01.verus.api.VerusAPI;
import me.levansj01.verus.api.events.BanEvent;
import me.levansj01.verus.api.events.ViolationEvent;
import me.levansj01.verus.api.manager.VerusManager;
import me.levansj01.verus.api.manager.impl.BlankManager;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class API1_2 extends API {
    private me.levansj01.verus.api.check.Check toAPI(Check check) {
        return new me.levansj01.verus.api.check.Check(check.getType().getName(), check.getSubType(),
                check.getFriendlyName());
    }

    public void enable(VerusPlugin verusPlugin) {
        VerusAPI verusAPI = (VerusAPI) API1_2.fetchPlugin();
        verusAPI.setVerusManager((VerusManager) new APIManager(verusPlugin.getTypeLoader().loadChecks()));
    }

    public void disable() {
        VerusAPI verusAPI = (VerusAPI) API1_2.fetchPlugin();
        verusAPI.setVerusManager(new BlankManager());
    }

    public boolean fireBanEvent(PlayerData playerData, Check check) {
        BanEvent banEvent = new BanEvent(playerData.getPlayer(), this.toAPI(check));
        Bukkit.getPluginManager().callEvent(banEvent);
        return banEvent.isCancelled();
    }

    public boolean fireViolationEvent(PlayerData playerData, Check check, int n) {
        ViolationEvent violationEvent = new ViolationEvent(playerData.getPlayer(), this.toAPI(check), n);
        Bukkit.getPluginManager().callEvent(violationEvent);
        return violationEvent.isCancelled();
    }
}
