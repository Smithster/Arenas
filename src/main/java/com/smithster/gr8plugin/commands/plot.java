package com.smithster.gr8plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class plot implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (!(args.length == 2) || (!(args[1] == "1") && !(args[1] == "2"))) {
            player.sendMessage("Invalid position argument, second arg must be either 1 or 2");
            return false;
        }

        return true;
    }

}
