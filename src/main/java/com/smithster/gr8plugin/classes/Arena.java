package com.smithster.gr8plugin.classes;
import com.smithster.gr8plugin.gamemodes.gamemode;

import java.util.ArrayList;

public class Arena extends Plot {

    private gamemode gamemode;
    private Boolean isActive = false;  
    private ArrayList<Team> teams = new ArrayList<Team>();
    private ArrayList<Spawn> spawns = new ArrayList<Spawn>();

    public void toggleActiveState() {
        if (isActive) {
            this.isActive = false;
            return;
        }
        this.isActive = true;
    }

    public void addTeam(Team team) {
        if (this.teams.contains(team)){
            return;
        }
        this.teams.add(team);
    }

    public void removeTeam( Team team ) {
        this.teams.remove(team);
    }

    public void clearTeams(){
        this.teams.clear();
    }

    public void addSpawn(Spawn spawn){
        if (this.spawns.contains(spawn)){
            return;
        }
        this.spawns.add(spawn);
    }
    
    public void removeSpawn(Spawn spawn){
        this.spawns.remove(spawn);
    }

    public void setGamemode(gamemode gamemode) {
        this.gamemode = gamemode;
    }

    public gamemode getGamemode(gamemode gamemode) {
        return this.gamemode;
    }
}
