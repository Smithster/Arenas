package uk.smithster.arenas.data.dataSchemas;

import java.util.UUID;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.loadouts.Loadout;

public class LoadoutSchema extends DataSchema {
    static final String schemaType = "loadout";
    static JsonObject jsonData = Data.loadouts;
    static final String path = "./saved_data/loadouts.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, jsonData);

    UUID id;
    String name;
    JsonArray items;

    public class ItemStackData {
        public String material;
        public Integer amount;

        public ItemStackData(ItemStack item) {
            if (item == null){
                this.material = "AIR";
                this.amount = 0;
            } else {
                this.material = item.getType().name();
                this.amount = item.getAmount();
            }
        }
    }

    public String getPath() {
        return path;
    }

    public LoadoutSchema(Loadout loadout) {
        this.id = loadout.getId();
        this.name = loadout.getName();
        for (ItemStack item : loadout.getContents()){
            ItemStackData itemDocument = new ItemStackData(item);
            
            items.add(Data.getInventoryArray(itemDocument));
        }
    }
}
