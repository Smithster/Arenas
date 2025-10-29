package uk.smithster.arenas.lobby;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import uk.smithster.arenas.Plugin;
import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.data.dataSchemas.LobbyJoinSchema;

public class LobbyJoin {
    // Static stored map of lobbyJoins
    public static HashMap<Location, LobbyJoin> lobbyJoins = new HashMap<Location, LobbyJoin>();

    // Class variables
    private Integer id;
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

    public Location getLoc() {
        return this.loc;
    }

    public boolean getActive() {
        return this.active;
    }

    public Integer getId() {
        return this.id;
    }

    public void save() {
        if (this.loc == null) {
            return;
        }

        LobbyJoinSchema lobbyJoin = new LobbyJoinSchema(this);

        Integer insertedId = Data.save(lobbyJoin);
        this.id = insertedId == null ? this.id : insertedId;
    }

    public static void load(JsonObject document) {
        if (document.get("lobby") == null || document.get("pos") == null || document.get("world") == null) {
            return;
        }
        Lobby lobby = Lobby.lobbies.get(document.get("lobby").getAsString());
        LobbyJoin join = new LobbyJoin(lobby);
        join.id = document.get("id").getAsInt();
        World world = Plugin.server.getWorld(document.get("world").getAsString());
        ArrayList<Integer> pos = Data.getIntArrayList((JsonArray) document.get("pos"));
        Location loc = Data.getLocation(world, pos);
        join.setActive(document.get("active").getAsBoolean());
        join.setLocation(loc);
    }

    public static void remove(Location loc) {
        LobbyJoin join = lobbyJoins.get(loc);
        lobbyJoins.remove(loc);
        Data.remove(new LobbyJoinSchema(join));
    }

    public void join(Player player) {
        this.lobby.enter(player);
    }

}
