package uk.smithster.arenas.data.dataSchemas;

import java.util.Set;

import com.google.gson.JsonArray;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.utils.Profile;

public class ProfileSchema extends DataSchema {
    static String schemaType = "profile";
    static String path = "./saved_data/profiles.json";
    static JsonArray jsonData = Data.profiles;

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, jsonData);

    String id;
    Set<String> permissions;
    String role;

    public ProfileSchema(Profile profile) {
        this.id = profile.getPlayer().getUniqueId().toString();
        this.permissions = profile.getPerms().getPermissions().keySet();
        this.role = profile.getRole();
    }
}
