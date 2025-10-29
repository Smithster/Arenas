package uk.smithster.arenas;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import uk.smithster.arenas.arena.Arena;
import uk.smithster.arenas.commands.*;
import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.handlers.*;
import uk.smithster.arenas.utils.Profile;

/*
 * gr8plugin java plugin
 */
public class Plugin extends JavaPlugin {
  public static final Logger LOGGER = Logger.getLogger("gr8plugin");

  // public static MongoCollection<Document> playersCollection =
  // database.getCollection("playerStates");

  public static HashMap<String, Arena> arenas;
  // public static HashMap<String, LobbyJoin> lobbyJoins;
  // public static HashMap<UUID, Profile> profiles;
  // public static HashMap<String, Player> players;
  // public static HashMap<String, Spawn> spawns;

  public static Server server;

  public void onEnable() {
    server = getServer();
    Profile.setPlugin(this);
    Data.init();

    this.getCommand("arena").setExecutor(new arena());
    this.getCommand("plot").setExecutor(new plot());
    this.getCommand("permission").setExecutor(new permission());
    this.getCommand("lobby").setExecutor(new lobby());
    this.getCommand("spawn").setExecutor(new spawn());
    this.getCommand("loadout").setExecutor(new loadout());

    this.getServer().getPluginManager().registerEvents(new plotBreakProtection(), this);
    this.getServer().getPluginManager().registerEvents(new playerLogin(), this);
    this.getServer().getPluginManager().registerEvents(new playerLeave(), this);
    this.getServer().getPluginManager().registerEvents(new playerKill(this), this);
    this.getServer().getPluginManager().registerEvents(new setLobbyTools(), this);
    this.getServer().getPluginManager().registerEvents(new useLobbyTools(), this);
    this.getServer().getPluginManager().registerEvents(new setLoadoutTools(), this);
    this.getServer().getPluginManager().registerEvents(new useLoadoutTools(), this);

  }

  public void onDisable() {
    LOGGER.info("gr8plugin disabled");
  }

}
