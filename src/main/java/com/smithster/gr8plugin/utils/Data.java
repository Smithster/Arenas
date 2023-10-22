package com.smithster.gr8plugin.utils;

import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.bukkit.Location;
import org.bukkit.World;

import static com.mongodb.client.model.Filters.eq;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.classes.Lobby;
import com.smithster.gr8plugin.classes.LobbyJoin;
import com.smithster.gr8plugin.classes.LobbyLeave;
import com.smithster.gr8plugin.classes.Plot;

public class Data {

  // Setting up mongoDB connection (basedon localhost and default port)
  public static ConnectionString connectionString = new ConnectionString("mongodb://localhost");
  public static MongoClient client = MongoClients.create(connectionString);
  public static MongoDatabase database = client.getDatabase("gr8plugin");

  // Defining database collections
  public static MongoCollection<Document> plots = database.getCollection("plots");
  public static MongoCollection<Document> profiles = database.getCollection("profiles");
  public static MongoCollection<Document> spawns = database.getCollection("spawns");
  public static MongoCollection<Document> arenas = database.getCollection("arenas");
  public static MongoCollection<Document> lobbies = database.getCollection("lobbies");
  public static MongoCollection<Document> lobbyJoins = database.getCollection("lobbyJoins");
  public static MongoCollection<Document> lobbyLeaves = database.getCollection("lobbyLeaves");

  // Collection loading
  public static void init() {

    for (Document document : plots.find()) {
      Plot.load(document);
    }

    for (Document document : profiles.find()) {
      Profile.load(document);
    }

    for (Document document : lobbies.find()) {
      Lobby.load(document);
    }

    for (Document document : lobbyJoins.find()) {
      LobbyJoin.load(document);
    }

    for (Document document : lobbyLeaves.find()) {
      LobbyLeave.load(document);
    }

    // for (Document document : spawns.find()) {
    // Spawns
    // }
  }

  public static ObjectId save(String collection, Document document) {
    UpdateOptions options = new UpdateOptions().upsert(true);
    Document update = new Document();
    if (document.get("_id") != null) {
      update.put("$set", document);
      database.getCollection(collection).updateOne(eq("_id", document.get("_id")), update, options);
      return null;
    } else {
      document.remove("_id");
      InsertOneResult inserted = database.getCollection(collection).insertOne(document);
      BsonObjectId bsonId = (BsonObjectId) inserted.getInsertedId();
      return bsonId.getValue();
    }
  }

  public static void remove(String collection, ObjectId id) {
    database.getCollection(collection).deleteOne(new Document("_id", id));
  }

  public static ArrayList<Integer> getXYZArrayList(Location loc) {
    ArrayList<Integer> xyz = new ArrayList<Integer>();
    xyz.add(loc.getBlockX());
    xyz.add(loc.getBlockY());
    xyz.add(loc.getBlockZ());
    return xyz;
  }

  public static Location getLocation(World world, ArrayList<Integer> xyz) {
    return new Location(world, xyz.get(0), xyz.get(1), xyz.get(2));
  }
}
