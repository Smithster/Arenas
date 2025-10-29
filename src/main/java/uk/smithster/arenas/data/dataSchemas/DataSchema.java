package uk.smithster.arenas.data.dataSchemas;

import com.google.gson.JsonArray;

public abstract class DataSchema {
    public Integer id;
    public static String schemaType;
    public static String path;
    public static JsonArray jsonData;
    public SchemaMetaData metaData;
    public String getPath(){
        return path;
    };

    public JsonArray getJsonArray() {
        return jsonData;
    }

    public Integer getId() {
        return this.id;
    }
}
