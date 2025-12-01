package uk.smithster.arenas.data.dataSchemas;

import java.util.ArrayList;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.lobby.LobbyStart;

public class LobbyStartSchema extends DataSchema {
    static final String schemaType = "lobbyStart";
    static final String path = "./saved_data/lobbyStarts.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, LobbyStart.jsonData, LobbyStart.class);

    String world;
    ArrayList<Integer> pos;
    String lobby;

    public String getPath() {
        return path;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public LobbyStartSchema(LobbyStart lobbyStart) {
        this.id = lobbyStart.getId();
        this.world = lobbyStart.getLoc().getWorld().getName();
        this.pos = Data.getXYZArrayList(lobbyStart.getLoc());
        this.lobby = lobbyStart.getLobby().getName();
    }
}
