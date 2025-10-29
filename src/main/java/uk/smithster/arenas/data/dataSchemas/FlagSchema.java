package uk.smithster.arenas.data.dataSchemas;

import java.util.ArrayList;
import java.util.UUID;

import uk.smithster.arenas.arena.Flag;
import uk.smithster.arenas.data.Data;

public class FlagSchema extends DataSchema {
    static String schemaType = "flag";
    static String path = "./saved_data/flags.json";
    // static JsonArray jsonData = Data.flags;

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, jsonData);

    UUID id;
    ArrayList<Integer> loc;
    String world;
    String team;

    public FlagSchema(Flag flag) {
        this.id = flag.getId();
        this.loc = Data.getXYZArrayList(flag.getLocation());
        this.world = flag.getLocation().getWorld().getName();
        this.team = flag.getTeam().getName();
    }
}
