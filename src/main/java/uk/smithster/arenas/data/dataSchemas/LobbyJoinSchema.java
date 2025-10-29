package uk.smithster.arenas.data.dataSchemas;

import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.JsonObject;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.lobby.LobbyJoin;

public class LobbyJoinSchema extends DataSchema{
    static final String schemaType = "lobbyJoin";
    static JsonObject jsonData = Data.lobbyJoins;
    static final String path = "./saved_data/lobbyJoins.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, jsonData);

    UUID id;
    String world;
    ArrayList<Integer> pos;
    String lobby;
    boolean active;

    public String getPath() {
        return path;
    }

    public LobbyJoinSchema(LobbyJoin lobbyJoin) {
        this.id = lobbyJoin.getId();
        this.world = lobbyJoin.getLoc().getWorld().getName();
        this.pos = Data.getXYZArrayList(lobbyJoin.getLoc());
        this.lobby = lobbyJoin.getLobby().getName();
        this.active = lobbyJoin.getActive();
    }
}
