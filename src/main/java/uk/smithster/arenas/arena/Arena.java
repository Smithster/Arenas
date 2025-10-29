package uk.smithster.arenas.arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import uk.smithster.arenas.Plugin;
import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.data.dataSchemas.ArenaSchema;
import uk.smithster.arenas.gamemodes.Gamemode;
import uk.smithster.arenas.lobby.Lobby;
import uk.smithster.arenas.team.Spawn;
import uk.smithster.arenas.team.Team;
import uk.smithster.arenas.utils.Party;
import uk.smithster.arenas.utils.Plot;
import uk.smithster.arenas.utils.Profile;

public class Arena {

    public static HashMap<String, Arena> arenas = new HashMap<String, Arena>();

    private UUID id;
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
    // this.gamemode.resetScoreboard(teamsMap);
    // }

    public Team getTeam(String team) {
        return this.teamsMap.get(team);
    }

    public org.bukkit.scoreboard.Team getTeam(Team team) {
        return this.teams.get(team);
    }

    public Set<String> getTeams() {
        return this.teamsMap.keySet();
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

    public boolean hasGamemode() {
        return this.gamemode == null ? false : true;
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

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public boolean isSetup() {
        for (Team team : teams.keySet()) {
            Spawn spawn = team.getSpawn();
            if (spawn == null || spawn.getSpawnLoc() == null) {
                return false;
            }
        }
        return true;
    }

    public UUID getId() {
        return this.id;
    }

    public Plot getPlot() {
        return this.plot;
    }

    public void save() {
        ArenaSchema arenaData = new ArenaSchema(this);
        
        UUID insertedId = Data.save(arenaData);
        this.id = insertedId == null ? this.id : insertedId;
    }

    public static void load(JsonObject data) {
        String name = data.get("name").getAsString();
        String plotName = data.get("plotName").getAsString();
        Arena arena = new Arena(plotName, name);
        arena.id = UUID.fromString(data.get("id").getAsString());
        if (data.get("gamemode") != null) {
            arena.setGamemode(Gamemode.gamemodes.get(data.get("gamemode").getAsString()));
        }
        if (data.get("teams") != null) {
            for (String team : Data.getStringArrayList((JsonArray) data.get("teams"))) {
                arena.addTeam(Team.teams.get(team));
            }
        }

        return;
    }

    public static void remove(Arena arena) {
        arenas.remove(arena.name);
        Data.remove(new ArenaSchema(arena));
    }
}
