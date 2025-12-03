package uk.smithster.arenas.data.dataSchemas;

import java.util.ArrayList;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.utils.Plot;

public class PlotSchema extends DataSchema{
    static final String schemaType = "plot";
    static final String path = "./saved_data/plots.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, Plot.jsonData, Plot::load);

    String name;
    String world;
    ArrayList<Integer> pos1;
    ArrayList<Integer> pos2;
    ArrayList<Integer> entryLoc;

    public String getPath() {
        return path;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public PlotSchema (Plot plot) {
        this.id = plot.getId();
        this.name = plot.getName();
        this.world = plot.getWorld() != null? plot.getWorld().getName() : null;
        this.pos1 = plot.getPos1() != null ? Data.getXYZArrayList(plot.getPos1()) : null;
        this.pos2 = plot.getPos2() != null ? Data.getXYZArrayList(plot.getPos2()) : null;
        this.entryLoc = plot.getEntryLoc() != null ? Data.getXYZRotArrayList(plot.getEntryLoc()) : null;
    };
}
