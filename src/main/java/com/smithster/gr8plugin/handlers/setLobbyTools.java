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
import com.smithster.gr8plugin.classes.LobbyLeave;
import com.smithster.gr8plugin.classes.LobbyStart;
import com.smithster.gr8plugin.classes.LobbyVote;
import com.smithster.gr8plugin.classes.VoteAgent;
import com.smithster.gr8plugin.utils.Profile;

import static com.smithster.gr8plugin.utils.Profile.profiles;

public class setLobbyTools implements Listener {

    @EventHandler
    public void onBlockDamageJoin(BlockDamageEvent event) {
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
    public void onBlockBreakJoin(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Profile profile = profiles.get(player.getUniqueId());
        Block block = event.getBlock();

        if (!profile.isSettingJoin()) {
            return;
        }

        LobbyJoin lobbyJoin = profile.getJoin();
        Location loc = block.getLocation();

        if (LobbyJoin.lobbyJoins.containsKey(loc)) {
            player.sendMessage("A trigger already exists at this location.");
            return;
        }

        lobbyJoin.setLocation(loc);
        lobbyJoin.setActive(true);
        lobbyJoin.save();

        player.sendMessage("You have created a new trigger!");
        profile.settingJoin(false);
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockDamageLeave(BlockDamageEvent event) {
        Player player = event.getPlayer();
        Profile profile = profiles.get(player.getUniqueId());
        Block block = event.getBlock();

        if (!profile.isSettingLeave()) {
            return;
        }

        LobbyLeave lobbyLeave = profile.getLeave();
        Location loc = block.getLocation();

        lobbyLeave.setLocation(loc);
        lobbyLeave.save();

        player.sendMessage("You have created a new trigger");
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreakLeave(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Profile profile = profiles.get(player.getUniqueId());
        Block block = event.getBlock();

        if (!profile.isSettingLeave()) {
            return;
        }

        LobbyLeave lobbyLeave = profile.getLeave();
        Location loc = block.getLocation();

        if (LobbyLeave.lobbyLeaves.containsKey(loc)) {
            player.sendMessage("A trigger already exists at this location.");
            return;
        }

        lobbyLeave.setLocation(loc);
        lobbyLeave.save();

        player.sendMessage("You have created a new trigger!");
        profile.settingLeave(false);
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockDamageVote(BlockDamageEvent event) {
        Player player = event.getPlayer();
        Profile profile = profiles.get(player.getUniqueId());
        Block block = event.getBlock();

        if (!profile.isSettingVote()) {
            return;
        }

        LobbyVote lobbyVote = profile.getVote();
        Location loc = block.getLocation();

        lobbyVote.setLocation(loc);
        lobbyVote.save();

        player.sendMessage("You have created a new trigger");
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreakVote(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Profile profile = profiles.get(player.getUniqueId());
        Block block = event.getBlock();

        if (!profile.isSettingVote()) {
            return;
        }

        LobbyVote lobbyVote = profile.getVote();
        Location loc = block.getLocation();

        if (LobbyVote.lobbyVotes.containsKey(loc)) {
            player.sendMessage("A trigger already exists at this location.");
            return;
        }

        lobbyVote.setLocation(loc);
        lobbyVote.save();

        player.sendMessage("You have created a new trigger!");
        profile.settingVote(false);
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreakStart(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Profile profile = profiles.get(player.getUniqueId());
        Block block = event.getBlock();

        if (!profile.isSettingStart()) {
            return;
        }

        LobbyStart lobbyStart = profile.getStart();
        Location loc = block.getLocation();

        if (LobbyStart.lobbyStarts.containsKey(loc)) {
            player.sendMessage("A trigger already exists at this location.");
            return;
        }

        lobbyStart.setLocation(loc);
        lobbyStart.save();

        player.sendMessage("You have created a new trigger!");
        profile.settingStart(false);
        event.setCancelled(true);
    }
}
