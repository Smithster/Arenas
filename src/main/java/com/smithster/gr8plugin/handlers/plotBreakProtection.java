package com.smithster.gr8plugin.handlers;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.block.Block;
import org.bukkit.Location;

import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.classes.Plot;
import com.smithster.gr8plugin.classes.Lobby;
import com.smithster.gr8plugin.classes.LobbyJoin;

import static com.smithster.gr8plugin.classes.Plot.plots;

public class plotBreakProtection implements Listener {

    @EventHandler
    public void onPlotBreak(BlockBreakEvent event) {
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
    public void onJoinBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location loc = block.getLocation();

        if (event.getPlayer().hasPermission("gr8plugin.joinBreak")) {
            if (LobbyJoin.lobbyJoins.containsKey(loc)) {
                LobbyJoin.remove(loc);
                event.getPlayer().sendMessage("You have destroyed this lobby join trigger.");
            }
            return;
        }

        if (LobbyJoin.lobbyJoins.containsKey(loc)) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlotDamage(BlockDamageEvent event) {
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
    public void onJoinDamage(BlockDamageEvent event) {
        Block block = event.getBlock();
        Location loc = block.getLocation();

        if (event.getPlayer().hasPermission("gr8plugin.joinDamage")) {
            return;
        }

        if (LobbyJoin.lobbyJoins.containsKey(loc)) {
            event.setCancelled(true);
        }

    }
}
