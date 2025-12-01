package uk.smithster.arenas.data.dataSchemas;

import java.util.UUID;

import com.google.gson.JsonObject;

public abstract class DataSchema {
    public UUID id;
    public static String schemaType;
    public static String path;
    public static JsonObject jsonData;
    public SchemaMetaData metaData;
    public String getPath(){
        return path;
    };

    public UUID getId() {
        return this.id;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public JsonObject getJsonData() {
        return jsonData;
    }
}
