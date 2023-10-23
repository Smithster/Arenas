package com.smithster.gr8plugin.utils;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.smithster.gr8plugin.classes.Lobby;

public class Party {

    private Player leader;
    private ArrayList<Player> players = new ArrayList<Player>();

    public Party(Player player) {
        this.leader = player;
        this.players.add(player);
    }

    public void join(Player player) {
        this.players.add(player);
        Profile.profiles.get(player).setParty(this);
        player.sendMessage(String.format("You have joined %s's party", this.leader.getName()));
    }

    public void leave(Player player) {
        this.players.remove(player);
        Profile.profiles.get(player).setParty(new Party(player));
        player.sendMessage(String.format("You have left %s's party", this.leader.getName()));
    }

    public void joinLobby(Lobby lobby) {
        for (Player player : this.players) {
            lobby.playerJoin(player);
        }
    }

    public void leaveLobby(Lobby lobby) {
        for (Player player : this.players) {
            lobby.leave(player);
        }
    }

    public boolean isPartyLeader(Player player) {
        return this.leader.equals(player);
    }
}
