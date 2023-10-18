package com.smithster.gr8plugin.utils;

import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.smithster.gr8plugin.classes.Plot;

public class Data {

  // Setting up mongoDB connection (basedon localhost and default port)
  public static ConnectionString connectionString = new ConnectionString("mongodb://localhost");
  public static MongoClient client = MongoClients.create(connectionString);
  public static MongoDatabase database = client.getDatabase("gr8plugin");

  // Defining database collections
  public static MongoCollection<Document> plots = database.getCollection("plots");
  public static MongoCollection<Document> spawns = database.getCollection("spawns");
  public static MongoCollection<Document> arenas = database.getCollection("arenas");
  public static MongoCollection<Document> lobbies = database.getCollection("lobbies");

  // Collection loading
  public static void init() {

    for (Document document : plots.find()) {
      Plot.load(document);
    }

    // for (Document document : spawns.find()) {
    // Spawns
    // }
  }

  public static void save(String collection, Document document) {
    UpdateOptions options = new UpdateOptions().upsert(true);
    Document update = new Document();
    update.put("$set", document);
    database.getCollection(collection).updateOne(eq("name", document.get("name")), update, options);
  }
}
