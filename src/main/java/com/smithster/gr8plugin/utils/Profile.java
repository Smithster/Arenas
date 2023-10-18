package com.smithster.gr8plugin.utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import com.smithster.gr8plugin.Plugin;
import com.smithster.gr8plugin.classes.Arena;
import com.smithster.gr8plugin.classes.Team;
import com.smithster.gr8plugin.gamemodes.gamemode;

public class Profile {

    private Player player;
    private Team team;
    private Arena arena;
    private gamemode gamemode;
    private Integer experience;
    public boolean isSettingTrigger = false;
    private String triggerName;
    private PermissionAttachment perms;

    public static HashMap<UUID, Profile> profiles;

    public Profile(Player player, Plugin plugin) {
        this.player = player;
        this.perms = this.player.addAttachment(plugin);
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
