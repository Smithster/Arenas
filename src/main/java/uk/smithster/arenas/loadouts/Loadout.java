package uk.smithster.arenas.loadouts;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.data.dataSchemas.LoadoutSchema;

public class Loadout {

    public static HashMap<String, Loadout> loadouts = new HashMap<String, Loadout>();
    
    private Integer id;
    private String name;
    private ItemStack[] contents;

    public Loadout(String name, PlayerInventory inventory){
        this.name = name;
        this.contents = inventory.getContents().clone();
        loadouts.put(name, this);
    }

    public Loadout(String name, ItemStack[] contents){
        this.name = name;
        this.contents = contents;
        loadouts.put(name, this);
    }

    public void giveItems(Player player){
        player.getInventory().setContents(this.contents);
    }

    public String getName(){
        return this.name;
    }

    public Integer getId() {
        return this.id;
    }

    public ItemStack[] getContents() {
        return this.contents;
    }

    public void save(){
        LoadoutSchema loadout = new LoadoutSchema(this);

        Integer insertedId = Data.save(loadout);
        this.id = insertedId == null ? this.id : insertedId;
    }

    public static void load(JsonObject document){
        String name = document.get("name").getAsString();
        ArrayList<ItemStack> contentsArr = new ArrayList<ItemStack>();
        JsonArray items = document.get("items").getAsJsonArray();
        for (JsonElement itemDocumentElement : items){
            JsonObject itemDocument = (JsonObject) itemDocumentElement;
            String type = itemDocument.get("type").getAsString();
            Material material = Material.getMaterial(type);
            Integer amount = itemDocument.get("amount").getAsInt();
            ItemStack item = new ItemStack(material, amount);
            contentsArr.add(item);
        }

        ItemStack[] contents = new ItemStack[contentsArr.size()];
        contentsArr.toArray(contents);
        new Loadout(name, contents);
        return;
    }

}
