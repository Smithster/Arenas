package com.smithster.gr8plugin.handlers;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.smithster.gr8plugin.classes.LobbyJoin;

import static com.smithster.gr8plugin.Plugin.profiles;

public class setLobbyJoin implements Listener {

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (!profiles.get(player.getUniqueId()).isSettingTrigger) {
            return;
        }

        String lobbyJoinName = profiles.get(player.getUniqueId()).getTriggerName();

        LobbyJoin lobbyJoin = new LobbyJoin();
        Location loc = block.getLocation(null);
        ArrayList<Integer> pos = new ArrayList<Integer>();
        pos.add(loc.getBlockX());
        pos.add(loc.getBlockY());
        pos.add(loc.getBlockZ());
        lobbyJoin.setLocation(pos, loc.getWorld().getName());

        lobbyJoin.save(lobbyJoinName);

        player.sendMessage(String.format("You have created a new trigger called: %s", lobbyJoinName));
        event.setCancelled(true);
    }
}
