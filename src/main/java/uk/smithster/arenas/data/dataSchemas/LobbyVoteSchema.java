package uk.smithster.arenas.data.dataSchemas;

import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.JsonObject;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.lobby.LobbyVote;

public class LobbyVoteSchema extends DataSchema {
    static final String schemaType = "lobbyVote";
    static JsonObject jsonData = Data.lobbyVotes;
    static final String path = "./saved_data/lobbyVotes.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, jsonData);

    UUID id;
    String world;
    ArrayList<Integer> pos;
    String lobby;
    String arena;

    public String getPath() {
        return path;
    }

    public LobbyVoteSchema(LobbyVote lobbyVote) {
        this.id = lobbyVote.getId();
        this.world = lobbyVote.getLoc().getWorld().getName();
        this.pos = Data.getXYZArrayList(lobbyVote.getLoc());
        this.lobby = lobbyVote.getLobby().getName();
        this.arena = lobbyVote.getArena().getName();
    }
}
