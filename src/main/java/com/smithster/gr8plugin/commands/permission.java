package com.smithster.gr8plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.utils.Profile;

import static com.smithster.gr8plugin.utils.Profile.profiles;

import java.util.UUID;

public class permission implements CommandExecutor {

    /*
     * Layout for permission command
     * /permission [name] [set/unset] [permission.name]
     * 
     */

    public boolean onCommand(CommandSender sender, Command command, String label,
            String[] args) {

        if (sender.hasPermission("gr8plugin.admin.permission") && args.length == 3) {
            String playerName = args[0];
            String func = args[1];
            String permission = args[2];

            UUID pid = Bukkit.getPlayer(playerName).getUniqueId();
            Profile profile = profiles.get(pid);
            if (func.equals("set")) {
                profile.addPermission(permission);
            } else if (func.equals("unset")) {
                profile.removePermission(permission);
            }
            profile.save();
            return true;
        }

        return false;
    }
}
