package com.smithster.gr8plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class arena implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage("this is a message");

        if (args.length == 6) {

            sender.sendMessage("this is a better message");

        }

        return true;
    }

}
