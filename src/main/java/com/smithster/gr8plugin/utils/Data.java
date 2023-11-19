package com.smithster.gr8plugin.utils;

import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import static com.mongodb.client.model.Filters.eq;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.arena.Arena;
import com.smithster.gr8plugin.gamemodes.Gamemode;
import com.smithster.gr8plugin.loadouts.Loadout;
import com.smithster.gr8plugin.loadouts.LoadoutSelect;
import com.smithster.gr8plugin.lobby.Lobby;
import com.smithster.gr8plugin.lobby.LobbyJoin;
import com.smithster.gr8plugin.lobby.LobbyLeave;
import com.smithster.gr8plugin.lobby.LobbyStart;
import com.smithster.gr8plugin.lobby.LobbyVote;
import com.smithster.gr8plugin.team.Spawn;
import com.smithster.gr8plugin.team.Team;


public class Data {

  // Setting up mongoDB connection (basedon localhost and default port)
  public static ConnectionString connectionString = new ConnectionString("mongodb://localhost");
  public static MongoClient client = MongoClients.create(connectionString);
  public static MongoDatabase database = client.getDatabase("gr8plugin");

  // Defining database collections
  public static MongoCollection<Document> plots = database.getCollection("plots");
  public static MongoCollection<Document> profiles = database.getCollection("profiles");
  public static MongoCollection<Document> spawns = database.getCollection("spawns");
  public static MongoCollection<Document> teams = database.getCollection("teams");
  public static MongoCollection<Document> arenas = database.getCollection("arenas");
  public static MongoCollection<Document> lobbies = database.getCollection("lobbies");
  public static MongoCollection<Document> lobbyJoins = database.getCollection("lobbyJoins");
  public static MongoCollection<Document> lobbyLeaves = database.getCollection("lobbyLeaves");
  public static MongoCollection<Document> lobbyVotes = database.getCollection("lobbyVotes");
  public static MongoCollection<Document> lobbyStarts = database.getCollection("lobbyStarts");
  public static MongoCollection<Document> loadouts = database.getCollection("loadouts");
  public static MongoCollection<Document> loadoutSelects = database.getCollection("loadoutSelects");

  public static Map<ChatColor, String> colors = new HashMap<ChatColor, String>();

  // Collection loading
  public static void init() {

    colors.put(ChatColor.DARK_AQUA, "darkaqua");
    colors.put(ChatColor.DARK_RED, "darkred");
    colors.put(ChatColor.DARK_BLUE, "darkblue");
    colors.put(ChatColor.DARK_GREEN, "darkgreen");
    colors.put(ChatColor.DARK_GRAY, "darkgray");
    colors.put(ChatColor.DARK_PURPLE, "darkpurple");
    colors.put(ChatColor.AQUA, "aqua");
    colors.put(ChatColor.BLACK, "black");
    colors.put(ChatColor.BLUE, "blue");
    colors.put(ChatColor.GOLD, "gold");
    colors.put(ChatColor.GRAY, "gray");
    colors.put(ChatColor.GREEN, "green");
    colors.put(ChatColor.LIGHT_PURPLE, "lightpurple");
    colors.put(ChatColor.YELLOW, "yellow");
    colors.put(ChatColor.WHITE, "white");
    colors.put(ChatColor.RED, "red");

    Gamemode.init();

    for (Document document : plots.find()) {
      Plot.load(document);
    }

    for (Document document : profiles.find()) {
      Profile.load(document);
    }

    for (Document document : lobbies.find()) {
      Lobby.load(document);
    }

    for (Document document : spawns.find()) {
      Spawn.load(document);
    }

    for (Document document : teams.find()) {
      Team.load(document);
    }

    for (Document document : arenas.find()) {
      Arena.load(document);
    }

    for (Document document : lobbyJoins.find()) {
      LobbyJoin.load(document);
    }

    for (Document document : lobbyLeaves.find()) {
      LobbyLeave.load(document);
    }

    for (Document document : lobbyVotes.find()) {
      LobbyVote.load(document);
    }

    for (Document document : lobbyStarts.find()) {
      LobbyStart.load(document);
    }

    for (Document document : loadouts.find()) {
      Loadout.load(document);
    }
    
    for (Document document : loadoutSelects.find()) {
      LoadoutSelect.load(document);
    }

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

  public static ArrayList<Integer> getXYZRotArrayList(Location loc) {
    ArrayList<Integer> xyz = new ArrayList<Integer>();
    xyz.add(loc.getBlockX());
    xyz.add(loc.getBlockY());
    xyz.add(loc.getBlockZ());
    xyz.add(((Float) loc.getYaw()).intValue());
    xyz.add(((Float) loc.getPitch()).intValue());
    return xyz;
  }

  public static Location getLocation(World world, ArrayList<Integer> xyz) {
    return new Location(world, xyz.get(0), xyz.get(1), xyz.get(2));
  }

  public static Location getRotLocation(World world, ArrayList<Integer> xyz) {
    return new Location(world, xyz.get(0), xyz.get(1), xyz.get(2), xyz.get(3), xyz.get(4));
  }

  public static ArrayList<String> getTeamNames(Set<Team> teams) {
    ArrayList<String> names = new ArrayList<String>();
    for (Team team : teams) {
      names.add(team.getName());
    }
    return names;
  }

  public static String getColorString(ChatColor chatColor){    
    return colors.get(chatColor);
  }

  public static ChatColor getChatColor(String color){
    for (Entry<ChatColor, String> entry : colors.entrySet()){
      if (color.equals(entry.getValue())){
        return entry.getKey();
      }
    }
    return null;
  }
}
