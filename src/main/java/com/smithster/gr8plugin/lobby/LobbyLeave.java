package com.smithster.gr8plugin.lobby;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.utils.Data;

public class LobbyLeave {

    public static HashMap<Location, LobbyLeave> lobbyLeaves = new HashMap<Location, LobbyLeave>();

    private ObjectId _id;
    private Location loc;
    private Lobby lobby;

    public LobbyLeave(Lobby lobby) {
        this.lobby = lobby;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
        lobbyLeaves.put(loc, this);
    }

    public Lobby getLobby() {
        return this.lobby;
    }

    public static Boolean isLobbyLeave(Block block) {
        Location loc = block.getLocation();
        return lobbyLeaves.get(loc) == null ? false : true;
    }

    public static LobbyLeave getLobbyLeave(Block block) {
        Location loc = block.getLocation();

        return lobbyLeaves.get(loc);
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

        ObjectId insertedId = Data.save("lobbyLeaves", document);
        this._id = insertedId == null ? this._id : insertedId;
    }

    public static void load(Document document) {
        if (document.get("lobby") == null || document.get("pos") == null || document.get("world") == null) {
            return;
        }
        Lobby lobby = Lobby.lobbies.get((String) document.get("lobby"));
        LobbyLeave leave = new LobbyLeave(lobby);
        leave._id = (ObjectId) document.get("_id");
        World world = Plugin.server.getWorld((String) document.get("world"));
        ArrayList<Integer> pos = (ArrayList<Integer>) document.get("pos");
        Location loc = Data.getLocation(world, pos);
        leave.setLocation(loc);
    }

    public void leave(Player player) {
        this.lobby.leave(player);
    }
}
