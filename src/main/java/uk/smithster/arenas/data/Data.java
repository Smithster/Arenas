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
  
  public static JsonArray plots;
  public static JsonArray profiles;
  public static JsonArray spawns;
  public static JsonArray teams;
  public static JsonArray arenas;
  public static JsonArray lobbies;
  public static JsonArray lobbyJoins;
  public static JsonArray lobbyLeaves;
  public static JsonArray lobbyVotes;
  public static JsonArray lobbyStarts;
  public static JsonArray loadouts;
  public static JsonArray loadoutSelects;
  
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
    
    for (JsonElement document : plots) {
      Plot.load((JsonObject) document);
    }

    for (JsonElement document : profiles) {
      Profile.load((JsonObject) document);
    }

    for (JsonElement document : lobbies) {
      Lobby.load((JsonObject) document);
    }

    for (JsonElement document : spawns) {
      Spawn.load((JsonObject) document);
    }

    for (JsonElement document : teams) {
      Team.load((JsonObject) document);
    }

    for (JsonElement document : arenas) {
      Arena.load((JsonObject) document);
    }

    for (JsonElement document : lobbyJoins) {
      LobbyJoin.load((JsonObject) document);
    }

    for (JsonElement document : lobbyLeaves) {
      LobbyLeave.load((JsonObject) document);
    }

    for (JsonElement document : lobbyVotes) {
      LobbyVote.load((JsonObject) document);
    }

    for (JsonElement document : lobbyStarts) {
      LobbyStart.load((JsonObject) document);
    }

    for (JsonElement document : loadouts) {
      Loadout.load((JsonObject) document);
    }
    
    for (JsonElement document : loadoutSelects) {
      LoadoutSelect.load((JsonObject) document);
    }

  }

  public static Integer save(DataSchema data) {
    FileWriter fileWriter;
    try {
      JsonArray dataArray = data.getJsonArray();

      Integer id = data.getId() != null ? data.getId() : dataArray.size(); 
      fileWriter = new FileWriter(data.getPath());
      gson.toJson(dataArray, fileWriter);
      return id;

    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void remove(DataSchema data) {
    FileWriter fileWriter;
    try {
      JsonArray dataArray = data.getJsonArray();

      Integer id = data.getId(); 
      dataArray.remove(id);

      fileWriter = new FileWriter(data.getPath());
      gson.toJson(dataArray, fileWriter);

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

  public static JsonArray registerJsonData(SchemaMetaData schemaMetaData) throws FileNotFoundException, IOException {
    new File("./saved_data").mkdir();
    File saveFile = new File(schemaMetaData.getPath());
    boolean fileCreated = saveFile.createNewFile();

    if (fileCreated) {
      FileWriter fileWriter = new FileWriter(saveFile);
      fileWriter.write("[]");
      fileWriter.close();
    }

    FileReader reader = new FileReader(saveFile);
    
    return gson.fromJson(new JsonReader(reader), JsonArray.class);
  }

  public static String getInventoryArray(ItemStackData itemStack) {
    return gson.toJson(itemStack);
  }
}
