package com.smithster.gr8plugin;

import com.smithster.gr8plugin.classes.*;
import com.smithster.gr8plugin.commands.*;
import com.smithster.gr8plugin.handlers.*;
import com.smithster.gr8plugin.utils.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/*
 * gr8plugin java plugin
 */
public class Plugin extends JavaPlugin {
  private static final Logger LOGGER = Logger.getLogger("gr8plugin");

  // Setting up mongoDB connection (basedon localhost and default port)
  public static ConnectionString connectionString = new ConnectionString("mongodb://localhost");
  public static MongoClient client = MongoClients.create(connectionString);
  public static MongoDatabase database = client.getDatabase("gr8plugin");

  // Defining database collections
  public static MongoCollection<Document> plotsCollection = database.getCollection("plots");
  public static MongoCollection<Document> spawnsCollection = database.getCollection("spawns");
  public static MongoCollection<Document> arenasCollection = database.getCollection("arenas");
  // public static MongoCollection<Document> playersCollection =
  // database.getCollection("playerStates");

  public static HashMap<String, Plot> plots;
  public static HashMap<String, Arena> arenas;
  public static HashMap<String, Trigger> triggers;
  public static HashMap<UUID, Profile> profiles;
  // public static HashMap<String, Player> players;
  // public static HashMap<String, Spawn> spawns;

  public void onEnable() {
    initPlots();

    this.getCommand("arena").setExecutor(new arena());
    this.getCommand("plot").setExecutor(new plotcmd());
    this.getCommand("createSpawn").setExecutor(new createSpawn());
    this.getServer().getPluginManager().registerEvents(new plotBreakProtection(this), this);
    this.getServer().getPluginManager().registerEvents(null, null);
  }

  public void onDisable() {
    LOGGER.info("gr8plugin disabled");
  }

  private static void initPlots() {
    for (Document plot : plotsCollection.find()) {
      addPlot(plot);
    }
  }

  public static void addPlot(Document document) {
    Plot plot = new Plot();
    String name = (String) document.get("name");
    plot.setName(name);
    plot.setWorld((String) document.get("world"));
    plot.setPos1((ArrayList<Integer>) document.get("pos1"));
    plot.setPos2((ArrayList<Integer>) document.get("pos2"));

    plots.put(name, plot);
    return;
  }

}
