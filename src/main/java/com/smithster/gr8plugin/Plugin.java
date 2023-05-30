package com.smithster.gr8plugin;

import com.smithster.gr8plugin.commands.*;
import com.smithster.gr8plugin.handlers.plotBreakProtection;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
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
  public static MongoCollection<Document> Plots = database.getCollection("plots");
  public static MongoCollection<Document> Spawns = database.getCollection("spawns");
  public static MongoCollection<Document> Arenas = database.getCollection("arenas");
  public static MongoCollection<Document> Players = database.getCollection("players");

  public void onEnable() {

    this.getCommand("arena").setExecutor(new arena());
    this.getCommand("plot").setExecutor(new plotcmd());
    this.getCommand("createSpawn").setExecutor(new createSpawn());
    this.getServer().getPluginManager().registerEvents(new plotBreakProtection(this), this);
  }

  public void onDisable() {
    LOGGER.info("gr8plugin disabled");
  }
}
