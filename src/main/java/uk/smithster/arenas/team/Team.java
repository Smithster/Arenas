package uk.smithster.arenas.team;

import static uk.smithster.arenas.utils.Profile.profiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

import com.google.gson.JsonObject;

import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.data.dataSchemas.TeamSchema;

public class Team {

    public static HashMap<String, Team> teams = new HashMap<String, Team>();

    private ArrayList<Player> players = new ArrayList<Player>();

    private UUID id;
    private String name;
    private Spawn spawn;
    private Score score;
    private ChatColor color;
    private org.bukkit.scoreboard.Team bukkitTeam;

    public Team(String name, Spawn spawn) {
        this.name = name;
        this.spawn = spawn;
        teams.put(name, this);
    }

    public Team(String name, Spawn spawn, String colour) {
        this.name = name;
        this.spawn = spawn;
        this.color = Data.getChatColor(colour);
        teams.put(name, this);
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Integer size() {
        return this.players.size();
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void playerJoin(Player player) {
        this.players.add(player);
        profiles.get(player.getUniqueId()).setTeam(this);
    }

    public void playerLeave(Player player) {
        this.players.remove(player);
    }

    public void clear() {
        this.players.clear();
        this.score.setScore(0);
    }

    public boolean hasPlayer(Player player) {
        return this.players.contains(player);
    }

    public void initScore(Score score){
        this.score = score;
        this.score.setScore(0);
    }

    public void gainPoint(Integer points) {
        this.score.setScore(this.score.getScore() + points);
        for (Player player : this.players) {
            player.sendMessage("Your team gained a point");
            player.sendMessage(this.score.toString());
        }
    }

    public void removePoint(Integer points) {
        this.score.setScore(this.score.getScore() - points);
    }

    public Integer getScore() {
        return this.score.getScore();
    }

    public void setScore(Integer points) {
        this.score.setScore(points);
    }

    public void setSpawn(Spawn spawn) {
        this.spawn = spawn;
    }

    public Spawn getSpawn() {
        return this.spawn;
    }

    public void spawn() {
        for (Player player : players) {
            player.sendMessage(String.format("Good luck, you're in the team: %s", this.name));
            this.spawn.spawn(player);
        }
    }

    public void setBukkitTeam(org.bukkit.scoreboard.Team bukkitTeam){
        this.bukkitTeam = bukkitTeam;
        bukkitTeam.addEntry(this.name);
    }

    public org.bukkit.scoreboard.Team getBukkitTeam(){
        return this.bukkitTeam;
    }

    public boolean hasColor(){
        return this.color == null? false : true;
    }

    public void setChatColor(String color){
        this.color = Data.getChatColor(color);
    }

    public ChatColor getChatColor(){
        return this.color;
    }

    public String getColoredName(){
        return this.color + this.name;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public UUID getId() {
        return this.id;
    }

    public void save() {
        TeamSchema team = new TeamSchema(this);

        UUID insertedId = Data.save(team);
        this.id = insertedId == null ? this.id : insertedId;
    }

    public static void load(JsonObject document) {
        String name = document.get("name").getAsString();
        Spawn spawn = Spawn.spawns.get(document.get("spawn").getAsString());
        String colour = document.get("colour").getAsString();
        Team team = colour == null ? new Team(name, spawn) : new Team(name, spawn, colour);
        team.id = UUID.fromString(document.get("id").getAsString());
        return;
    }
}
