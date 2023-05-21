package com.smithster.gr8plugin.classes;

import org.bson.Document;
import org.bukkit.Location;

import com.mongodb.client.model.Updates;

import static com.smithster.gr8plugin.Plugin.Plots;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class plot {

    private String name;
    private String world;
    private String[] pos1;
    private String[] pos2;

    public void createPlot() {
        Document document = new Document();
        document.put("name", this.name);

        Plots.insertOne(document);

    }

    public void setWorld(String field, String value) {
        Plots.updateOne(eq("name", this.name), Updates.set(field, value), null);
    }

    public void setPos(String pos, String[] values) {
        Plots.updateOne(eq("name", this.name), Updates.set(pos, values));
    }
}
