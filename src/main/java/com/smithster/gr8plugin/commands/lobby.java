package com.smithster.gr8plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.smithster.gr8plugin.classes.Lobby;
import static com.smithster.gr8plugin.classes.Lobby.lobbies;
import static com.smithster.gr8plugin.classes.Plot.plots;

public class lobby implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            return false;
        }

        if (args[0].equals("create")) {
            if (args.length == 1) {
                return false;
            }
            String name = args[1];

            if (plots.containsKey(name)) {
                Lobby lobby = new Lobby(name);
                lobby.save();
            } else {
                sender.sendMessage("You must first create a plot with the same name you intend to use for the lobby");
            }

            return true;
        }

        if (lobbies.containsKey(args[0])) {
            Lobby lobby = lobbies.get(args[0]);

            if (args[1].equals("join")) {
                Player player = (Player) sender;
                lobby.enter(player);
            }
        }

        return false;
    }
}
