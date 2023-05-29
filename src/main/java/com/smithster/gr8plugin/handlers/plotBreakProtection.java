package com.smithster.gr8plugin.handlers;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.block.Block;
import org.bukkit.Location;

import com.smithster.gr8plugin.Plugin;
import static com.smithster.gr8plugin.Plugin.Plots;

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

        for (Document plot : Plots.find()) {
            String world = (String) plot.get("world");
            ArrayList<Integer> pos1 = (ArrayList<Integer>) plot.get("pos1");
            ArrayList<Integer> pos2 = (ArrayList<Integer>) plot.get("pos2");

            if (pos1 == null || pos2 == null || world == null) {
                continue;
            }

            Integer maxX = Math.max(pos1.get(0), pos2.get(0));
            Integer maxY = Math.max(pos1.get(1), pos2.get(1));
            Integer maxZ = Math.max(pos1.get(2), pos2.get(2));
            Integer minX = Math.min(pos1.get(0), pos2.get(0));
            Integer minY = Math.min(pos1.get(1), pos2.get(1));
            Integer minZ = Math.min(pos1.get(2), pos2.get(2));

            if (!loc.getWorld().getName().equals(world)) {
                continue;
            }

            if (minX > loc.getBlockX() || loc.getBlockX() > maxX) {
                continue;
            }

            if (minY > loc.getBlockY() || loc.getBlockY() > maxY) {
                continue;
            }

            if (minZ > loc.getBlockZ() || loc.getBlockZ() > maxZ) {
                continue;
            }

            event.setCancelled(true);

        }

    }
}
