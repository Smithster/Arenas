package uk.smithster.arenas.data;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import uk.smithster.arenas.arena.Arena;
import uk.smithster.arenas.data.dataSchemas.*;
import uk.smithster.arenas.data.dataSchemas.LoadoutSchema.ItemStackData;
import uk.smithster.arenas.gamemodes.Gamemode;
import uk.smithster.arenas.loadouts.Loadout;
import uk.smithster.arenas.loadouts.LoadoutSelect;
import uk.smithster.arenas.lobby.Lobby;
import uk.smithster.arenas.lobby.LobbyJoin;
import uk.smithster.arenas.lobby.LobbyLeave;
import uk.smithster.arenas.lobby.LobbyStart;
import uk.smithster.arenas.lobby.LobbyVote;
import uk.smithster.arenas.team.Spawn;
import uk.smithster.arenas.team.Team;
import uk.smithster.arenas.utils.Plot;
import uk.smithster.arenas.utils.Profile;


public class Data {
  private static Gson gson = new Gson();

  // Defining data files for in app memory
  
  public static JsonObject plots;
  public static JsonObject profiles;
  public static JsonObject spawns;
  public static JsonObject teams;
  public static JsonObject arenas;
  public static JsonObject lobbies;
  public static JsonObject lobbyJoins;
  public static JsonObject lobbyLeaves;
  public static JsonObject lobbyVotes;
  public static JsonObject lobbyStarts;
  public static JsonObject loadouts;
  public static JsonObject loadoutSelects;
  
  public static void initJsonFiles() {
    try {
      plots = registerJsonData(PlotSchema.metaData);
      profiles = registerJsonData(ProfileSchema.metaData);
      spawns = registerJsonData(SpawnSchema.metaData);
      teams = registerJsonData(TeamSchema.metaData);
      arenas = registerJsonData(ArenaSchema.metaData);
      lobbies = registerJsonData(LobbySchema.metaData);
      lobbyJoins = registerJsonData(LobbyJoinSchema.metaData);
      lobbyLeaves = registerJsonData(LobbyLeaveSchema.metaData);
      lobbyVotes = registerJsonData(LobbyVoteSchema.metaData);
      lobbyStarts = registerJsonData(LobbyStartSchema.metaData);
      loadouts = registerJsonData(LoadoutSchema.metaData);
      loadoutSelects = registerJsonData(LoadoutSelectSchema.metaData);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Map<ChatColor, String> colors = new HashMap<ChatColor, String>();

  // Collection loading
  public static void init() {

    initJsonFiles();

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
    
    for (String key : plots.keySet()) {
      Plot.load((JsonObject) plots.get(key));
    }

    for (String key : profiles.keySet()) {
      Profile.load((JsonObject) profiles.get(key));
    }

    for (String key : lobbies.keySet()) {
      Lobby.load((JsonObject) lobbies.get(key));
    }

    for (String key : spawns.keySet()) {
      Spawn.load((JsonObject) spawns.get(key));
    }

    for (String key : teams.keySet()) {
      Team.load((JsonObject) teams.get(key));
    }

    for (String key : arenas.keySet()) {
      Arena.load((JsonObject) arenas.get(key));
    }

    for (String key : lobbyJoins.keySet()) {
      LobbyJoin.load((JsonObject) lobbyJoins.get(key));
    }

    for (String key : lobbyLeaves.keySet()) {
      LobbyLeave.load((JsonObject) lobbyLeaves.get(key));
    }

    for (String key : lobbyVotes.keySet()) {
      LobbyVote.load((JsonObject) lobbyVotes.get(key));
    }

    for (String key : lobbyStarts.keySet()) {
      LobbyStart.load((JsonObject) lobbyStarts.get(key));
    }

    for (String key : loadouts.keySet()) {
      Loadout.load((JsonObject) loadouts.get(key));
    }
    
    for (String key : loadoutSelects.keySet()) {
      LoadoutSelect.load((JsonObject) loadoutSelects.get(key));
    }

  }

  public static UUID save(DataSchema data) {
    FileWriter fileWriter;
    try {
      JsonObject dataObject = data.getJsonObject();
      dataObject.add(null, dataObject);
      UUID id = data.getId() != null ? data.getId() : UUID.randomUUID(); 
      fileWriter = new FileWriter(data.getPath());
      gson.toJson(dataObject, fileWriter);
      return id;

    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void remove(DataSchema data) {
    FileWriter fileWriter;
    try {
      JsonObject dataObject = data.getJsonObject();

      UUID id = data.getId(); 
      dataObject.remove(id.toString());

      fileWriter = new FileWriter(data.getPath());
      gson.toJson(dataObject, fileWriter);

    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
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

  public static ArrayList<Integer> getIntArrayList(JsonArray jsonArray) {
    ArrayList<Integer> intArrayList = new ArrayList<Integer>();
    for (JsonElement jsonElement : jsonArray) {
      intArrayList.add( jsonElement.getAsInt() );
    };
    return intArrayList;
  }

  public static Location getLocation(World world, ArrayList<Integer> xyz) {
    return new Location(world, xyz.get(0), xyz.get(1), xyz.get(2));
  }

  public static Location getRotLocation(World world, ArrayList<Integer> xyz) {
    return new Location(world, xyz.get(0), xyz.get(1), xyz.get(2), xyz.get(3), xyz.get(4));
  }

  public static ArrayList<String> getStringArrayList(JsonArray jsonArray) {
    ArrayList<String> stringArrayList = new ArrayList<String>();
    for (JsonElement jsonElement : jsonArray) {
      stringArrayList.add( jsonElement.getAsString() );
    };
    return stringArrayList;
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

  public static JsonObject registerJsonData(SchemaMetaData schemaMetaData) throws FileNotFoundException, IOException {
    new File("./saved_data").mkdir();
    File saveFile = new File(schemaMetaData.getPath());
    boolean fileCreated = saveFile.createNewFile();

    if (fileCreated) {
      FileWriter fileWriter = new FileWriter(saveFile);
      fileWriter.write("[]");
      fileWriter.close();
    }

    FileReader reader = new FileReader(saveFile);
    
    return gson.fromJson(new JsonReader(reader), JsonObject.class);
  }

  public static String getInventoryArray(ItemStackData itemStack) {
    return gson.toJson(itemStack);
  }
}
