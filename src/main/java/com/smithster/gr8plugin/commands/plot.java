package com.smithster.gr8plugin.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mongodb.client.model.Updates;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.smithster.gr8plugin.Plugin.Plots;

public class plot implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        player.sendMessage(args);

        if (!(args.length == 2)) {
            return false;
        }

        if (args[1] == "1") {
            player.sendMessage("this is equal to 1");
        }

        Document plot = Plots.find(eq("name", args[0])).first();

        if (plot == null) {
            Plots.insertOne(new Document("name", args[0]));
        }

        Location loc = player.getLocation();

        Plots.updateOne(eq("name", args[0]), Updates.set(args[1], loc));

        return true;
    }

}
