package uk.smithster.arenas.arena;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import uk.smithster.arenas.Plugin;
import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.data.dataSchemas.FlagSchema;
import uk.smithster.arenas.team.Team;

public class Flag {

    public static HashMap<Location, Flag> flags = new HashMap<Location, Flag>();

    private UUID id;
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

    public UUID getId() {
        return this.id;
    }

    public void save() {
        FlagSchema flag = new FlagSchema(this);

        UUID insertedId = Data.save(flag);
        this.id = insertedId == null ? this.id : insertedId;
    }

    public static void load(JsonObject document) {
        Team team = Team.teams.get(document.get("team").getAsString());
        Location loc = Data.getLocation(Plugin.server.getWorld(document.get("world").getAsString()),
                Data.getIntArrayList((JsonArray) document.get("loc")));
        new Flag(loc, team);
        return;
    }

}
