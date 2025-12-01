package uk.smithster.arenas.data.dataSchemas;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.team.Team;

public class TeamSchema extends DataSchema{
    static final String schemaType = "team";
    static final String path = "./saved_data/teams.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, Team.jsonData, Team.class);

    String name;
    String spawn;
    String color;

    public String getPath() {
        return path;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public TeamSchema(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.spawn = team.getSpawn().getName();
        this.color = team.hasColor()? Data.getColorString(team.getColor()) : null;
    }
}
