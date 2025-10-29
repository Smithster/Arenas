package uk.smithster.arenas.commands;

import static uk.smithster.arenas.arena.Arena.arenas;
import static uk.smithster.arenas.utils.Plot.plots;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

import uk.smithster.arenas.arena.Arena;
import uk.smithster.arenas.arena.Flag;
import uk.smithster.arenas.gamemodes.Gamemode;
import uk.smithster.arenas.team.Spawn;
import uk.smithster.arenas.team.Team;
import uk.smithster.arenas.utils.Profile;

public class arena implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            return false;
        }

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (args[0].equals("list")) {
            for (String name : arenas.keySet()) {
                player.sendMessage(name);
            }
            return true;
        }

        if (args[0].equals("create")) {
            if (args.length <= 2) {
                return false;
            }

            String plotName = args[1];
            String name = args[2];

            if (arenas.containsKey(name)) {
                sender.sendMessage(String.format("There is already a arena called %s", name));
                return true;
            }

            if (plots.containsKey(plotName)) {
                Arena arena = new Arena(plotName, name);
                arena.save();
                sender.sendMessage(String.format("You have successfully created a arena called %s", name));
            } else {
                sender.sendMessage("You must first create a plot with the same name you intend to use for the arena");
            }

            return true;
        }

        if (args[0].equals("remove")) {
            if (args.length >= 2) {
                if (arenas.containsKey(args[1])) {
                    Arena.remove(arenas.get(args[1]));
                    return true;
                } else {
                    player.sendMessage(String.format("No lobby by the name %s", args[1]));
                    return true;
                }
            }
        }

        if (Arena.arenas.containsKey(args[0])) {
            Arena arena = Arena.arenas.get(args[0]);

            if (args.length == 1) {
                return false;
            }

            if (args[1].equals("set")) {
                if (args.length == 2) {
                    return false;
                }

                if (args[2].equals("team")) {
                    if (args.length < 5) {
                        return false;
                    }

                    String name = args[3];

                    Spawn spawn = Spawn.spawns.get(args[4]);

                    if (spawn == null) {
                        player.sendMessage(String.format("There is no spawn with the name %s", args[4]));
                        return true;
                    }

                    String colour = null;
                    if (args.length == 6) {
                        colour = args[5];
                    }

                    if (Team.teams.containsKey(name)) {
                        player.sendMessage("A team already exists with this name");
                        return true;
                    }

                    Team team = colour == null ? new Team(name, spawn) : new Team(name, spawn, colour);
                    if (!arena.hasGamemode()) {
                        player.sendMessage("You must set a gamemode on the arena before allocating teams");
                        return true;
                    }

                    team.save();
                    arena.addTeam(team);
                    arena.save();
                    return true;
                }

                if (args[2].equals("color")) {
                    if (args.length < 5) {
                        return false;
                    }

                    String teamName = args[3];

                    String colour = args[4];
                    Team team = arena.getTeam(teamName);
                    if (team != null) {
                        String lastName = team.getColoredName();
                        team.setChatColor(colour);
                        arena.getGamemode().resetScoreName(team, lastName);
                        team.save();
                        return true;
                    }
                }

                if (args[2].equals("gamemode")) {
                    if (args.length < 4) {
                        return false;
                    }
                    Gamemode gamemode = Gamemode.gamemodes.get(args[3]);

                    if (gamemode == null) {
                        player.sendMessage("No gamemode exists of that type");
                        return true;
                    }
                    arena.setGamemode(gamemode);
                    arena.save();
                    return true;
                }

                if (args[2].equals("flag")) {
                    if (args.length < 4) {
                        return false;
                    }

                    Team team = Team.teams.get(args[3]);

                    if (team == null) {
                        player.sendMessage("No team exists with this name");
                        return true;
                    }

                    Profile profile = Profile.profiles.get(player.getUniqueId());

                    profile.settingFlag(true);
                    Flag flag = new Flag(team);
                    profile.setFlag(flag);
                    return true;
                }
            }
        }

        return false;

    }

}
