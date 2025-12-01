package uk.smithster.arenas.data.dataSchemas;

import java.util.Set;

import uk.smithster.arenas.utils.Profile;

public class ProfileSchema extends DataSchema {
    static final String schemaType = "profile";
    static final String path = "./saved_data/profiles.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, Profile.jsonData, Profile.class);

    Set<String> permissions;
    String role;

    public String getPath() {
        return path;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public ProfileSchema(Profile profile) {
        this.id = profile.getPlayer().getUniqueId();
        this.permissions = profile.getPerms().getPermissions().keySet();
        this.role = profile.getRole();
    }
}
