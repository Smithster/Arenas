package uk.smithster.arenas.loadouts;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import uk.smithster.arenas.Plugin;
import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.data.Storable;
import uk.smithster.arenas.data.dataSchemas.LoadoutSelectSchema;
import uk.smithster.arenas.data.dataSchemas.SchemaMetaData;

public class LoadoutSelect implements Storable {
    
    public static HashMap<Location, LoadoutSelect> LoadoutSelects = new HashMap<Location, LoadoutSelect>();

    private UUID id;
    private Location loc;
    private Loadout loadout;

    public static JsonObject jsonData = new JsonObject();
    
    public SchemaMetaData getStoreMetaData() {
        return LoadoutSelectSchema.metaData;
    }

    public LoadoutSelect(Loadout loadout){
        this.loadout = loadout;
    }

    public LoadoutSelect(Location loc, Loadout loadout){
        this.loc = loc;
        this.loadout = loadout;
        LoadoutSelects.put(loc, this);
    }

    public void setLocation(Location loc){
        this.loc = loc;
        LoadoutSelects.put(loc, this);
    }

    public LoadoutSelect getLoadoutSelect(Location loc){
        return LoadoutSelects.get(loc);
    }

    public void use(Player player){
        this.loadout.giveItems(player);
    }

    public Location getLoc() {
        return this.loc;
    }

    public Loadout getLoadout() {
        return this.loadout;
    }

    public UUID getId() {
        return this.id;
    }

    public void save(){
        LoadoutSelectSchema loadoutSelect = new LoadoutSelectSchema(this);

        UUID insertedId = Data.save(loadoutSelect);
        this.id = insertedId == null ? this.id : insertedId;
    }

    public static Void load(JsonObject document){
        Location loc = Data.getLocation(Plugin.server.getWorld(document.get("world").getAsString()), Data.getIntArrayList((JsonArray) document.get("xyz")));
        Loadout loadout = Loadout.loadouts.get(document.get("loadout").getAsString());
        new LoadoutSelect(loc, loadout);
        return null;
    }

}
