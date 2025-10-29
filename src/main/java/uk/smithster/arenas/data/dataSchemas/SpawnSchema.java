package uk.smithster.arenas.data.dataSchemas;

import java.util.UUID;

import com.google.gson.JsonObject;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.team.Spawn;

public class SpawnSchema extends DataSchema {
    static final String schemaType = "spawn";
    static JsonObject jsonData = Data.spawns;
    static final String path = "./saved_data/spawns.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, jsonData);

    UUID id;
    String name;
    String plotName;

    public String getPath() {
        return path;
    }

    public SpawnSchema(Spawn spawn) {
        this.id = spawn.getId();
        this.name = spawn.getName();
        this.plotName = spawn.getPlot().getName();
    }
}
