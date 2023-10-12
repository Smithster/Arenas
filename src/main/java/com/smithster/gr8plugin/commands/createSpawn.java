// package com.smithster.gr8plugin.commands;

// import org.bson.Document;
// import org.bukkit.command.Command;
// import org.bukkit.command.CommandExecutor;
// import org.bukkit.command.CommandSender;
// import org.bukkit.entity.Player;

// import static com.smithster.gr8plugin.Plugin.spawnsCollection;
// import static com.mongodb.client.model.Filters.eq;

// public class createSpawn implements CommandExecutor {

// public boolean onCommand(CommandSender sender, Command command, String label,
// String[] args) {

// if (!(sender instanceof Player)) {
// sender.sendMessage("Must be a player to use this command");
// return false;
// }

// Player player = (Player) sender;

// Integer[] loc = { player.getLocation().getBlockX(),
// player.getLocation().getBlockY(),
// player.getLocation().getBlockZ() };

// if (spawnsCollection.find(eq("name", args[0])).first() == null) {
// player.sendMessage("A spawn already exists with this name");
// return false;
// }

// Document spawn = new Document();
// spawn.put("name", args[0]);
// spawn.put("world", player.getWorld());
// spawn.put("type", "radius");
// spawn.put("radius", 5);
// spawn.put("loc1", loc);
// spawn.put("team", args[1]);

// spawnsCollection.insertOne(spawn);

// return true;
// }
// }
