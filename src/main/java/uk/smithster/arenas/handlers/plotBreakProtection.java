package uk.smithster.arenas.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

import uk.smithster.arenas.lobby.LobbyJoin;
import uk.smithster.arenas.areas.Plot;

import org.bukkit.block.Block;

import static uk.smithster.arenas.areas.Plot.plots;

import org.bukkit.Location;

public class plotBreakProtection implements Listener {

    @EventHandler
    public void onPlotBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location loc = block.getLocation();

        if (event.getPlayer().hasPermission("arenas.plotBreak")) {
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

        if (event.getPlayer().hasPermission("arenas.joinBreak")) {
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

        if (event.getPlayer().hasPermission("arenas.plotDamage")) {
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

        if (event.getPlayer().hasPermission("arenas.joinDamage")) {
            return;
        }

        if (LobbyJoin.lobbyJoins.containsKey(loc)) {
            event.setCancelled(true);
        }

    }
}
