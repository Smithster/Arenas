package com.smithster.gr8plugin.handlers;

import com.mongodb.client.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.block.Block;
import org.bukkit.Location;

import com.smithster.gr8plugin.Plugin;

public class protection implements Listener {
    public protection(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        Location loc = block.getLocation();

    }
}
