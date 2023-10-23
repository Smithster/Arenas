package com.smithster.gr8plugin.classes;

import com.smithster.gr8plugin.gamemodes.gamemode;
import com.smithster.gr8plugin.utils.Data;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.entity.Player;

public class Arena {

    public static HashMap<String, Arena> arenas = new HashMap<String, Arena>();

    private ObjectId _id;
    private String name;
    private gamemode gamemode;
    private Boolean isActive = false;
    private Plot plot;
    private ArrayList<Team> teams = new ArrayList<Team>();
    private ArrayList<Player> players = new ArrayList<Player>();

    public Arena(String plotName, String name) {
        this.plot = Plot.plots.get(plotName);
        this.name = name;
        arenas.put(name, this);
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
        if (this.teams.contains(team)) {
            return;
        }
        this.teams.add(team);
    }

    public Team getTeam(Integer i) {
        return this.teams.get(i);
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

    public void playerJoin(Player player) {
        if (this.teams.size() > 1) {
            Team team = this.getTeam(this.players.size() % this.teams.size());
            this.players.add(player);
        }

    }

    public void save() {
        Document arena = new Document();

        arena.put("_id", this._id);
        arena.put("name", this.name);
        arena.put("plotName", this.plot.getName());

        ObjectId insertedId = Data.save("arenas", arena);
        this._id = insertedId == null ? this._id : insertedId;
    }

    public static void load(Document document) {
        String name = (String) document.get("name");
        String plotName = (String) document.get("plotName");
        Arena arena = new Arena(plotName, name);
        arena._id = (ObjectId) document.get("_id");
        return;
    }

    public static void remove(Arena arena) {
        arenas.remove(arena.name);
        Data.remove("lobbies", arena._id);
    }
}
