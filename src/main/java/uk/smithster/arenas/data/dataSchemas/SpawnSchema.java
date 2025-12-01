package uk.smithster.arenas.data.dataSchemas;

import uk.smithster.arenas.team.Spawn;

public class SpawnSchema extends DataSchema {
    static final String schemaType = "spawn";
    static final String path = "./saved_data/spawns.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, Spawn.jsonData, Spawn.class);

    String name;
    String plotName;

    public String getPath() {
        return path;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public SpawnSchema(Spawn spawn) {
        this.id = spawn.getId();
        this.name = spawn.getName();
        this.plotName = spawn.getPlot().getName();
    }
}
