package com.smithster.gr8plugin.classes;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import static com.smithster.gr8plugin.Plugin.profiles;

public class Team {

    private ArrayList<Player> players;

    private Integer score;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void playerJoin(Player player) {
        this.players.add(player);
        profiles.get(player.getUniqueId()).setTeam(this);
    }

    public void playerLeave(Player player) {
        this.players.remove(player);
    }

    public void clear() {
        this.players.clear();
    }

    public boolean hasPlayer(Player player) {
        return this.players.contains(player);
    }

    public void gainPoint(Integer points) {
        this.score = +points;
    }

    public void removePoint(Integer points) {
        this.score = -points;
    }

    public Integer getScore() {
        return this.score;
    }

}
