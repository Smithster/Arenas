package uk.smithster.arenas.data.dataSchemas;

import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.JsonObject;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.loadouts.LoadoutSelect;

public class LoadoutSelectSchema extends DataSchema {
    static final String schemaType = "loadoutSelect";
    static JsonObject jsonData = Data.loadoutSelects;
    static final String path = "./saved_data/loadoutSelects.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, jsonData);

    UUID id;
    ArrayList<Integer> xyz;
    String world;
    String loadout;

    public String getPath() {
        return path;
    }

    public LoadoutSelectSchema(LoadoutSelect loadoutSelect) {
        this.id = loadoutSelect.getId();
        this.world = loadoutSelect.getLoc().getWorld().getName();
        this.xyz = Data.getXYZArrayList(loadoutSelect.getLoc());
        this.loadout = loadoutSelect.getLoadout().getName();
    }
}
