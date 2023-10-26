package com.smithster.gr8plugin.classes;

import com.smithster.gr8plugin.gamemodes.TeamDeathmatch;
import com.smithster.gr8plugin.gamemodes.Gamemode;
import com.smithster.gr8plugin.utils.Data;
import com.smithster.gr8plugin.utils.Party;
import com.smithster.gr8plugin.utils.Profile;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.entity.Player;

public class Arena {

    public static HashMap<String, Arena> arenas = new HashMap<String, Arena>();

    private ObjectId _id;
    private String name;
    private Gamemode gamemode;
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

    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    public Gamemode getGamemode() {
        return this.gamemode;
    }

    public void start(Lobby lobby) {
        this.toggleActiveState();
        for (Team team : teams) {
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
        for (Team team : this.teams) {
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
        for (Team team : this.teams) {
            if (this.gamemode.hasWon(team)) {
                return team;
            }
            for (Player player : team.getPlayers()) {
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
        for (Team team : this.teams) {
            team.clear();
        }
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        for (Team team : this.teams) {
            team.playerLeave(player);
        }
        this.players.remove(player);
    }

    public void clearPlayers() {
        this.players.clear();
    }

    public void save() {
        Document arena = new Document();

        arena.put("_id", this._id);
        arena.put("name", this.name);
        arena.put("plotName", this.plot.getName());
        arena.put("teams", Data.getTeamNames(this.teams));
        arena.put("gamemode", this.gamemode.getType());

        ObjectId insertedId = Data.save("arenas", arena);
        this._id = insertedId == null ? this._id : insertedId;
    }

    public static void load(Document document) {
        String name = (String) document.get("name");
        String plotName = (String) document.get("plotName");
        Arena arena = new Arena(plotName, name);
        arena._id = (ObjectId) document.get("_id");
        if (document.get("teams") != null) {
            for (String team : (ArrayList<String>) document.get("teams")) {
                arena.addTeam(Team.teams.get(team));
            }
        }

        if (document.get("gamemode") != null) {
            arena.setGamemode(Gamemode.gamemodes.get((String) document.get("gamemode")));
        }

        return;
    }

    public static void remove(Arena arena) {
        arenas.remove(arena.name);
        Data.remove("lobbies", arena._id);
    }
}
