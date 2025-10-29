package uk.smithster.arenas.team;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.gson.JsonObject;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.data.dataSchemas.SpawnSchema;
import uk.smithster.arenas.utils.Plot;

public class Spawn {

    public static HashMap<String, Spawn> spawns = new HashMap<String, Spawn>();

    private UUID id;
    private String name;
    private Plot plot;

    public Spawn(String name, String plotName) {
        this.plot = Plot.plots.get(plotName);
        this.name = name;
        spawns.put(name, this);
    }

    public void spawn(Player player) {
        player.teleport(this.plot.getEntryLoc());
    }

    public Location getSpawnLoc() {
        return this.plot.getEntryLoc();
    }

    public String getName() {
        return this.name;
    }

    public Plot getPlot() {
        return this.plot;
    }

    public UUID getId() {
        return this.id;
    }

    public void save() {
        SpawnSchema spawnData = new SpawnSchema(this);

        UUID insertedId = Data.save(spawnData);
        this.id = insertedId == null ? this.id : insertedId;
    }

    public static void load(JsonObject document) {
        String name = document.get("name").getAsString();
        Plot plot = Plot.plots.get(document.get("plot").getAsString());
        Spawn spawn = new Spawn(name, plot.getName());
        spawn.id = UUID.fromString(document.get("id").getAsString());
        return;
    }

}
