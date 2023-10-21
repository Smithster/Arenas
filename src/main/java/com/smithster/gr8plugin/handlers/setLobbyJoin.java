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
import com.smithster.gr8plugin.utils.Profile;

import static com.smithster.gr8plugin.utils.Profile.profiles;

public class setLobbyJoin implements Listener {

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        Profile profile = profiles.get(player.getUniqueId());
        Block block = event.getBlock();

        if (!profile.isSettingJoin()) {
            return;
        }

        LobbyJoin lobbyJoin = profile.getJoin();
        Location loc = block.getLocation();

        lobbyJoin.setLocation(loc);
        lobbyJoin.setActive(true);
        lobbyJoin.save();

        player.sendMessage("You have created a new trigger");
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Profile profile = profiles.get(player.getUniqueId());
        Block block = event.getBlock();

        if (!profile.isSettingJoin()) {
            return;
        }

        LobbyJoin lobbyJoin = profile.getJoin();
        Location loc = block.getLocation();

        lobbyJoin.setLocation(loc);
        lobbyJoin.setActive(true);
        lobbyJoin.save();

        player.sendMessage("You have created a new trigger");
        event.setCancelled(true);
    }
}
