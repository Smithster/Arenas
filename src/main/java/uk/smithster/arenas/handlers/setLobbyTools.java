package uk.smithster.arenas.handlers;

import static uk.smithster.arenas.utils.Profile.profiles;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

import uk.smithster.arenas.lobby.LobbyJoin;
import uk.smithster.arenas.lobby.LobbyLeave;
import uk.smithster.arenas.lobby.LobbyStart;
import uk.smithster.arenas.lobby.LobbyVote;
import uk.smithster.arenas.utils.Profile;

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
