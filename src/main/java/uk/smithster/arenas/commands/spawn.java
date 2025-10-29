package uk.smithster.arenas.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.smithster.arenas.team.Spawn;
import uk.smithster.arenas.utils.Plot;

public class spawn implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            return false;
        }

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (args[0].equals("list")) {
            for (String name : Spawn.spawns.keySet()) {
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

            if (Spawn.spawns.containsKey(name)) {
                sender.sendMessage(String.format("There is already a spawn called %s", name));
                return true;
            }

            if (Plot.plots.containsKey(plotName)) {
                Spawn spawn = new Spawn(name, plotName);
                spawn.save();
                sender.sendMessage(String.format("You have successfully created a spawn called %s", name));
            } else {
                sender.sendMessage("You must first create a plot with the same name you intend to use for the spawn");
            }

            return true;
        }

        // if (args[0].equals("remove")) {
        // if (args.length >= 2) {
        // if (Spawn.spawns.containsKey(args[1])) {
        // Spawn.remove(Spawn.spawns.get(args[1]));
        // return true;
        // } else {
        // player.sendMessage(String.format("No lobby by the name %s", args[1]));
        // return true;
        // }
        // }
        // }

        return false;

    }

}
