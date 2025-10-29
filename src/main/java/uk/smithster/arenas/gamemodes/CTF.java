package uk.smithster.arenas.gamemodes;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import uk.smithster.arenas.team.Team;
import uk.smithster.arenas.utils.Profile;

public class CTF extends Gamemode {

    Objective objective;

    public CTF() {
        this.setType("CTF");
        this.setMaxScore(3);
    }

    public void handleCapture(Profile capturer) {
        capturer.getTeam().gainPoint(1);
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.objective = scoreboard.registerNewObjective("score", "dummy", ChatColor.DARK_RED + "Points");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void resetScores() {
        this.objective.unregister();
    }

    public void addTeam(Team team) {
        team.initScore(
                this.objective.getScore(team.hasColor() ? team.getChatColor() + team.getName() : team.getName()));
    }

    public void resetScoreName(Team team, String lastName) {
        Integer oldScore = this.objective.getScore(lastName).getScore();
        Score newScore = this.objective
                .getScore(team.hasColor() ? team.getChatColor() + team.getName() : team.getName());
        newScore.setScore(oldScore);
        this.objective.getScoreboard().resetScores(lastName);
        team.initScore(newScore);
    }

}
