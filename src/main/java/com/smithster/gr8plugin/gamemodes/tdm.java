package com.smithster.gr8plugin.gamemodes;

import java.util.ArrayList;
import com.smithster.gr8plugin.classes.*;

public class tdm implements gamemode {

    private ArrayList<Team> teams;
    private Integer maxScore = 50;

    public void gainPoint(Team team) {
        if (this.teams.contains(team)) {
            team.gainPoint(10);
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

}
