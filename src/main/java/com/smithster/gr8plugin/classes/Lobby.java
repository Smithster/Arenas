package com.smithster.gr8plugin.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import com.smithster.gr8plugin.gamemodes.gamemode;
import static com.smithster.gr8plugin.Plugin.server;

import com.smithster.gr8plugin.utils.Data;

public class Lobby extends Plot {

    public static HashMap<String, Lobby> lobbies = new HashMap<String, Lobby>();

    private ObjectId _id;
    private ArrayList<Player> players;
    private Integer limit;
    private LobbyVote vote;
    private String plotName;
    private ArrayList<Arena> arenas;

    public Lobby(String plotName, String name) {
        this.plotName = plotName;
        Plot plot = plots.get(plotName);
        this.setWorld(plot.getWorld());
        this.setPos1(plot.getPos1());
        this.setPos2(plot.getPos2());
        this.setEntryLoc(plot.getEntryLoc());
        lobbies.put(name, this);
    }

    public void save() {
        Document lobby = new Document();

        lobby.put("_id", this._id);
        lobby.put("name", this.getName());
        lobby.put("plotName", this.plotName);
        // lobby.put("world", this.getWorld() != null ? this.getWorld().getName() :
        // null);
        // lobby.put("pos1", this.getPos1() != null ?
        // Data.getXYZArrayList(this.getPos1()) : null);
        // lobby.put("pos2", this.getPos2() != null ?
        // Data.getXYZArrayList(this.getPos2()) : null);

        ObjectId insertedId = Data.save("lobbies", lobby);
        this._id = insertedId == null ? this._id : insertedId;
    }

    public static void load(Document document) {
        String name = (String) document.get("name");
        String plotName = (String) document.get("plotName");
        Lobby lobby = new Lobby(plotName, name);
        lobby._id = (ObjectId) document.get("_id");
        // if (document.get("world") != null) {
        // lobby.setWorld(server.getWorld((String) document.get("world")));
        // }

        // if (document.get("pos1") != null) {
        // ArrayList<Integer> xyz1 = (ArrayList<Integer>) document.get("pos1");
        // Location pos1 = new Location(server.getWorld(name), (double) xyz1.get(0),
        // (double) xyz1.get(1),
        // (double) xyz1.get(2));
        // lobby.setPos1(pos1);
        // }

        // if (document.get("pos2") != null) {
        // ArrayList<Integer> xyz2 = (ArrayList<Integer>) document.get("pos2");
        // Location pos2 = new Location(server.getWorld(name), (double) xyz2.get(0),
        // (double) xyz2.get(1),
        // (double) xyz2.get(2));
        // lobby.setPos2(pos2);
        // }

        return;
    }

    public void finishVote() {
        Arena winner = this.callVote();
        for (Player player : this.players) {
            winner.playerJoin(player);
        }
        this.clearLobby();
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void playerJoin(Player player) {
        this.players.add(player);

    }

    public boolean isLobbyFull() {
        if (this.players.size() >= this.limit) {
            return true;
        }

        return false;
    }

    public void startVote() {
        this.vote = new LobbyVote(this.arenas);
    }

    public Arena callVote() {
        return this.vote.getWinner();
    }

    public void enter(Player player) {
        if (this.isLobbyFull()) {
            player.sendMessage("This lobby is currently full");
        }
        Location entry = this.getEntryLoc();
        if (entry == null) {
            player.sendMessage("Unable to join, an entry location hasn't been set for this Lobby.");
        } else {
            player.teleport(this.getEntryLoc());
        }
        this.playerJoin(player);
    }

    public void clearLobby() {
        this.vote = null;
        this.players.clear();
    }
}
