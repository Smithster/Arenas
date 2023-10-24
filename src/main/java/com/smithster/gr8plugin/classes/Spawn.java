package com.smithster.gr8plugin.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.entity.Player;

import com.smithster.gr8plugin.utils.Data;

public class Spawn {

    public static HashMap<String, Spawn> spawns = new HashMap<String, Spawn>();

    private ObjectId _id;
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

    public String getName() {
        return this.name;
    }

    public void save() {
        Document spawn = new Document();

        spawn.put("_id", this._id);
        spawn.put("name", this.name);
        spawn.put("plot", this.plot.getName());

        ObjectId insertedId = Data.save("spawns", spawn);
        this._id = insertedId == null ? this._id : insertedId;
    }

    public static void load(Document document) {
        String name = (String) document.get("name");
        Plot plot = Plot.plots.get((String) document.get("plot"));
        Spawn spawn = new Spawn(name, plot.getName());
        spawn._id = (ObjectId) document.get("_id");
        return;
    }

}
