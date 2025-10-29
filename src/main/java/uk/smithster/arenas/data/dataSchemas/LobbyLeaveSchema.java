package uk.smithster.arenas.data.dataSchemas;

import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.JsonObject;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.lobby.LobbyLeave;

public class LobbyLeaveSchema extends DataSchema{
    static final String schemaType = "lobbyLeave";
    static JsonObject jsonData = Data.lobbyLeaves;
    static final String path = "./saved_data/lobbyLeaves.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, jsonData);

    UUID id;
    String world;
    ArrayList<Integer> pos;
    String lobby;

    public String getPath() {
        return path;
    }

    public LobbyLeaveSchema(LobbyLeave lobbyLeave) {
        this.id = lobbyLeave.getId();
        this.world = lobbyLeave.getLoc().getWorld().getName();
        this.pos = Data.getXYZArrayList(lobbyLeave.getLoc());
        this.lobby = lobbyLeave.getLobby().getName();
    }
}
