package uk.smithster.arenas.gamemodes;

import java.util.HashMap;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import uk.smithster.arenas.team.Team;
import uk.smithster.arenas.utils.Profile;

public class Gamemode {

    public static HashMap<String, Gamemode> gamemodes = new HashMap<String, Gamemode>();

    private Objective objective;

    public static void init() {
        gamemodes.put("TDM", new TeamDeathmatch());
    }

    private String type;
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
    //     for (Team team : teams) {
    //         if (this.hasWon(team)) {
    //             return team;
    //         }
    //     }
    //     return null;
    // }

    public void handleWin(Team team) {

    }

    public void setScoreboard(Scoreboard scoreboard) {
        return;
    }

    public void addTeam(Team team) {
        return;
    }

    // public void resetScoreboard(HashMap<String, Team> teamsMap){
    // return;
    // }

    public void resetScoreName(Team team, String lastName) {
        Integer oldScore = this.objective.getScore(lastName).getScore();
        Score newScore = this.objective
                .getScore(team.hasColor() ? team.getChatColor() + team.getName() : team.getName());
        newScore.setScore(oldScore);
        this.objective.getScoreboard().resetScores(lastName);
        team.initScore(newScore);
    }
}
