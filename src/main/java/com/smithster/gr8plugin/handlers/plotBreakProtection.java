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

import static com.smithster.gr8plugin.Plugin.plots;

import java.util.ArrayList;

public class plotBreakProtection implements Listener {
    public plotBreakProtection(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location loc = block.getLocation();

        if (event.getPlayer().hasPermission("gr8plugin.plotBreak")) {
            return;
        }

        for (Plot plot : plots.values()) {

            if (plot.contains(loc)) {
                continue;
            }

            event.setCancelled(true);
        }

    }
}
