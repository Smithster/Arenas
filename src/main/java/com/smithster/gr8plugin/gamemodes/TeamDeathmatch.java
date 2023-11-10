package com.smithster.gr8plugin.gamemodes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.smithster.gr8plugin.classes.*;
import com.smithster.gr8plugin.utils.Profile;

public class TeamDeathmatch extends Gamemode {

    public TeamDeathmatch() {
        this.setType("TDM");
        this.setMaxScore(10);
    }

    public void handleKill(Profile killer, Profile killed) {
        killer.getTeam().gainPoint(1);
    }

    public void setScoreboard(Scoreboard scoreboard, Set<Team> teams){
        Objective objective = scoreboard.registerNewObjective("score", "dummy", "Points");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (Team team : teams){
            team.initScore(objective.getScore(team.getName()));
        }
    }
}
