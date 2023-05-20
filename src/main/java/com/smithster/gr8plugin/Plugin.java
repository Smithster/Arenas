package com.smithster.gr8plugin;

import com.smithster.gr8plugin.commands.*;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.ConnectionString;
import org.bson.Document;

/*
 * gr8plugin java plugin
 */
public class Plugin extends JavaPlugin {
  private static final Logger LOGGER = Logger.getLogger("gr8plugin");

  public void onEnable() {
    LOGGER.info("gr8plugin enabled");
    ConnectionString connectionString = new ConnectionString("mongodb://localhost");
    MongoClient client = MongoClients.create();
    MongoDatabase database;
    MongoCollection<Document> collection;
    this.getCommand("arena").setExecutor(new arena());
  }

  public void onDisable() {
    LOGGER.info("gr8plugin disabled");
  }
}
