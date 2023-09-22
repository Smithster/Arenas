package com.smithster.gr8plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.smithster.gr8plugin.classes.Trigger;
import com.smithster.gr8plugin.utils.Profile;

import static com.smithster.gr8plugin.Plugin.profiles;
import static com.smithster.gr8plugin.Plugin.triggers;

public class createTrigger implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }

        if (!(args.length == 1)) {
            return false;
        }

        if (triggers.containsKey(args[0])) {
            sender.sendMessage("A trigger already exists with this name");
            return true;
        }

        Player player = (Player) sender;

        Profile state = profiles.get(player.getUniqueId());

        state.isSettingTrigger = true;

        state.setTriggerName(args[0]);

        triggers.put(args[0], new Trigger());

        profiles.put(player.getUniqueId(), state);

        String message = "The next redstone object you punch will now become a trigger with the name %s";

        player.sendMessage(String.format(message, args[0]));

        return true;
    }
}
