package com.smithster.gr8plugin.lobby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.smithster.gr8plugin.arena.Arena;
import com.smithster.gr8plugin.gamemodes.Gamemode;
import static com.smithster.gr8plugin.Plugin.server;
import static com.smithster.gr8plugin.utils.Plot.plots;

import com.smithster.gr8plugin.utils.Data;
import com.smithster.gr8plugin.utils.Party;
import com.smithster.gr8plugin.utils.Plot;
import com.smithster.gr8plugin.utils.Profile;

public class Lobby {

    public static HashMap<String, Lobby> lobbies = new HashMap<String, Lobby>();

    private ObjectId _id;
    private String name;
    private HashMap<Player, Location> players = new HashMap<Player, Location>();
    private Integer limit = 10;
    private Plot plot;
    private ArrayList<Arena> arenas = new ArrayList<Arena>();
    private VoteAgent vote;

    public Lobby(String plotName, String name) {
        // this.plotName = plotName;
        this.name = name;
        this.plot = plots.get(plotName);
        lobbies.put(name, this);
    }

    public void save() {
        Document lobby = new Document();

        lobby.put("_id", this._id);
        lobby.put("name", this.name);
        lobby.put("plotName", this.plot.getName());

        ObjectId insertedId = Data.save("lobbies", lobby);
        this._id = insertedId == null ? this._id : insertedId;
    }

    public static void load(Document document) {
        String name = (String) document.get("name");
        String plotName = (String) document.get("plotName");
        Lobby lobby = new Lobby(plotName, name);
        lobby._id = (ObjectId) document.get("_id");
        return;
    }

    public static void remove(Lobby lobby) {
        lobbies.remove(lobby.name);
        Data.remove("lobbies", lobby._id);
    }

    public void finishVote() {
        Arena arena = this.callVote();
        
        if (arena == null) {
            for (Player player : this.players.keySet()) {
                player.sendMessage("Players must vote before starting a match.");
            }
            return;
        }

        if (!arena.isSetup()){
            for (Player player : this.players.keySet()) {
                player.sendMessage("This arena hasn't been setup correctly");
            }
        }

        for (Player player : this.players.keySet()) {
            Profile profile = Profile.profiles.get(player.getUniqueId());
            if (!profile.isPartyLeader()) {
                continue;
            }
            Party party = profile.getParty();
            party.joinArena(arena);
        }
        arena.start(this);
        this.vote = null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void playerJoin(Player player) {
        this.players.put(player, player.getLocation());
        Profile.profiles.get(player.getUniqueId()).setLobby(this);
    }

    public boolean isLobbyFull() {
        if (this.players.size() >= this.limit) {
            return true;
        }

        return false;
    }

    public void startVote() {
        this.vote = new VoteAgent(this.arenas);
    }

    public void vote(Player player, Arena arena) {
        this.vote.vote(player, arena);
    }

    public Arena callVote() {
        return this.vote.getWinner();
    }

    public void enter(Player player) {
        if (this.isLobbyFull()) {
            player.sendMessage("This lobby is currently full");
            return;
        }
        Location entry = this.plot.getEntryLoc();
        if (entry == null) {
            player.sendMessage("Unable to join, an entry location hasn't been set for this Lobby.");
            return;
        } else {
            this.playerJoin(player);
            player.teleport(entry);
            if (this.vote == null) {
                this.startVote();
            }
            return;
        }
    }

    public void moveBackToLobby(Player player) {
        player.teleport(this.plot.getEntryLoc());
        if (this.vote == null) {
            this.startVote();
        }
    }

    public void leave(Player player) {
        player.teleport(this.players.get(player));
        this.players.remove(player);
        if (this.vote == null) {
            return;
        }
        this.vote.removeVote(player);
    }

    public boolean containsPlayer(Player player) {
        return this.players.containsKey(player);
    }

    public Plot getPlot(){
        return this.plot;
    }
}
