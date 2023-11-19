package com.smithster.gr8plugin.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.smithster.gr8plugin.arena.Arena;
import com.smithster.gr8plugin.lobby.Lobby;
import com.smithster.gr8plugin.utils.Profile;

public class playerLeave implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        Profile profile = Profile.profiles.get(player.getUniqueId());
        Arena arena = profile.getArena();
        Lobby lobby = profile.getLobby();
        if (arena != null) {
            arena.removePlayer(player);
            profile.leaveArena();
        }

        if (lobby != null) {
            lobby.leave(player);
            profile.setLobby(null);
        }
    }
}
