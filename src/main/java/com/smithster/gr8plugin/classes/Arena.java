package com.smithster.gr8plugin.classes;

import com.smithster.gr8plugin.gamemodes.gamemode;

import java.util.ArrayList;

public class Arena extends Plot {

    private gamemode gamemode;
    private Boolean isActive = false;
    private ArrayList<Team> teams = new ArrayList<Team>();

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
}
