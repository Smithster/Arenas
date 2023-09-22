package com.smithster.gr8plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mongodb.client.model.Updates;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;

import static com.smithster.gr8plugin.Plugin.plotsCollection;
import java.util.Arrays;

public class plotcmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player) || args.length <= 1) {
            return false;
        }

        Player player = (Player) sender;

        if (args[0].equals("create") && args.length == 2) {
            String world = player.getLocation().getWorld().getName();
            String name = args[1];

            if (plotsCollection.find(eq("name", name)).first() == null) {
                generatePlot(name);
                setWorld(name, world);
                return true;
            }

            player.sendMessage("A plot already exists with this name");
            return false;

        } else if (args[0].equals("create")) {
            player.sendMessage("Incorrect arguments. To create a plot just use /plot create [name]");
            return false;
        }

        String name = args[0];
        String func = args[1];

        if (func.equals("set")) {

            if (args.length <= 2) {
                player.sendMessage("set requires a field to set: /plot [name] set [field]");
                return false;
            }

            String field = args[2];

            if (field.equals("world") && args.length == 4) {
                setWorld(name, args[3]);
                return true;
            }

            if (field.equals("pos")) {

                if (args[3].equals("pos1") || args[3].equals("pos2")) {
                    Integer[] xyz = { player.getLocation().getBlockX(), player.getLocation().getBlockY(),
                            player.getLocation().getBlockZ() };
                    setPos(name, args[3], xyz);
                    return true;
                }
            }

        }

        if (func.equals("remove")) {
            deletePlot(name);
            return true;
        }

        return false;
    }

    private static void generatePlot(String name) {
        Document document = new Document();
        document.put("name", name);

        plotsCollection.insertOne(document);
    }

    private static void setWorld(String name, String value) {
        plotsCollection.updateOne(eq("name", name), Updates.set("world", value));
    }

    private static void setPos(String name, String pos, Integer[] values) {
        plotsCollection.updateOne(eq("name", name), Updates.set(pos, Arrays.asList(values)));
    }

    private static void deletePlot(String name) {
        plotsCollection.findOneAndDelete(eq("name", name));
    }
}
