package com.smithster.gr8plugin.classes;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.utils.Data;
import com.smithster.gr8plugin.utils.Loadout;

public class LoadoutSelect {
    
    public static HashMap<Location, LoadoutSelect> LoadoutSelects = new HashMap<Location, LoadoutSelect>();

    private Location loc;
    private Loadout loadout;

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

    public void save(){
        Document document = new Document();
        document.put("xyz", Data.getXYZArrayList(loc));
        document.put("world", loc.getWorld().getName());
        document.put("loadout", this.loadout.getName());
        Data.save("loadoutSelects", document);
    }

    public static void load(Document document){
        Location loc = Data.getLocation(Plugin.server.getWorld((String) document.get("world")), (ArrayList<Integer>) document.get("xyz"));
        Loadout loadout = Loadout.loadouts.get((String) document.get("loadout"));
        new LoadoutSelect(loc, loadout);
        return;
    }

}
