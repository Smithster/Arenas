package com.smithster.gr8plugin.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.smithster.gr8plugin.utils.Profile;

import static com.smithster.gr8plugin.Plugin.profiles;

public class playerKill implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player killed = (Player) event.getEntity();
        Player killer = (Player) killed.getKiller();

        Profile profile = profiles.get(killer.getUniqueId());
        profile.handleKill(killed.getUniqueId());
        return;
    }
}
