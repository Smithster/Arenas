package com.smithster.gr8plugin.gamemodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import com.smithster.gr8plugin.classes.Arena;
import com.smithster.gr8plugin.classes.Team;
import com.smithster.gr8plugin.utils.Profile;

public class Gamemode {

    public static HashMap<String, Gamemode> gamemodes = new HashMap<String, Gamemode>();

    public static void init() {
        gamemodes.put("TDM", new TeamDeathmatch());
    }

    private String type;
    private ArrayList<Team> teams;
    private Integer maxScore;

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
        killer.getPlayer().sendMessage("this is not the right gamemode");
        return;
    }

    // public Team checkForWinner(ArrayList<Team> teams) {
    // for (Team team : teams) {
    // if (this.hasWon(team)) {
    // return team;
    // }
    // }
    // return null;
    // }

    public void handleWin(Team team) {

    }

    public void setScoreboard(Scoreboard scoreboard, Set<Team> teams){
        return;
    }
}
