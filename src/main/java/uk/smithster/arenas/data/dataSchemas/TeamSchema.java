package uk.smithster.arenas.data.dataSchemas;

import com.google.gson.JsonArray;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.team.Team;

public class TeamSchema extends DataSchema{
    static final String schemaType = "team";
    static JsonArray jsonData = Data.teams;
    static final String path = "./saved_data/teams.json";

    public static SchemaMetaData metaData = new SchemaMetaData(schemaType, path, jsonData);

    Integer id;
    String name;
    String spawn;
    String color;

    public String getPath() {
        return path;
    }

    public TeamSchema(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.spawn = team.getSpawn().getName();
        this.color = team.hasColor()? Data.getColorString(team.getColor()) : null;
    }
}
