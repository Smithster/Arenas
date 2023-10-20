package com.smithster.gr8plugin.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import static com.smithster.gr8plugin.Plugin.server;

import com.smithster.gr8plugin.utils.Data;

public class Lobby extends Plot {

    public static HashMap<String, Lobby> lobbies = new HashMap<String, Lobby>();

    private ArrayList<UUID> players;
    private Integer limit;

    public Lobby(String name) {
        this.setName(name);
        Plot plot = plots.get(name);
        this.setWorld(plot.getWorld());
        this.setPos1(plot.getPos1());
        this.setPos2(plot.getPos2());
        this.setEntryLoc(plot.getEntryLoc());
        lobbies.put(name, this);
    }

    public void save() {
        Document lobby = new Document();

        lobby.put("name", this.getName());
        lobby.put("world", this.getWorld() != null ? this.getWorld().getName() : null);
        lobby.put("pos1", this.getPos1() != null ? getXYZArrayList(this.getPos1()) : null);
        lobby.put("pos2", this.getPos2() != null ? getXYZArrayList(this.getPos2()) : null);

        Data.save("lobbies", lobby);
    }

    public static void load(Document document) {
        String name = (String) document.get("name");
        Plot lobby = new Lobby(name);
        if (document.get("world") != null) {
            lobby.setWorld(server.getWorld((String) document.get("world")));
        }

        if (document.get("pos1") != null) {
            ArrayList<Integer> xyz1 = (ArrayList<Integer>) document.get("pos1");
            Location pos1 = new Location(server.getWorld(name), (double) xyz1.get(0), (double) xyz1.get(1),
                    (double) xyz1.get(2));
            lobby.setPos1(pos1);
        }

        if (document.get("pos2") != null) {
            ArrayList<Integer> xyz2 = (ArrayList<Integer>) document.get("pos2");
            Location pos2 = new Location(server.getWorld(name), (double) xyz2.get(0), (double) xyz2.get(1),
                    (double) xyz2.get(2));
            lobby.setPos2(pos2);
        }

        return;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void playerJoin(UUID playerUUID) {
        this.players.add(playerUUID);
    }

    public boolean isLobbyFull() {
        if (this.players.size() >= this.limit) {
            return true;
        }

        return false;
    }

    public void enter(Player player) {
        this.playerJoin(player.getUniqueId());
        player.teleport(this.getEntryLoc());
    }
}
