package com.smithster.gr8plugin.classes;

import java.util.ArrayList;

import org.bukkit.Location;

public class Trigger {
    private Integer blockX;
    private Integer blockY;
    private Integer blockZ;
    private String world;
    private boolean active = true;

    public boolean isActive() {
        return this.active;
    }

    public boolean isTriggered(Location loc) {

        if (this.isActive() && loc.getBlockX() == this.blockX && loc.getBlockY() == this.blockY
                && loc.getBlockZ() == this.blockZ && loc.getWorld().getName() == this.world) {
            return true;
        }

        return false;
    }

    public void setLocation(ArrayList<Integer> pos, String world) {
        this.blockX = pos.get(0);
        this.blockY = pos.get(1);
        this.blockZ = pos.get(2);
        this.world = world;
    }

}
