package com.smithster.gr8plugin.handlers;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.block.Block;
import org.bukkit.Location;

import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.classes.Plot;
import com.smithster.gr8plugin.classes.LobbyJoin;

import static com.smithster.gr8plugin.classes.Plot.plots;

public class plotBreakProtection implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location loc = block.getLocation();

        if (event.getPlayer().hasPermission("gr8plugin.plotBreak")) {
            return;
        }

        for (Plot plot : plots.values()) {
            if (!plot.contains(loc)) {
                continue;
            }
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location loc = block.getLocation();

        if (event.getPlayer().hasPermission("gr8plugin.joinBreak")) {
            if (LobbyJoin.lobbyJoins.containsKey(loc)){
                lobbyJoin.remove(lobbies.get(loc));
            }
            return;
        }

        if (LobbyJoin.lobbyJoins.containsKey(loc)){
            event.setCancelled(true);
        }
            
    }
    
    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        Block block = event.getBlock();
        Location loc = block.getLocation();

        if (event.getPlayer().hasPermission("gr8plugin.plotDamage")) {
            return;
        }

        for (Plot plot : plots.values()) {
            if (!plot.contains(loc)) {
                continue;
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockDamageEvent event) {
        Block block = event.getBlock();
        Location loc = block.getLocation();

        if (event.getPlayer().hasPermission("gr8plugin.joinDamage")) {
            return;
        }

        if (LobbyJoin.lobbyJoins.containsKey(loc)){
            event.setCancelled(true);
        }
            
    }
}
