package uk.smithster.arenas.lobby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import uk.smithster.arenas.Plugin;
import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.data.Storable;
import uk.smithster.arenas.data.dataSchemas.LobbyLeaveSchema;
import uk.smithster.arenas.data.dataSchemas.SchemaMetaData;

public class LobbyLeave implements Storable {

    public static HashMap<Location, LobbyLeave> lobbyLeaves = new HashMap<Location, LobbyLeave>();

    private UUID id;
    private Location loc;
    private Lobby lobby;

    public static JsonObject jsonData = new JsonObject();
    
    public SchemaMetaData getStoreMetaData() {
        return LobbyLeaveSchema.metaData;
    }

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

    public Location getLoc() {
        return this.loc;
    }

    public UUID getId() {
        return this.id;
    }

    public void save() {
        if (this.loc == null) {
            return;
        }

        LobbyLeaveSchema lobbyLeave = new LobbyLeaveSchema(this);

        UUID insertedId = Data.save(lobbyLeave);
        this.id = insertedId == null ? this.id : insertedId;
    }

    public static void load(JsonObject document) {
        if (document.get("lobby") == null || document.get("pos") == null || document.get("world") == null) {
            return;
        }
        Lobby lobby = Lobby.lobbies.get(document.get("lobby").getAsString());
        LobbyLeave leave = new LobbyLeave(lobby);
        leave.id = UUID.fromString(document.get("id").getAsString());
        World world = Plugin.server.getWorld(document.get("world").getAsString());
        ArrayList<Integer> pos = Data.getIntArrayList((JsonArray) document.get("pos"));
        Location loc = Data.getLocation(world, pos);
        leave.setLocation(loc);
    }

    public void leave(Player player) {
        this.lobby.leave(player);
    }
}
