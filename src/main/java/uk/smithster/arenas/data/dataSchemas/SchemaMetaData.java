package uk.smithster.arenas.data.dataSchemas;

import com.google.gson.JsonObject;

import uk.smithster.arenas.data.Storable;

public class SchemaMetaData {
    public String schemaType;
    public String path;
    public JsonObject jsonData;
    public Class<? extends Storable> storableClass;

    public String getPath() {return this.path;};

    public SchemaMetaData(String schemaType, String path, JsonObject jsonData, Class<? extends Storable> storableClass) {
        this.schemaType = schemaType;
        this.path = path;
        this.jsonData = jsonData;
        this.storableClass = storableClass;
    }
}
