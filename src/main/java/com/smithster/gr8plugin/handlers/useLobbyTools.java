package com.smithster.gr8plugin.handlers;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.smithster.gr8plugin.classes.Lobby;
import com.smithster.gr8plugin.classes.LobbyJoin;
import com.smithster.gr8plugin.classes.LobbyLeave;
import com.smithster.gr8plugin.classes.LobbyStart;
import com.smithster.gr8plugin.classes.LobbyVote;
import com.smithster.gr8plugin.utils.Party;
import com.smithster.gr8plugin.utils.Profile;

import static com.smithster.gr8plugin.classes.LobbyJoin.getLobbyJoin;

public class useLobbyTools implements Listener {

    @EventHandler
    public void onRightClickJoin(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

            if (!LobbyJoin.isLobbyJoin(block)) {
                return;
            }

            LobbyJoin lobbyJoin = LobbyJoin.getLobbyJoin(block);
            Lobby lobby = lobbyJoin.getLobby();

            event.setCancelled(true);

            Player player = event.getPlayer();

            if (!lobby.getPlot().hasEntryLoc()){
                player.sendMessage("This lobby doesn't have an entry location set yet");
                return;
            }

            Profile profile = Profile.profiles.get(player.getUniqueId());
            if (!profile.isPartyLeader()) {
                player.sendMessage("You must be the party leader to do this.");
                return;
            }
            Party party = profile.getParty();
            party.joinLobby(lobbyJoin.getLobby());
            player.sendMessage("Lobby Joined!");
        }
    }

    @EventHandler
    public void onRightClickLeave(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

            if (!LobbyLeave.isLobbyLeave(block)) {
                return;
            }

            LobbyLeave lobbyLeave = LobbyLeave.getLobbyLeave(block);

            event.setCancelled(true);
            Player player = event.getPlayer();
            Profile profile = Profile.profiles.get(player.getUniqueId());
            if (!profile.isPartyLeader()) {
                player.sendMessage("You must be the party leader to do this.");
                return;
            }
            Party party = profile.getParty();
            if (lobbyLeave.getLobby().containsPlayer(player)) {
                party.leaveLobby(lobbyLeave.getLobby());
            }
        }
    }

    @EventHandler
    public void onRightClickVote(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

            if (!LobbyVote.isLobbyVote(block)) {
                return;
            }

            LobbyVote lobbyVote = LobbyVote.getLobbyVote(block);

            event.setCancelled(true);
            Player player = event.getPlayer();
            if (lobbyVote.getLobby().containsPlayer(player)) {
                lobbyVote.vote(player);
            }
        }
    }

    @EventHandler
    public void onRightClickStart(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

            if (!LobbyStart.isLobbyStart(block)) {
                return;
            }

            LobbyStart lobbyStart = LobbyStart.getLobbyStart(block);

            event.setCancelled(true);
            Player player = event.getPlayer();
            if (lobbyStart.getLobby().containsPlayer(player)) {
                lobbyStart.startGame();
            }
        }
    }
}
