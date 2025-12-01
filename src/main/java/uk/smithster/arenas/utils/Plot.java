package uk.smithster.arenas.utils;

import static uk.smithster.arenas.Plugin.LOGGER;
import static uk.smithster.arenas.Plugin.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.data.Storable;
import uk.smithster.arenas.data.dataSchemas.PlotSchema;
import uk.smithster.arenas.data.dataSchemas.SchemaMetaData;

public class Plot implements Storable {
    private UUID id;
    private String name;
    private World world;
    private Location pos1;
    private Location pos2;
    private Location entryLoc;

    public static HashMap<String, Plot> plots = new HashMap<String, Plot>();

    public static JsonObject jsonData = new JsonObject();
    
    public SchemaMetaData getStoreMetaData() {
        return PlotSchema.metaData;
    }

    public Plot(String name) {
        this.name = name;
        plots.put(name, this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    // public void setPos1(ArrayList<Integer> pos1) {
    // this.pos1 = pos1;
    // this.save();
    // }

    public void setPos1(Location pos1) {
        this.world = pos1.getWorld();
        this.pos1 = pos1;
    }

    // public void setPos2(ArrayList<Integer> pos2) {
    // this.pos2 = pos2;
    // this.save();
    // }
    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

    public String getName() {
        return this.name;
    }

    public World getWorld() {
        return this.world;
    }

    public Location getPos1() {
        return this.pos1;
    }

    public Location getPos2() {
        return this.pos2;
    }

    public ArrayList<Integer> getMaxCorner() {
        Integer maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        Integer maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        Integer maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
        ArrayList<Integer> maxList = new ArrayList<>(Arrays.asList(maxX, maxY, maxZ));
        return maxList;
    }

    public ArrayList<Integer> getMinCorner() {
        Integer minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        Integer minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        Integer minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        ArrayList<Integer> minList = new ArrayList<>(Arrays.asList(minX, minY, minZ));
        return minList;
    }

    public boolean contains(Location loc) {

        if (this.pos1 == null || this.pos2 == null) {
            return false;
        }

        Integer x = loc.getBlockX();
        Integer y = loc.getBlockY();
        Integer z = loc.getBlockZ();
        World world = loc.getWorld();

        ArrayList<Integer> maxLimit = getMaxCorner();
        ArrayList<Integer> minLimit = getMinCorner();
        if (x < minLimit.get(0) || x > maxLimit.get(0)) {
            return false;
        }
        if (y < minLimit.get(1) || y > maxLimit.get(1)) {
            return false;
        }
        if (z < minLimit.get(2) || z > maxLimit.get(2)) {
            return false;
        }
        if (!world.equals(this.world)) {
            return false;
        }
        return true;
    }

    public ArrayList<Integer> getCenter() {
        ArrayList<Integer> max = this.getMaxCorner();
        ArrayList<Integer> min = this.getMinCorner();

        Integer midX = (max.get(0) + min.get(0)) / 2;
        Integer midY = (max.get(1) + min.get(1)) / 2;
        Integer midZ = (max.get(2) + min.get(2)) / 2;

        ArrayList<Integer> midPoint = new ArrayList<>(Arrays.asList(midX, midY, midZ));
        return midPoint;
    }

    public Location getEntryLoc() {
        return this.entryLoc;
    }

    public boolean hasEntryLoc(){
        return this.entryLoc == null? false : true;
    }

    public void setEntryLoc(Location loc) {
        this.entryLoc = loc;
        this.save();
    }

    public void action() {
        return;
    }

    public UUID getId() {
        return this.id;
    }

    public void save() {
        PlotSchema plotData = new PlotSchema(this);

        UUID insertedId = Data.save(plotData);
        this.id = insertedId == null ? this.id : insertedId;
    }

    public static void load(JsonObject document) {
        String name = document.get("name").getAsString();
        LOGGER.info(name);
        Plot plot = new Plot(name);

        plot.id = UUID.fromString(document.get("id").getAsString());

        World world = server.getWorld(document.get("world") != null ? document.get("world").getAsString() : "");

        if (world != null) {
            plot.setWorld(world);
        } else {
            return;
        }

        if (document.get("pos1") != null) {
            ArrayList<Integer> xyz1 = Data.getIntArrayList((JsonArray) document.get("pos1"));
            Location pos1 = Data.getLocation(world, xyz1);
            plot.setPos1(pos1);
        }

        if (document.get("pos2") != null) {
            ArrayList<Integer> xyz2 = Data.getIntArrayList((JsonArray) document.get("pos2"));
            Location pos2 = Data.getLocation(world, xyz2);
            plot.setPos2(pos2);
        }

        if (document.get("entry") != null) {
            ArrayList<Integer> xyz = Data.getIntArrayList((JsonArray) document.get("entry"));
            Location entry = Data.getRotLocation(world, xyz);
            plot.setEntryLoc(entry);
        }

        return;
    }

    public static void remove(Plot plot) {
        plots.remove(plot.getName());
        Data.remove(new PlotSchema(plot));
    }
}
