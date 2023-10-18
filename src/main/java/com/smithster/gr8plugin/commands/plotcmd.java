package com.smithster.gr8plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mongodb.client.model.Updates;
import com.smithster.gr8plugin.classes.Plot;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;

import static com.smithster.gr8plugin.classes.Plot.plots;

import java.util.ArrayList;
import java.util.Arrays;

public class plotcmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label,
            String[] args) {

        if (!(sender instanceof Player) || args.length <= 1) {
            return false;
        }

        Player player = (Player) sender;

        if (args[0].equals("create") && args.length == 2) {
            String name = args[1];

            if (!plots.containsKey(name)) {
                Plot plot = new Plot(name);
                plot.save();
                return true;
            }

            player.sendMessage("A plot already exists with this name");
            return false;

        } else if (args[0].equals("create")) {
            player.sendMessage("Incorrect arguments. To create a plot use /plot create [name]");
            return false;
        }

        Plot plot = plots.get(args[0]);
        String func = args[1];

        if (func.equals("set")) {

            if (args.length <= 2) {
                player.sendMessage("set requires a field to set: /plot [name] set [field]");
                return false;
            }

            String field = args[2];

            if (field.equals("world") && args.length == 4) {
                setWorld(plot, args[3]);
                return true;
            }

            if (field.equals("pos")) {

                if (args[3].equals("1") || args[3].equals("2")) {
                    ArrayList<Integer> xyz = new ArrayList<Integer>();
                    xyz.add(player.getLocation().getBlockX());
                    xyz.add(player.getLocation().getBlockY());
                    xyz.add(player.getLocation().getBlockZ());
                    setPos(plot, args[3], xyz);
                    return true;
                }
            }
        }

        if (func.equals("remove")) {
            deletePlot(args[0]);
            return true;
        }

        return false;
    }

    private static void setWorld(Plot plot, String value) {
        plot.setWorld(value);
    }

    private static void setPos(Plot plot, String pos, ArrayList<Integer> values) {
        if (pos.equals("1")) {
            plot.setPos1(values);
        } else {
            plot.setPos2(values);
        }
    }

    private static void deletePlot(String plotName) {
        Plot.delete(plotName);
    }
}
