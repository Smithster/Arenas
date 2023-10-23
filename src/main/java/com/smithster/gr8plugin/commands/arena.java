package com.smithster.gr8plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import com.mongodb.client.MongoDatabase;
import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.classes.Arena;
import com.smithster.gr8plugin.classes.Lobby;

import static com.smithster.gr8plugin.classes.Plot.plots;
import static com.smithster.gr8plugin.classes.Arena.arenas;

public class arena implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            return false;
        }

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (args[0].equals("list")) {
            for (String name : arenas.keySet()) {
                player.sendMessage(name);
            }
            return true;
        }

        if (args[0].equals("create")) {
            if (args.length <= 2) {
                return false;
            }

            String plotName = args[1];
            String name = args[2];

            if (arenas.containsKey(name)) {
                sender.sendMessage(String.format("There is already a arena called %s", name));
                return true;
            }

            if (plots.containsKey(plotName)) {
                Arena arena = new Arena(plotName, name);
                arena.save();
                sender.sendMessage(String.format("You have successfully created a arena called %s", name));
            } else {
                sender.sendMessage("You must first create a plot with the same name you intend to use for the arena");
            }

            return true;
        }

        if (args[0].equals("remove")) {
            if (args.length >= 2) {
                if (arenas.containsKey(args[1])) {
                    Arena.remove(arenas.get(args[1]));
                    return true;
                } else {
                    player.sendMessage(String.format("No lobby by the name %s", args[1]));
                    return true;
                }
            }
        }

        return false;

    }

}
