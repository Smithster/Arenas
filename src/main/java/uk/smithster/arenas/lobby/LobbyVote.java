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
import uk.smithster.arenas.arena.Arena;
import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.data.dataSchemas.LobbyVoteSchema;

public class LobbyVote {
    public static HashMap<Location, LobbyVote> lobbyVotes = new HashMap<Location, LobbyVote>();

    private UUID id;
    private Location loc;
    private Lobby lobby;
    private Arena arena;

    public LobbyVote(Lobby lobby, Arena arena) {
        this.lobby = lobby;
        this.arena = arena;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
        lobbyVotes.put(loc, this);
    }

    public Lobby getLobby() {
        return this.lobby;
    }

    public static Boolean isLobbyVote(Block block) {
        Location loc = block.getLocation();
        return lobbyVotes.get(loc) == null ? false : true;
    }

    public static LobbyVote getLobbyVote(Block block) {
        Location loc = block.getLocation();

        return lobbyVotes.get(loc);
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

        LobbyVoteSchema lobbyVote = new LobbyVoteSchema(this);

        UUID insertedId = Data.save(lobbyVote);
        this.id = insertedId == null ? this.id : insertedId;
    }

    public static void load(JsonObject document) {
        if (document.get("lobby") == null || document.get("pos") == null || document.get("world") == null) {
            return;
        }
        Lobby lobby = Lobby.lobbies.get(document.get("lobby").getAsString());
        Arena arena = Arena.arenas.get(document.get("arena").getAsString());
        LobbyVote vote = new LobbyVote(lobby, arena);
        vote.id = UUID.fromString(document.get("id").getAsString());
        World world = Plugin.server.getWorld(document.get("world").getAsString());
        ArrayList<Integer> pos = Data.getIntArrayList((JsonArray) document.get("pos"));
        Location loc = Data.getLocation(world, pos);
        vote.setLocation(loc);
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public Arena getArena() {
        return this.arena;
    }

    public void vote(Player player) {
        this.lobby.vote(player, this.arena);
    }
}
