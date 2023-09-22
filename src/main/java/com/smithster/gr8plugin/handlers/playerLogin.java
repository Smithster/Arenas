package com.smithster.gr8plugin.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.smithster.gr8plugin.utils.Profile;

import static com.smithster.gr8plugin.Plugin.profiles;

public class playerLogin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        profiles.put(event.getPlayer().getUniqueId(), new Profile(event.getPlayer()));
    };

}
