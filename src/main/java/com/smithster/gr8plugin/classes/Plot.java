package com.smithster.gr8plugin.classes;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;

public class Plot {

    private String name;
    private String world;
    private ArrayList<Integer> pos1;
    private ArrayList<Integer> pos2;

    public void setName(String name) {
        this.name = name;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public void setPos1(ArrayList<Integer> pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(ArrayList<Integer> pos2) {
        this.pos2 = pos2;
    }

    public String getName() {
        return this.name;
    }

    public String getWorld() {
        return this.world;
    }

    public ArrayList<Integer> getPos1() {
        return this.pos1;
    }

    public ArrayList<Integer> getPos2() {
        return this.pos2;
    }

    public ArrayList<Integer> getMaxCorner() {
        Integer maxX = Math.max(pos1.get(0), pos2.get(0));
        Integer maxY = Math.max(pos1.get(1), pos2.get(1));
        Integer maxZ = Math.max(pos1.get(2), pos2.get(2));
        ArrayList<Integer> maxList = new ArrayList<>(Arrays.asList(maxX, maxY, maxZ));
        return maxList;
    }

    public ArrayList<Integer> getMinCorner() {
        Integer minX = Math.min(pos1.get(0), pos2.get(0));
        Integer minY = Math.min(pos1.get(1), pos2.get(1));
        Integer minZ = Math.min(pos1.get(2), pos2.get(2));
        ArrayList<Integer> minList = new ArrayList<>(Arrays.asList(minX, minY, minZ));
        return minList;
    }

    public boolean contains(Location loc) {

        Integer x = loc.getBlockX();
        Integer y = loc.getBlockY();
        Integer z = loc.getBlockZ();
        String world = loc.getWorld().getName();

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
        if (world.equals(getWorld())) {
            return false;
        }
        return true;
    }
}
