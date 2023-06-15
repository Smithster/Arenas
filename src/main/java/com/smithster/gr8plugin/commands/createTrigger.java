package com.smithster.gr8plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.smithster.gr8plugin.classes.Trigger;
import com.smithster.gr8plugin.classes.playerState;

import static com.smithster.gr8plugin.Plugin.states;
import static com.smithster.gr8plugin.Plugin.triggers;

public class createTrigger implements CommandExecutor {

    public boolean onCommand(CommandSender player, Command command, String label, String[] args) {

        if (!(player instanceof Player)) {
            player.sendMessage("You must be a player to use this command");
            return false;
        }

        if (!(args.length == 1)) {
            return false;
        }

        if (triggers.containsKey(args[0])) {
            player.sendMessage("A trigger already exists with this name");
            return true;
        }

        player = (Player) player;

        playerState state = states.get(player.getName());

        state.isSettingTrigger = true;

        state.setTriggerName(args[0]);

        triggers.put(args[0], new Trigger());

        states.put(player.getName(), state);

        String message = "The next redstone object you punch will now become a trigger with the name %s";

        player.sendMessage(String.format(message, args[0]));

        return true;
    }
}
