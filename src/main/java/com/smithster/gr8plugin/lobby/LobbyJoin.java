package com.smithster.gr8plugin.lobby;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;
import org.bson.types.ObjectId;

// import static com.smithster.gr8plugin.Plugin.lobbyJoins;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.utils.Data;

public class LobbyJoin {
    // Static stored map of lobbyJoins
    public static HashMap<Location, LobbyJoin> lobbyJoins = new HashMap<Location, LobbyJoin>();

    // Class variables
    private ObjectId _id;
    private Location loc;
    private boolean active = false;
    private Lobby lobby;

    // constructors
    public LobbyJoin(Lobby lobby) {
        this.lobby = lobby;
    }

    // Static methods for lobbyJoins

    public static Boolean isLobbyJoin(Block block) {
        Location loc = block.getLocation();
        return lobbyJoins.get(loc) == null ? false : true;
    }

    public static LobbyJoin getLobbyJoin(Block block) {
        Location loc = block.getLocation();

        return lobbyJoins.get(loc);
    }

    // Class methods
    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean b) {
        this.active = b;
    }

    public boolean isLocated(Location loc) {

        if (this.loc.equals(loc)) {
            return true;
        }

        return false;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
        lobbyJoins.put(this.loc, this);
    }

    public void attach(Lobby lobby) {
        this.lobby = lobby;
    }

    public Lobby getLobby() {
        return this.lobby;
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
        document.put("active", this.active);

        ObjectId insertedId = Data.save("lobbyJoins", document);
        this._id = insertedId == null ? this._id : insertedId;
    }

    public static void load(Document document) {
        if (document.get("lobby") == null || document.get("pos") == null || document.get("world") == null) {
            return;
        }
        Lobby lobby = Lobby.lobbies.get((String) document.get("lobby"));
        LobbyJoin join = new LobbyJoin(lobby);
        join._id = (ObjectId) document.get("_id");
        World world = Plugin.server.getWorld((String) document.get("world"));
        ArrayList<Integer> pos = (ArrayList<Integer>) document.get("pos");
        Location loc = Data.getLocation(world, pos);
        join.setActive((boolean) document.get("active"));
        join.setLocation(loc);
    }

    public static void remove(Location loc) {
        LobbyJoin join = lobbyJoins.get(loc);
        lobbyJoins.remove(loc);
        Data.remove("lobbyJoins", join._id);
    }

    public void join(Player player) {
        this.lobby.enter(player);
    }

}
