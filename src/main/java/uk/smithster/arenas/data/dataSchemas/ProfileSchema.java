package uk.smithster.arenas.data.dataSchemas;

import java.util.Set;
import java.util.UUID;

import com.google.gson.JsonObject;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.utils.Profile;

public class ProfileSchema extends DataSchema {
    static String schemaType = "profile";
    static String path = "./saved_data/profiles.json";
    static JsonObject jsonData = Data.profiles;

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, jsonData);

    UUID id;
    Set<String> permissions;
    String role;

    public ProfileSchema(Profile profile) {
        this.id = profile.getPlayer().getUniqueId();
        this.permissions = profile.getPerms().getPermissions().keySet();
        this.role = profile.getRole();
    }
}
