package uk.smithster.arenas.data.dataSchemas;

import com.google.gson.JsonArray;

public class SchemaMetaData {
    public String schemaType;
    public String path;
    public JsonArray jsonData;

    public String getPath() {return this.path;};

    public SchemaMetaData(String schemaType, String path, JsonArray jsonData) {
        this.schemaType = schemaType;
        this.path = path;
        this.jsonData = jsonData;
    }
}
