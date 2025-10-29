package uk.smithster.arenas.data.dataSchemas;

import com.google.gson.JsonObject;

public class SchemaMetaData {
    public String schemaType;
    public String path;
    public JsonObject jsonData;

    public String getPath() {return this.path;};

    public SchemaMetaData(String schemaType, String path, JsonObject jsonData) {
        this.schemaType = schemaType;
        this.path = path;
        this.jsonData = jsonData;
    }
}
