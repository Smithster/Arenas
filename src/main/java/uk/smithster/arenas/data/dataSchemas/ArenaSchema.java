package uk.smithster.arenas.data.dataSchemas;

import java.util.Set;

import com.google.gson.JsonArray;

import uk.smithster.arenas.arena.Arena;
import uk.smithster.arenas.data.Data;

public class ArenaSchema extends DataSchema {
    static final String schemaType = "arena";
    static JsonArray jsonData = Data.arenas;
    static final String path = "./saved_data/arenas.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, jsonData);

    Integer id;
    String name;
    String plotName;
    String gamemode;
    Set<String> teams;

    public String getPath() {
        return path;
    }

    public ArenaSchema(Arena arena) {
        this.id = arena.getId();
        this.name = arena.getName();
        this.plotName = arena.getPlot().getName();
        this.gamemode = arena.getGamemode().getType();
        this.teams = arena.getTeams();
    }
}