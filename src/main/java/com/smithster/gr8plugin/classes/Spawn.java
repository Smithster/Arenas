package com.smithster.gr8plugin.classes;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.entity.Player;

public class Spawn extends Plot {

    private Team team;

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void spawn(Player player) {
        player.teleport(this.getEntryLoc());
    }

}
