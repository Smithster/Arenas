package uk.smithster.arenas.data.dataSchemas;

import com.google.gson.JsonArray;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.lobby.Lobby;

public class LobbySchema extends DataSchema {
    static final String schemaType = "lobby";
    static JsonArray jsonData = Data.lobbies;
    static final String path = "./saved_data/lobbies.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, jsonData);

    Integer id;
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
