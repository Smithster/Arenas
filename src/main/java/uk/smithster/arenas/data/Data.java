package uk.smithster.arenas.data;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import static uk.smithster.arenas.Plugin.LOGGER;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Function;

import uk.smithster.arenas.arena.Arena;
import uk.smithster.arenas.data.dataSchemas.*;
import uk.smithster.arenas.data.dataSchemas.LoadoutSchema.ItemStackData;
import uk.smithster.arenas.gamemodes.Gamemode;
import uk.smithster.arenas.team.Team;


public class Data {
  private static Gson gson = new Gson();

  // Defining data files for in app memory
  public static HashMap<String, JsonObject> dataStores = new HashMap<String, JsonObject>();
  public static HashMap<String, Function<JsonObject, Void>> dataLoaders = new HashMap<String, Function<JsonObject, Void>>(); 
  public static HashMap<String, SchemaMetaData> dataStoreTypes = new HashMap<String, SchemaMetaData>();
  
  public static void initStorableTypes() {
    dataStoreTypes.put("plot", PlotSchema.metaData);
    dataStoreTypes.put("profile", ProfileSchema.metaData);
    dataStoreTypes.put("spawn", SpawnSchema.metaData);
    dataStoreTypes.put("team", TeamSchema.metaData);
    dataStoreTypes.put("arena", ArenaSchema.metaData);
    dataStoreTypes.put("lobbie", LobbySchema.metaData);
    dataStoreTypes.put("lobbyJoin", LobbyJoinSchema.metaData);
    dataStoreTypes.put("lobbyLeave", LobbyLeaveSchema.metaData);
    dataStoreTypes.put("lobbyVote", LobbyVoteSchema.metaData);
    dataStoreTypes.put("lobbyStart", LobbyStartSchema.metaData);
    dataStoreTypes.put("loadout", LoadoutSchema.metaData);
    dataStoreTypes.put("loadoutSelect", LoadoutSelectSchema.metaData);

    dataLoaders.put("plot", Arena::load);
  }



  public static Map<ChatColor, String> colors = new HashMap<ChatColor, String>();

  // Collection loading
  public static void init() {

    initStorableTypes();

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
    
    for (String key : dataStoreTypes.keySet()) {
      SchemaMetaData typeSchema = dataStoreTypes.get(key);
      try {
        dataStores.put(key, registerJsonData(typeSchema));
      } catch (IOException e) {
        LOGGER.warning("Something went from trying to register Json data for " + key);
      }
      loadDataFromStore(key, dataStores.get(key));
    }

  }

  public static UUID save(DataSchema data) {
    try {
      JsonObject dataObject = dataStores.get(data.getSchemaType());
      UUID id = data.getId() != null ? data.getId() : UUID.randomUUID(); 
      
      if (dataObject.get(id.toString()) != null && dataObject.get(id.toString()).isJsonObject()) {
        dataObject.remove(id.toString());
      }

      dataObject.add(id.toString(), gson.toJsonTree(data));
      LOGGER.info(dataObject.toString());
      Writer fileWriter = new FileWriter(data.getPath());
      gson.toJson(dataObject, fileWriter);
      fileWriter.close();
      return id;
      
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    
  }

  public static Void loadDataFromStore(String type, JsonObject dataStoreObject) {
    LOGGER.info("LOADING DATA OF TYPE: " + type);
    try {
      for (Entry<String, JsonElement> dataEntry : dataStoreObject.entrySet()) {
        String id = dataEntry.getKey();
        LOGGER.info("LOADING DATA WITH ID: " + id);
        dataEntry.getValue().getAsJsonObject().addProperty("id", id);
        dataStoreTypes.get(type).load(dataEntry.getValue().getAsJsonObject());
        LOGGER.info(dataEntry.toString());
      }

    } catch (Exception e){
      LOGGER.warning("Cannot load from data store");
    }
    
    return null;
  }


  public static void remove(DataSchema data) {
    FileWriter fileWriter;
    try {
      JsonObject dataObject = data.getJsonData();

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
    FileReader fileReader = new FileReader(saveFile);
    if (fileCreated || fileReader.read() == -1) {
      FileWriter fileWriter = new FileWriter(saveFile);
      fileWriter.write("{}");
      fileWriter.close();
    }
    fileReader.close();
    FileReader reader = new FileReader(new File(schemaMetaData.getPath()));
    
    JsonObject dataFromFile = gson.fromJson(new JsonReader(reader), JsonObject.class);
    if (dataFromFile == null) {
      LOGGER.warning("No data in File " + schemaMetaData.schemaType);
    }
    return dataFromFile;
  }

  public static String getInventoryArray(ItemStackData itemStack) {
    return gson.toJson(itemStack);
  }
}
