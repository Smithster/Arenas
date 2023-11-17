package com.smithster.gr8plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.smithster.gr8plugin.utils.Loadout;

public class loadout implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("You must be a player to use this command");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0){
            return false;
        }

        if (args[0].equals("set") && args.length == 2){
            Loadout loadout = new Loadout(args[1], player.getInventory());
            loadout.save();
            return true;
        }

        Loadout loadout = Loadout.loadouts.get(args[0]);
        if (loadout == null){
            player.sendMessage("No loadout exists with that name");
            return true;
        }

        loadout.giveItems(player);

        return true;
    }
}
