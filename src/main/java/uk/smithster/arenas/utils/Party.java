package uk.smithster.arenas.utils;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import uk.smithster.arenas.arena.Arena;
import uk.smithster.arenas.lobby.Lobby;
import uk.smithster.arenas.team.Team;

public class Party {

    private Player leader;
    private ArrayList<Player> players = new ArrayList<Player>();

    public Party(Player player) {
        this.leader = player;
        this.players.add(player);
    }

    public void join(Player player) {
        this.players.add(player);
        Profile.profiles.get(player.getUniqueId()).setParty(this);
        player.sendMessage(String.format("You have joined %s's party", this.leader.getName()));
    }

    public void leave(Player player) {
        this.players.remove(player);
        Profile.profiles.get(player.getUniqueId()).setParty(new Party(player));
        player.sendMessage(String.format("You have left %s's party", this.leader.getName()));
    }

    public void joinLobby(Lobby lobby) {
        for (Player player : this.players) {
            Profile.profiles.get(player.getUniqueId()).setLobby(lobby);
            lobby.enter(player);
        }
    }

    public void leaveLobby(Lobby lobby) {
        for (Player player : this.players) {
            lobby.leave(player);
        }
    }

    public void joinArena(Arena arena) {
        arena.enter(this);
    }

    public void leaveArena() {
        for (Player player : this.players) {
            Profile profile = Profile.profiles.get(player.getUniqueId());
            profile.leaveArena();
        }
    }

    public boolean isPartyLeader(Player player) {
        return this.leader.equals(player);
    }

    public void joinTeam(Team team) {
        for (Player player : this.players) {
            team.playerJoin(player);
        }
    }

    public void setArena(Arena arena) {
        for (Player player : this.players) {
            Profile profile = Profile.profiles.get(player.getUniqueId());
            profile.setArena(arena);
            player.setScoreboard(arena.getScoreboard());
        }
    }
}
