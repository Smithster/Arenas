package com.smithster.gr8plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.smithster.gr8plugin.classes.Lobby;
import com.smithster.gr8plugin.classes.LobbyJoin;
import com.smithster.gr8plugin.classes.LobbyLeave;
import com.smithster.gr8plugin.utils.Profile;

import static com.smithster.gr8plugin.classes.Lobby.lobbies;
import static com.smithster.gr8plugin.classes.Plot.plots;
import static com.smithster.gr8plugin.utils.Profile.profiles;

public class lobby implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            return false;
        }

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (args[0].equals("create")) {
            if (args.length <= 2) {
                return false;
            }

            String plotName = args[1];
            String name = args[2];

            if (lobbies.containsKey(name)) {
                sender.sendMessage(String.format("There is already a lobby called %s", name));
                return true;
            }

            if (plots.containsKey(plotName)) {
                Lobby lobby = new Lobby(plotName, name);
                lobby.save();
                sender.sendMessage(String.format("You have successfully created a lobby called %s", name));
            } else {
                sender.sendMessage("You must first create a plot with the same name you intend to use for the lobby");
            }

            return true;
        }

        if (args[0].equals("remove")) {
            if (args.length >= 2) {
                if (lobbies.containsKey(args[1])) {
                    Lobby.remove(lobbies.get(args[1]));
                    return true;
                } else {
                    player.sendMessage(String.format("No lobby by the name %s", args[1]));
                    return true;
                }
            }
        }

        if (lobbies.containsKey(args[0])) {
            Lobby lobby = lobbies.get(args[0]);

            if (args[1].equals("join")) {

                lobby.enter(player);
                return true;
            }

            if (args[1].equals("set")) {

                if (args[2].equals("join")) {

                    Profile profile = profiles.get(player.getUniqueId());
                    profile.settingJoin(true);
                    LobbyJoin join = new LobbyJoin(lobby);
                    profile.setJoin(join);
                    sender.sendMessage("To finish setting the lobby join, punch a block to be the trigger.");

                    return true;
                }

                if (args[2].equals("leave")) {

                    Profile profile = profiles.get(player.getUniqueId());
                    profile.settingLeave(true);
                    LobbyLeave leave = new LobbyLeave(lobby);
                    profile.setLeave(leave);
                    sender.sendMessage("To finish setting the lobby leave, punch a block to be the trigger.");

                    return true;
                }
            }
        }

        return false;
    }
}
