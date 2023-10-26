package com.smithster.gr8plugin.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.utils.Profile;

import static com.smithster.gr8plugin.utils.Profile.profiles;

import java.util.UUID;

public class playerLogin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID id = player.getUniqueId();
        if (profiles.containsKey(id)) {
            profiles.get(id).setPlayer(player);
        } else {
            Profile profile = new Profile(player);
            profile.save();
            profiles.put(player.getUniqueId(), profile);
        }

    };

}
