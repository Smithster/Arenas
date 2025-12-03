package uk.smithster.arenas.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import uk.smithster.arenas.Plugin;
import uk.smithster.arenas.arena.Arena;
import uk.smithster.arenas.arena.Flag;
import uk.smithster.arenas.data.Data;
import uk.smithster.arenas.data.Storable;
import uk.smithster.arenas.data.dataSchemas.ProfileSchema;
import uk.smithster.arenas.data.dataSchemas.SchemaMetaData;
import uk.smithster.arenas.loadouts.LoadoutSelect;
import uk.smithster.arenas.lobby.Lobby;
import uk.smithster.arenas.lobby.LobbyJoin;
import uk.smithster.arenas.lobby.LobbyLeave;
import uk.smithster.arenas.lobby.LobbyStart;
import uk.smithster.arenas.lobby.LobbyVote;
import uk.smithster.arenas.team.Team;

public class Profile implements Storable{

    private Player player;
    private OfflinePlayer offlinePlayer;
    private Team team;
    private Arena arena;
    private Lobby lobby;
    // private Gamemode gamemode;
    private String role;
    // private Integer experience;
    private boolean settingJoin = false;
    private boolean settingLeave = false;
    private boolean settingVote = false;
    private boolean settingStart = false;
    private boolean settingSelect = false;
    private boolean settingFlag = false;
    private LobbyJoin join;
    private LobbyLeave leave;
    private LobbyVote vote;
    private LobbyStart start;
    private LoadoutSelect select;
    private Flag flag;
    private Set<String> permList;
    private PermissionAttachment perms;
    private Party party;
    // private Location savedSpawn;
    public static JsonObject jsonData = new JsonObject();
    
    public SchemaMetaData getStoreMetaData() {
        return ProfileSchema.metaData;
    }

    public static Plugin plugin;
    public static HashMap<UUID, Profile> profiles = new HashMap<UUID, Profile>();
    public static HashMap<UUID, Profile> offlineProfiles = new HashMap<UUID, Profile>();

    public Profile(Player player) {
        this.player = player;
        this.perms = this.player.addAttachment(plugin);
        this.role = "default";
        this.party = new Party(this.player);
    }

    public Profile(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
        this.role = "default";
    }

    public static void setPlugin(Plugin p) {
        plugin = p;
    }

    public PermissionAttachment getPerms() {
        return this.perms;
    }

    public String getRole() {
        return this.role;
    }

    public void save() {
        ProfileSchema profileSchema = new ProfileSchema(this);

        Data.save(profileSchema);
        return;
    }

    public static Void load(JsonObject document) {
        UUID playerID = UUID.fromString( document.get("id").getAsString() );
        OfflinePlayer offlinePlayer = Plugin.server.getOfflinePlayer(playerID);
        Profile profile = new Profile(offlinePlayer);
        profile.setOfflinePermList(new HashSet<String>());
        if (document.get("perms") != null) {
            Set<String> permsList = new HashSet<String>(Data.getStringArrayList((JsonArray) document.get("perms")));
            profile.setOfflinePermList(permsList);
        }
        if (document.get("role") != null) {
            profile.setRole( document.get("role").getAsString() );
        }
        profiles.put(playerID, profile);
        return null;
    }

    public void addPermission(String permission) {
        this.permList.add(permission);
        if (this.offlinePlayer.isOnline()) {
            this.perms.setPermission(permission, true);
        }
    }

    public void removePermission(String permission) {
        this.permList.remove(permission);
        if (this.offlinePlayer.isOnline()) {
            this.perms.unsetPermission(permission);
        }
    }

    public void setOfflinePermList(Set<String> permList) {
        this.permList = permList;
    }

    public Set<String> getOfflinePermList() {
        return this.permList;
    }

    public void setJoin(LobbyJoin join) {
        this.join = join;
    }

    public LobbyJoin getJoin() {
        return this.join;
    }

    public void settingJoin(boolean b) {
        this.settingJoin = b;
    }

    public boolean isSettingJoin() {
        return this.settingJoin;
    }

    public void settingLeave(boolean b) {
        this.settingLeave = b;
    }

    public boolean isSettingLeave() {
        return this.settingLeave;
    }

    public void setLeave(LobbyLeave leave) {
        this.leave = leave;
    }

    public LobbyLeave getLeave() {
        return this.leave;
    }

    public void settingVote(boolean b) {
        this.settingVote = b;
    }

    public boolean isSettingVote() {
        return this.settingVote;
    }

    public void setVote(LobbyVote vote) {
        this.vote = vote;
    }

    public LobbyVote getVote() {
        return this.vote;
    }

    public void settingStart(boolean b) {
        this.settingStart = b;
    }

    public boolean isSettingStart() {
        return this.settingStart;
    }

    public void setStart(LobbyStart start) {
        this.start = start;
    }

    public LobbyStart getStart() {
        return this.start;
    }

    public void settingSelect(boolean b) {
        this.settingSelect = b;
    }

    public boolean isSettingSelect() {
        return this.settingSelect;
    }

    public void setSelect(LoadoutSelect select) {
        this.select = select;
    }

    public LoadoutSelect getSelect() {
        return this.select;
    }

    public void settingFlag(boolean b) {
        this.settingFlag = b;
    }

    public boolean isSettingFlag() {
        return this.settingFlag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public Flag getFlag() {
        return this.flag;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Party getParty() {
        return this.party;
    }

    public boolean isPartyLeader() {
        return this.party.isPartyLeader(this.player);
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.party = new Party(this.player);
        PermissionAttachment perms = player.addAttachment(plugin);

        if (this.permList == null) {
            this.perms = perms;
            return;
        }

        for (String permission : this.permList) {
            perms.setPermission(permission, true);
        }

        this.perms = perms;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public Lobby getLobby() {
        return this.lobby;
    }

    public void setArena(Arena arena) {
        arena.addPlayer(this.player);
        this.arena = arena;
    }

    public void leaveArena() {
        this.arena = null;
        this.lobby.moveBackToLobby(this.player);
    }

    public Arena getArena() {
        return this.arena;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void respawn() {
        this.team.getSpawn().spawn(this.player);
    }

}
