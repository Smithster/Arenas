package com.smithster.gr8plugin.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.Location;
import org.bukkit.World;

import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.utils.Data;

import static com.smithster.gr8plugin.Plugin.server;

public class Plot {
    private ObjectId _id;
    private String name;
    private World world;
    private Location pos1;
    private Location pos2;
    private Location entryLoc;

    public static HashMap<String, Plot> plots = new HashMap<String, Plot>();

    public Plot(String name) {
        this.name = name;
        plots.put(name, this);
    }

    public Plot() {
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

    public void save() {
        Document plot = new Document();

        plot.put("_id", this._id);
        plot.put("name", this.name);
        plot.put("world", this.world != null ? this.world.getName() : null);
        plot.put("pos1", this.pos1 != null ? Data.getXYZArrayList(this.pos1) : null);
        plot.put("pos2", this.pos2 != null ? Data.getXYZArrayList(this.pos2) : null);
        plot.put("entry", this.entryLoc != null ? Data.getXYZRotArrayList(this.entryLoc) : null);

        ObjectId insertedId = Data.save("plots", plot);
        this._id = insertedId == null ? this._id : insertedId;
    }

    public static void load(Document document) {
        String name = (String) document.get("name");
        Plot plot = new Plot(name);

        plot._id = (ObjectId) document.get("_id");

        World world = server.getWorld(document.get("world") != null ? (String) document.get("world") : "");

        if (world != null) {
            plot.setWorld(world);
        } else {
            return;
        }

        if (document.get("pos1") != null) {
            ArrayList<Integer> xyz1 = (ArrayList<Integer>) document.get("pos1");
            Location pos1 = Data.getLocation(world, xyz1);
            plot.setPos1(pos1);
        }

        if (document.get("pos2") != null) {
            ArrayList<Integer> xyz2 = (ArrayList<Integer>) document.get("pos2");
            Location pos2 = Data.getLocation(world, xyz2);
            plot.setPos2(pos2);
        }

        if (document.get("entry") != null) {
            ArrayList<Integer> xyz = (ArrayList<Integer>) document.get("entry");
            Location entry = Data.getRotLocation(world, xyz);
            plot.setEntryLoc(entry);
        }

        return;
    }

    public static void remove(Plot plot) {
        plots.remove(plot.getName());
        Data.remove("plots", plot._id);
    }
}
