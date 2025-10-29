package uk.smithster.arenas.gamemodes;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import uk.smithster.arenas.team.Team;
import uk.smithster.arenas.utils.Profile;

public class TeamDeathmatch extends Gamemode {

    Objective objective;

    public TeamDeathmatch() {
        this.setType("TDM");
        this.setMaxScore(10);
    }

    public void handleKill(Profile killer, Profile killed) {
        killer.getTeam().gainPoint(1);
    }

    public void setScoreboard(Scoreboard scoreboard){
        this.objective = scoreboard.registerNewObjective("score", "dummy", ChatColor.DARK_RED + "Points");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void resetScores(){
        this.objective.unregister();
    }

    public void addTeam(Team team){
        team.initScore(this.objective.getScore(team.hasColor()? team.getChatColor() + team.getName() : team.getName()));
    }

    public void resetScoreName(Team team, String lastName){
        Integer oldScore = this.objective.getScore(lastName).getScore();
        Score newScore = this.objective.getScore(team.hasColor()? team.getChatColor() + team.getName() : team.getName());
        newScore.setScore(oldScore);
        this.objective.getScoreboard().resetScores(lastName);
        team.initScore(newScore);
    }

    // public void resetScoreboard(HashMap<String, Team> teams){
    //     this.objective.getScoreboard().resetScores(getType());
    //     for (Team team : teams.values()){
            
    //         Score score = this.objective.getScore(team.hasColor()? team.getChatColor() + team.getName() : team.getName());
        
    //         score.setScore(team.getScore());
    //         team.initScore(score);
    //     }
    //     return;
    // }
}
