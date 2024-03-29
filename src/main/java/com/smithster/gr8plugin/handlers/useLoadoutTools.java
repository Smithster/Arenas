package com.smithster.gr8plugin.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.smithster.gr8plugin.loadouts.LoadoutSelect;

public class useLoadoutTools implements Listener {

    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {

        LoadoutSelect ls = LoadoutSelect.LoadoutSelects.get(event.getClickedBlock().getLocation());

        if (ls == null) {
            return;
        }

        event.setCancelled(true);
        ls.use(event.getPlayer());
        return;
    }
}
