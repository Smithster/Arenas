package com.smithster.gr8plugin.classes;

import com.smithster.gr8plugin.gamemodes.gamemode;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Arena extends Plot {

    private gamemode gamemode;
    private Boolean isActive = false;
    private ArrayList<Team> teams = new ArrayList<Team>();
    private ArrayList<Player> players = new ArrayList<Player>();

    public void toggleActiveState() {
        this.isActive = !this.isActive;
    }

    public Boolean isActive() {
        return this.isActive;
    }

    public void addTeam(Team team) {
        if (this.teams.contains(team)) {
            return;
        }
        this.teams.add(team);
    }

    public Team getTeam(Integer i) {
        return this.teams.get(i);
    }

    public void removeTeam(Team team) {
        this.teams.remove(team);
    }

    public void clearTeams() {
        this.teams.clear();
    }

    public void setGamemode(gamemode gamemode) {
        this.gamemode = gamemode;
    }

    public gamemode getGamemode(gamemode gamemode) {
        return this.gamemode;
    }

    public void start() {
        this.toggleActiveState();
        for (Team team : teams) {
            team.spawn();
        }
    }

    public void playerJoin(Player player) {
        if (this.teams.size() > 1) {
            Team team = this.getTeam(this.players.size() % this.teams.size());
            this.players.add(player);
        }

    }
}
