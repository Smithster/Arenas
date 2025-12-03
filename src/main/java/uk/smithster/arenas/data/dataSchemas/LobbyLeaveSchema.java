package uk.smithster.arenas.data.dataSchemas;

import java.util.ArrayList;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.lobby.LobbyLeave;

public class LobbyLeaveSchema extends DataSchema{
    static final String schemaType = "lobbyLeave";
    static final String path = "./saved_data/lobbyLeaves.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, LobbyLeave.jsonData, LobbyLeave::load);

    String world;
    ArrayList<Integer> pos;
    String lobby;

    public String getPath() {
        return path;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public LobbyLeaveSchema(LobbyLeave lobbyLeave) {
        this.id = lobbyLeave.getId();
        this.world = lobbyLeave.getLoc().getWorld().getName();
        this.pos = Data.getXYZArrayList(lobbyLeave.getLoc());
        this.lobby = lobbyLeave.getLobby().getName();
    }
}
