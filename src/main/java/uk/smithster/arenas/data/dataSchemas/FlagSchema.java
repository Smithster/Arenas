package uk.smithster.arenas.data.dataSchemas;

import java.util.ArrayList;

import uk.smithster.arenas.arena.Flag;
import uk.smithster.arenas.data.Data;

public class FlagSchema extends DataSchema {
    static final String schemaType = "flag";
    static final String path = "./saved_data/flags.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, Flag.jsonData, Flag::load);

    ArrayList<Integer> loc;
    String world;
    String team;

    public String getPath() {
        return path;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public FlagSchema(Flag flag) {
        this.id = flag.getId();
        this.loc = Data.getXYZArrayList(flag.getLocation());
        this.world = flag.getLocation().getWorld().getName();
        this.team = flag.getTeam().getName();
    }
}
