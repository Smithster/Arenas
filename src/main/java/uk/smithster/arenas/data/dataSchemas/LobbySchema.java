package uk.smithster.arenas.data.dataSchemas;

import uk.smithster.arenas.lobby.Lobby;

public class LobbySchema extends DataSchema {
    static final String schemaType = "lobby";
    static final String path = "./saved_data/lobbies.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, Lobby.jsonData, Lobby::load);

    String name;
    String plotName;

    public String getPath() {
        return path;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public LobbySchema(Lobby lobby) {
        this.id = lobby.getId();
        this.name = lobby.getName();
        this.plotName = lobby.getPlot().getName();
    }
}
