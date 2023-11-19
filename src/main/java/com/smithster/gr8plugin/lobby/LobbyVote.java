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
import com.smithster.gr8plugin.arena.Arena;
import com.smithster.gr8plugin.utils.Data;

public class LobbyVote {
    public static HashMap<Location, LobbyVote> lobbyVotes = new HashMap<Location, LobbyVote>();

    private ObjectId _id;
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

    public void save() {
        if (this.loc == null) {
            return;
        }

        Document document = new Document();

        document.put("_id", this._id);
        document.put("world", this.loc.getWorld().getName());
        document.put("pos", Data.getXYZArrayList(this.loc));
        document.put("lobby", this.lobby.getName());
        document.put("arena", this.arena.getName());

        ObjectId insertedId = Data.save("lobbyVotes", document);
        this._id = insertedId == null ? this._id : insertedId;
    }

    public static void load(Document document) {
        if (document.get("lobby") == null || document.get("pos") == null || document.get("world") == null) {
            return;
        }
        Lobby lobby = Lobby.lobbies.get((String) document.get("lobby"));
        Arena arena = Arena.arenas.get((String) document.get("arena"));
        LobbyVote vote = new LobbyVote(lobby, arena);
        vote._id = (ObjectId) document.get("_id");
        World world = Plugin.server.getWorld((String) document.get("world"));
        ArrayList<Integer> pos = (ArrayList<Integer>) document.get("pos");
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
