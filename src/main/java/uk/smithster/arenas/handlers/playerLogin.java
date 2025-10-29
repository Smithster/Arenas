package uk.smithster.arenas.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import uk.smithster.arenas.utils.Profile;

import static uk.smithster.arenas.utils.Profile.profiles;

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
