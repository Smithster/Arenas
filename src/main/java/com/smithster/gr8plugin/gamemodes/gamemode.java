package com.smithster.gr8plugin.gamemodes;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.smithster.gr8plugin.classes.Arena;
import com.smithster.gr8plugin.classes.Team;
import com.smithster.gr8plugin.utils.Profile;

public class gamemode {

    public static HashMap<String, Class<?>> gamemodes = new HashMap<String, Class<?>>();

    public void init() {
        gamemodes.put("TDM", TeamDeathmatch.class);
    }

    private String type;
    private ArrayList<Team> teams;
    private Integer maxScore;

    public void gainPoint(Team team) {
        if (this.teams.contains(team)) {
            team.gainPoint(10);
            if (this.hasWon(team)) {
                finish();
            }
        }
    }

    public void finish() {
        System.out.println("The game has been finished");
    }

    public boolean hasWon(Team team) {
        if (team.getScore() >= this.maxScore) {
            return true;
        }
        return false;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setMaxScore(Integer score) {
        this.maxScore = score;
    }

    public Integer getMaxScore() {
        return this.maxScore;
    }

    public void handleKill(Profile killer, Profile killed) {
        return;
    }

    public Team checkForWinner() {
        for (Team team : this.teams) {
            if (this.hasWon(team)) {
                return team;
            }
        }
        return null;
    }

    public void handleWin(Team team) {

    }
}
