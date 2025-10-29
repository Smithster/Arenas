package uk.smithster.arenas.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import uk.smithster.arenas.arena.Arena;
import uk.smithster.arenas.lobby.Lobby;
import uk.smithster.arenas.utils.Profile;

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
