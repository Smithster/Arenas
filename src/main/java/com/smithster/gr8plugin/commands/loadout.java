package com.smithster.gr8plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.smithster.gr8plugin.loadouts.Loadout;
import com.smithster.gr8plugin.loadouts.LoadoutSelect;
import com.smithster.gr8plugin.utils.Profile;

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

        if (args[0].equals("create") && args.length == 2){
            Loadout loadout = new Loadout(args[1], player.getInventory());
            loadout.save();
            return true;
        }

        if( args[0].equals("set") && args.length == 3){
            if (args[1].equals("select")){
                Loadout loadout = Loadout.loadouts.get(args[2]);

                if (loadout == null){
                    player.sendMessage("No loadout exists with that name.");
                    return true;
                }
                
                Profile profile = Profile.profiles.get(player.getUniqueId());
                profile.settingSelect(true);
                profile.setSelect(new LoadoutSelect(loadout));
                return true;
            }
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
