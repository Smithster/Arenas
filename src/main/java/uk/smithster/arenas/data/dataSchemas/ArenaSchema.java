package uk.smithster.arenas.data.dataSchemas;

import java.util.Set;

import uk.smithster.arenas.arena.Arena;

public class ArenaSchema extends DataSchema {
    static final String schemaType = "arena";
    static final String path = "./saved_data/arenas.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, Arena.jsonData, Arena.class);

    String name;
    String plotName;
    String gamemode;
    Set<String> teams;

    public String getPath() {
        return path;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public ArenaSchema(Arena arena) {
        this.id = arena.getId();
        this.name = arena.getName();
        this.plotName = arena.getPlot().getName();
        this.gamemode = arena.getGamemode().getType();
        this.teams = arena.getTeams();
    }
}