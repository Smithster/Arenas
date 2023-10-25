package com.smithster.gr8plugin.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

import com.smithster.gr8plugin.classes.Arena;
import com.smithster.gr8plugin.utils.Profile;

import static com.smithster.gr8plugin.utils.Profile.profiles;

public class playerKill implements Listener {

    private Plugin plugin;

    public playerKill(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player killed = (Player) event.getEntity();
        Player killer = (Player) killed.getKiller();

        if (killer == null) {
            return;
        }

        Profile killerProfile = profiles.get(killer.getUniqueId());
        Profile killedProfile = profiles.get(killed.getUniqueId());

        Arena arena = killerProfile.getArena();
        if (arena == null || !arena.equals(killedProfile.getArena())) {
            return;
        }
        arena.handleKill(killerProfile, killedProfile);
        return;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        final Profile profile = Profile.profiles.get(player.getUniqueId());
        final Runnable backToLobby = () -> {
            profile.getLobby().moveBackToLobby(player);
        };
        player.sendMessage(profile.getLobby() == null ? "Respawning normally" : "Sending back to lobby");
        if (profile.getArena() == null && profile.getLobby() != null) {
            Bukkit.getScheduler().runTaskLater(plugin, backToLobby, 1L);
        }
    }
}
