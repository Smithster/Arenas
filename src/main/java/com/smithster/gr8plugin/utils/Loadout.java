package com.smithster.gr8plugin.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Loadout {

    public static HashMap<String, Loadout> loadouts = new HashMap<String, Loadout>();
    
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

    public void save(){
        Document document = new Document();
        ArrayList<Document> items = new ArrayList<Document>();
        for (ItemStack item : this.contents){
            String material;
            Integer amount;
            if (item == null){
                material = "AIR";
                amount = 0;
            } else {
                material = item.getType().name();
                amount = item.getAmount();
            }
            Document itemDocument = new Document();
            itemDocument.put("type", material);
            itemDocument.put("amount", amount);
            items.add(itemDocument);
        }
        document.put("name", this.name);
        document.put("items", items);
        Data.save("loadouts", document);
    }

    public static void load(Document document){
        String name = (String) document.get("name");
        ArrayList<ItemStack> contentsArr = new ArrayList<ItemStack>();
        ArrayList<Document> items = (ArrayList<Document>) document.get("items");
        for (Document itemDocument : items){
            String type = (String) itemDocument.get("type");
            Material material = Material.getMaterial(type);
            Integer amount = (Integer) itemDocument.get("amount");
            ItemStack item = new ItemStack(material, amount);
            contentsArr.add(item);
        }

        ItemStack[] contents = new ItemStack[contentsArr.size()];
        contentsArr.toArray(contents);
        new Loadout(name, contents);
        return;
    }

}
