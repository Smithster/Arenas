package com.smithster.gr8plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static com.smithster.gr8plugin.Plugin.states;

public class createTrigger implements CommandExecutor {

    public boolean onCommand(CommandSender player, Command command, String label, String[] args){
        
        if (!(player instanceof Player)){
            player.sendMessage("You must be a player to use this command");
            return false;
        }

        player = (Player) player;

        states[player.getName()]


        return true;
    }
}
