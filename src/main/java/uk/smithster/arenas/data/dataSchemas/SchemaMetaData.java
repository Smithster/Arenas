package uk.smithster.arenas.data.dataSchemas;

import java.util.function.Function;

import com.google.gson.JsonObject;

public class SchemaMetaData {
    public String schemaType;
    public String path;
    public JsonObject jsonData;
    public Function<JsonObject, Void> load;

    public String getPath() {return this.path;};
    
    public void load(JsonObject data) {
        this.load.apply(data);
    }

    public SchemaMetaData(String schemaType, String path, JsonObject jsonData, Function<JsonObject, Void> load) {
        this.schemaType = schemaType;
        this.path = path;
        this.jsonData = jsonData;
        this.load = load;
    }
}
