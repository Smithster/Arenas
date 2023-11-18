package com.smithster.gr8plugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;

import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.classes.Arena;
import com.smithster.gr8plugin.classes.LoadoutSelect;
import com.smithster.gr8plugin.classes.Lobby;
import com.smithster.gr8plugin.classes.LobbyJoin;
import com.smithster.gr8plugin.classes.LobbyLeave;
import com.smithster.gr8plugin.classes.LobbyStart;
import com.smithster.gr8plugin.classes.LobbyVote;
import com.smithster.gr8plugin.classes.Team;
import com.smithster.gr8plugin.gamemodes.Gamemode;

public class Profile {

    private Player player;
    private OfflinePlayer offlinePlayer;
    private Team team;
    private Arena arena;
    private Lobby lobby;
    private Gamemode gamemode;
    private String role;
    // private Integer experience;
    private boolean settingJoin = false;
    private boolean settingLeave = false;
    private boolean settingVote = false;
    private boolean settingStart = false;
    private boolean settingSelect = false;
    private LobbyJoin join;
    private LobbyLeave leave;
    private LobbyVote vote;
    private LobbyStart start;
    private LoadoutSelect select;
    private Set<String> permList;
    private PermissionAttachment perms;
    private Party party;
    private Location savedSpawn;

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

    public void save() {
        Document document = new Document();

        // Object permissions = (Object) this.perms.getPermissions();
        document.put("_id", this.player.getUniqueId().toString());
        if (this.perms == null) {
            document.put("perms", null);
        } else {
            // ArrayList<String> permList = new
            // ArrayList<String>(this.perms.getPermissions().keySet());
            document.put("perms", new ArrayList<String>(this.perms.getPermissions().keySet()));
        }
        if (this.role == null) {
            document.put("role", null);
        } else {
            document.put("role", this.role);
        }

        Data.save("profiles", document);
        return;
    }

    public static void load(Document document) {
        UUID playerID = UUID.fromString((String) document.get("_id"));
        OfflinePlayer offlinePlayer = Plugin.server.getOfflinePlayer(playerID);
        Profile profile = new Profile(offlinePlayer);
        profile.setOfflinePermList(new HashSet<String>());
        if (document.get("perms") != null) {
            Set<String> permsList = new HashSet<String>((ArrayList<String>) document.get("perms"));
            profile.setOfflinePermList(permsList);
        }
        if (document.get("role") != null) {
            profile.setRole((String) document.get("role"));
        }
        profiles.put(playerID, profile);
        return;
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
