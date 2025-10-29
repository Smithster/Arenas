package uk.smithster.arenas.data.dataSchemas;

import java.util.UUID;

import com.google.gson.JsonObject;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.lobby.Lobby;

public class LobbySchema extends DataSchema {
    static final String schemaType = "lobby";
    static JsonObject jsonData = Data.lobbies;
    static final String path = "./saved_data/lobbies.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, jsonData);

    UUID id;
    String name;
    String plotName;

    public String getPath() {
        return path;
    }

    public LobbySchema(Lobby lobby) {
        this.id = lobby.getId();
        this.name = lobby.getName();
        this.plotName = lobby.getPlot().getName();
    }
}
