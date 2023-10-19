package com.smithster.gr8plugin.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.World;

import com.smithster.gr8plugin.utils.Data;

import static com.smithster.gr8plugin.Plugin.server;

public class Plot {

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

    public void setWorld(World world) {
        this.world = world;
        this.save();
    }

    // public void setPos1(ArrayList<Integer> pos1) {
    // this.pos1 = pos1;
    // this.save();
    // }

    public void setPos1(Location pos1) {
        this.world = pos1.getWorld();
        this.pos1 = pos1;
        this.save();
    }

    // public void setPos2(ArrayList<Integer> pos2) {
    // this.pos2 = pos2;
    // this.save();
    // }
    public void setPos2(Location pos2) {
        this.pos2 = pos2;
        this.save();
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

    public void setEntryLoc(Location loc) {
        this.entryLoc = loc;
    }

    public void setEntryLoc(ArrayList<Integer> pos, String world) {
        this.entryLoc = new Location(server.getWorld(world), (double) pos.get(0), (double) pos.get(1),
                (double) pos.get(2));
    }

    public void action() {
        return;
    }

    public void save() {
        Document plot = new Document();

        plot.put("name", this.name);
        plot.put("world", this.world.getName());
        plot.put("pos1", getXYZArrayList(this.pos1));
        plot.put("pos2", getXYZArrayList(this.pos2));

        Data.save("plots", plot);
    }

    public static ArrayList<Integer> getXYZArrayList(Location loc) {
        ArrayList<Integer> xyz = new ArrayList<Integer>();
        xyz.add(loc.getBlockX());
        xyz.add(loc.getBlockY());
        xyz.add(loc.getBlockZ());
        return xyz;
    }

    public static void load(Document document) {
        String name = (String) document.get("name");
        Plot plot = new Plot(name);
        plot.setWorld(server.getWorld((String) document.get("world")));
        if (plot.getWorld() != null) {
            ArrayList<Integer> xyz1 = (ArrayList<Integer>) document.get("pos1");
            Location pos1 = new Location(server.getWorld(name), (double) xyz1.get(0), (double) xyz1.get(1),
                    (double) xyz1.get(2));
            plot.setPos1(pos1);

            ArrayList<Integer> xyz2 = (ArrayList<Integer>) document.get("pos2");
            Location pos2 = new Location(server.getWorld(name), (double) xyz2.get(0), (double) xyz2.get(1),
                    (double) xyz2.get(2));
            plot.setPos2(pos2);
        }

        return;
    }

    public static void delete(String plot) {
        plots.remove(plot);
    }
}
