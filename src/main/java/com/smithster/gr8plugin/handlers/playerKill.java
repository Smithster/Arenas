package com.smithster.gr8plugin.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.smithster.gr8plugin.classes.Arena;
import com.smithster.gr8plugin.utils.Profile;

import static com.smithster.gr8plugin.utils.Profile.profiles;

public class playerKill implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player killed = (Player) event.getEntity();
        Player killer = (Player) killed.getKiller();

        Profile killerProfile = profiles.get(killer.getUniqueId());
        Profile killedProfile = profiles.get(killed.getUniqueId());

        Arena arena = killerProfile.getArena();
        if (arena == null || !arena.equals(killedProfile.getArena())) {
            return;
        }

        arena.handleKill(killerProfile, killedProfile);

        return;
    }
}
