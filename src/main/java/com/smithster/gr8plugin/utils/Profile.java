package com.smithster.gr8plugin.utils;

import java.util.UUID;

import org.bukkit.entity.Player;

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

    public Profile(Player player) {
        this.player = player;
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
