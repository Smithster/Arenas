package com.smithster.gr8plugin.classes;

import com.smithster.gr8plugin.gamemodes.TeamDeathmatch;
import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.gamemodes.Gamemode;
import com.smithster.gr8plugin.utils.Data;
import com.smithster.gr8plugin.utils.Party;
import com.smithster.gr8plugin.utils.Profile;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class Arena {

    public static HashMap<String, Arena> arenas = new HashMap<String, Arena>();

    private ObjectId _id;
    private String name;
    private Gamemode gamemode;
    private Scoreboard scoreboard;
    private Boolean isActive = false;
    private Plot plot;
    private HashMap<String, Team> teamsMap = new HashMap<String, Team>();
    private HashMap<Team, org.bukkit.scoreboard.Team> teams = new HashMap<Team, org.bukkit.scoreboard.Team>();
    private ArrayList<Player> players = new ArrayList<Player>();

    public Arena(String plotName, String name) {
        this.plot = Plot.plots.get(plotName);
        this.name = name;
        arenas.put(name, this);
        this.scoreboard = Plugin.server.getScoreboardManager().getNewScoreboard();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void toggleActiveState() {
        this.isActive = !this.isActive;
    }

    public Boolean isActive() {
        return this.isActive;
    }

    public void addTeam(Team team) {
        if (this.teams.containsKey(team)) {
            return;
        }
        org.bukkit.scoreboard.Team bukkitTeam = this.scoreboard.registerNewTeam(team.getName());
        team.setBukkitTeam(bukkitTeam);
        this.teamsMap.put(team.getName(), team);
        this.teams.put(team, bukkitTeam);
        this.gamemode.addTeam(team);
    }

    // public void resetScoreboard() {
    //     this.gamemode.resetScoreboard(teamsMap);
    // }

    public Team getTeam(String team){
        return this.teamsMap.get(team);
    }

    public org.bukkit.scoreboard.Team getTeam(Team team) {
        return this.teams.get(team);
    }

    public void removeTeam(Team team) {
        this.teams.remove(team);
    }

    public void clearTeams() {
        this.teams.clear();
    }

    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
        gamemode.setScoreboard(this.scoreboard);
    }

    public boolean hasGamemode(){
        return this.gamemode == null? false : true;
    }
    public Gamemode getGamemode() {
        return this.gamemode;
    }

    public void start(Lobby lobby) {
        this.toggleActiveState();
        for (Team team : teams.keySet()) {
            team.spawn();
        }
    }

    // public void playerJoin(Player player) {
    // if (this.teams.size() > 1) {
    // // Team team = this.getTeam(this.players.size() % this.teams.size());
    // this.players.add(player);
    // }
    // }

    public void enter(Party party) {
        Integer minSize = 0;
        Team smallest = null;
        for (Team team : this.teams.keySet()) {
            if (minSize > team.size() || smallest == null) {
                smallest = team;
                minSize = team.size();
            }
        }
        party.setArena(this);
        party.joinTeam(smallest);
    }

    public void handleKill(Profile killer, Profile killed) {
        this.gamemode.handleKill(killer, killed);
        Team winner = this.checkForWinner();
        if (winner == null) {
            return;
        }

        this.handleWin(winner);
    }

    public Team checkForWinner() {
        for (Team team : this.teams.keySet()) {
            if (this.gamemode.hasWon(team)) {
                return team;
            }
        }
        return null;
    }

    public void handleWin(Team winner) {
        for (Player player : this.players) {
            player.sendMessage(String.format("The winner was team %s!", winner.getName()));
            Profile profile = Profile.profiles.get(player.getUniqueId());
            if (profile.isPartyLeader()) {
                profile.getParty().leaveArena();
            }
        }
        this.toggleActiveState();
        this.players.clear();
        for (Team team : this.teams.keySet()) {
            team.clear();
        }
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        for (Team team : this.teams.keySet()) {
            team.playerLeave(player);
        }
        this.players.remove(player);
    }

    public void clearPlayers() {
        this.players.clear();
    }

    public Scoreboard getScoreboard(){
        return this.scoreboard;
    }

    public boolean isSetup(){
        for (Team team : teams.keySet()){
            Spawn spawn = team.getSpawn();
            if (spawn == null || spawn.getSpawnLoc() == null){
                return false;
            }
        }
        return true;
    }

    public void save() {
        Document arena = new Document();

        arena.put("_id", this._id);
        arena.put("name", this.name);
        arena.put("plotName", this.plot.getName());
        if (this.gamemode != null){
            arena.put("gamemode", this.gamemode.getType());
        }
        if (this.teams != null){
            arena.put("teams", Data.getTeamNames(this.teams.keySet()));
        }
        
        ObjectId insertedId = Data.save("arenas", arena);
        this._id = insertedId == null ? this._id : insertedId;
    }

    public static void load(Document document) {
        String name = (String) document.get("name");
        String plotName = (String) document.get("plotName");
        Arena arena = new Arena(plotName, name);
        arena._id = (ObjectId) document.get("_id");
        if (document.get("gamemode") != null) {
            arena.setGamemode(Gamemode.gamemodes.get((String) document.get("gamemode")));
        }
        if (document.get("teams") != null) {
            for (String team : (ArrayList<String>) document.get("teams")) {
                arena.addTeam(Team.teams.get(team));
            }
        }

        return;
    }

    public static void remove(Arena arena) {
        arenas.remove(arena.name);
        Data.remove("lobbies", arena._id);
    }
}
