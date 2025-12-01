package uk.smithster.arenas.data.dataSchemas;

import java.util.ArrayList;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.lobby.LobbyJoin;

public class LobbyJoinSchema extends DataSchema {
    static final String schemaType = "lobbyJoin";
    static final String path = "./saved_data/lobbyJoins.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, LobbyJoin.jsonData, LobbyJoin.class);

    String world;
    ArrayList<Integer> pos;
    String lobby;
    boolean active;

    public String getPath() {
        return path;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public LobbyJoinSchema(LobbyJoin lobbyJoin) {
        this.id = lobbyJoin.getId();
        this.world = lobbyJoin.getLoc().getWorld().getName();
        this.pos = Data.getXYZArrayList(lobbyJoin.getLoc());
        this.lobby = lobbyJoin.getLobby().getName();
        this.active = lobbyJoin.getActive();
    }
}
