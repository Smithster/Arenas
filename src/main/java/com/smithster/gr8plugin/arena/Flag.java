package com.smithster.gr8plugin.arena;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.team.Team;
import com.smithster.gr8plugin.utils.Data;

public class Flag {

    public static HashMap<Location, Flag> flags = new HashMap<Location, Flag>();

    private Location loc;
    private Player carrier;
    private Team team;

    public Flag(Team team) {
        this.team = team;
    }

    public Flag(Location loc, Team team) {
        this.team = team;
        this.setLocation(loc);
    }

    public Location getLocation() {
        return this.loc;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
        flags.put(loc, this);
    }

    public void setCarrier(Player player) {
        this.carrier = player;
    }

    public boolean isCarried() {
        return this.carrier == null ? false : true;
    }

    public void save() {
        Document document = new Document();

        document.put("loc", Data.getXYZArrayList(loc));
        document.put("world", this.loc.getWorld().getName());
        document.put("team", this.team.getName());

        Data.save("flags", document);
    }

    public static void load(Document document) {
        Team team = Team.teams.get((String) document.get("team"));
        Location loc = Data.getLocation(Plugin.server.getWorld((String) document.get("world")),
                (ArrayList<Integer>) document.get("loc"));
        new Flag(loc, team);
        return;
    }

}
