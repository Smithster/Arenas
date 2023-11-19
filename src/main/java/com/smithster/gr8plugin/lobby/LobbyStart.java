package com.smithster.gr8plugin.lobby;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.utils.Data;

public class LobbyStart {

    public static HashMap<Location, LobbyStart> lobbyStarts = new HashMap<Location, LobbyStart>();

    private Location loc;
    private ObjectId _id;
    private Lobby lobby;

    public LobbyStart(Lobby lobby) {
        this.lobby = lobby;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
        lobbyStarts.put(loc, this);
    }

    public Lobby getLobby() {
        return this.lobby;
    }

    public static Boolean isLobbyStart(Block block) {
        Location loc = block.getLocation();
        return lobbyStarts.get(loc) == null ? false : true;
    }

    public static LobbyStart getLobbyStart(Block block) {
        Location loc = block.getLocation();

        return lobbyStarts.get(loc);
    }

    public void save() {
        if (this.loc == null) {
            return;
        }

        Document document = new Document();

        document.put("_id", this._id);
        document.put("world", this.loc.getWorld().getName());
        document.put("pos", Data.getXYZArrayList(this.loc));
        document.put("lobby", this.lobby.getName());

        ObjectId insertedId = Data.save("lobbyStarts", document);
        this._id = insertedId == null ? this._id : insertedId;
    }

    public static void load(Document document) {
        if (document.get("lobby") == null || document.get("pos") == null || document.get("world") == null) {
            return;
        }
        Lobby lobby = Lobby.lobbies.get((String) document.get("lobby"));
        LobbyStart start = new LobbyStart(lobby);
        start._id = (ObjectId) document.get("_id");
        World world = Plugin.server.getWorld((String) document.get("world"));
        ArrayList<Integer> pos = (ArrayList<Integer>) document.get("pos");
        Location loc = Data.getLocation(world, pos);
        start.setLocation(loc);
    }

    public void startGame() {
        this.lobby.finishVote();
    }
}
