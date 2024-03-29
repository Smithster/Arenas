package com.smithster.gr8plugin.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.smithster.gr8plugin.arena.Flag;
import com.smithster.gr8plugin.utils.Profile;

public class useArenaTools implements Listener {

    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Profile profile = Profile.profiles.get(player.getUniqueId());

        Flag flag = Flag.flags.get(event.getClickedBlock().getLocation());

        if (flag == null) {
            return;
        }

        if (!profile.getArena().getTeams().contains(flag.getTeam().getName())) {
            return;
        }

        player.sendMessage("You have interacted with this flag");

        event.setCancelled(true);
        
    }
}
