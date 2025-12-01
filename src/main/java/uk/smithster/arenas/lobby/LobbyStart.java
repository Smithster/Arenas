package uk.smithster.arenas.lobby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import uk.smithster.arenas.Plugin;
import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.data.Storable;
import uk.smithster.arenas.data.dataSchemas.LobbyStartSchema;
import uk.smithster.arenas.data.dataSchemas.SchemaMetaData;

public class LobbyStart implements Storable {

    public static HashMap<Location, LobbyStart> lobbyStarts = new HashMap<Location, LobbyStart>();

    private Location loc;
    private UUID id;
    private Lobby lobby;

    public static JsonObject jsonData = new JsonObject();
    
    public SchemaMetaData getStoreMetaData() {
        return LobbyStartSchema.metaData;
    }

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

        LobbyStartSchema lobbyStart = new LobbyStartSchema(this);

        UUID insertedId = Data.save(lobbyStart);
        this.id = insertedId == null ? this.id : insertedId;
    }

    public static void load(JsonObject document) {
        if (document.get("lobby") == null || document.get("pos") == null || document.get("world") == null) {
            return;
        }
        Lobby lobby = Lobby.lobbies.get(document.get("lobby").getAsString());
        LobbyStart start = new LobbyStart(lobby);
        start.id = UUID.fromString(document.get("id").getAsString());
        World world = Plugin.server.getWorld(document.get("world").getAsString());
        ArrayList<Integer> pos = Data.getIntArrayList((JsonArray) document.get("pos"));
        Location loc = Data.getLocation(world, pos);
        start.setLocation(loc);
    }

    public void startGame() {
        this.lobby.finishVote();
    }
}
