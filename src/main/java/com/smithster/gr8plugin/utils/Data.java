package com.smithster.gr8plugin.utils;

import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.bukkit.Location;
import org.bukkit.World;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
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

  // Collection loading
  public static void init() {

    for (Document document : plots.find()) {
      Plot.load(document);
    }

    for (Document document : profiles.find()) {
      Profile.load(document);
    }

    // for (Document document : spawns.find()) {
    // Spawns
    // }
  }

  public static ObjectId save(String collection, Document document) {
    UpdateOptions options = new UpdateOptions().upsert(true);
    Document update = new Document();
    if (document.get("_id") == null) {
      update.put("$set", document);
      database.getCollection(collection).updateOne(eq("_id", document.get("_id")), update, options);
      return null;
    } else {
      InsertOneResult inserted = database.getCollection(collection).insertOne(document);
      BsonObjectId bsonId = (BsonObjectId) inserted.getInsertedId();
      return bsonId.getValue();
    }

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
