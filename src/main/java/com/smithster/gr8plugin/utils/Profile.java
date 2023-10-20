package com.smithster.gr8plugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bson.Document;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;

import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.classes.Arena;
import com.smithster.gr8plugin.classes.Team;
import com.smithster.gr8plugin.gamemodes.gamemode;

public class Profile {

    private Player player;
    private OfflinePlayer offlinePlayer;
    private Team team;
    private Arena arena;
    private gamemode gamemode;
    private String role;
    private Integer experience;
    public boolean isSettingTrigger = false;
    private String triggerName;
    private PermissionAttachment perms;

    public static Plugin plugin;
    public static HashMap<UUID, Profile> profiles = new HashMap<UUID, Profile>();
    public static HashMap<UUID, Profile> offlineProfiles = new HashMap<UUID, Profile>();

    public Profile(Player player) {
        this.player = player;
        this.perms = this.player.addAttachment(plugin);
        this.role = "default";
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
        document.put("UUID", this.player.getUniqueId().toString());
        if (this.perms == null) {
            document.put("perms", null);
        } else {
            document.put("perms", this.perms.getPermissions().keySet().toArray());
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
        UUID playerID = UUID.fromString((String) document.get("UUID"));
        OfflinePlayer offlinePlayer = Plugin.server.getOfflinePlayer(playerID);
        Profile profile = new Profile(offlinePlayer);
        if (document.get("perms") != null) {
            String[] permsList = (String[]) document.get("perms", String[].class);
            for (String perm : permsList) {
                profile.addPermission(perm);
            }
        }
        if (document.get("role") != null) {
            profile.setRole((String) document.get("role"));
        }
        profiles.put(playerID, profile);
        return;
    }

    public void addPermission(String permission) {
        this.perms.setPermission(permission, true);
    }

    public void removePermission(String permission) {
        this.perms.unsetPermission(permission);
    }

    public void toggleIsSettingTrigger() {
        this.isSettingTrigger = !(this.isSettingTrigger);
    }

    public String getTriggerName() {
        return this.triggerName;
    }

    public void setTriggerName(String name) {
        this.triggerName = name;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return this.team;
    }

    public void handleKill(UUID killed) {
        if (this.gamemode.getType().equals("TDM")) {
            gamemode.gainPoint(team);
        }
    };

    public void setPlayer(Player player) {
        this.player = player;
        PermissionAttachment newPerms = player.addAttachment(plugin);

        if (this.perms != null && this.perms.getPermissions().size() != 0) {
            for (String permission : this.perms.getPermissions().keySet()) {
                newPerms.setPermission(permission, true);
            }
        }

        this.perms = newPerms;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // AS OF YET NOT USED
    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public Arena getArena() {
        return this.arena;
    }

    public Player getPlayer() {
        return this.player;
    }

}
