package com.smithster.gr8plugin.gamemodes;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.smithster.gr8plugin.classes.*;
import com.smithster.gr8plugin.utils.Profile;

public class TeamDeathmatch extends Gamemode {

    public TeamDeathmatch() {
        this.setType("TDM");
        this.setMaxScore(10);
    }

    public void handleKill(Profile killer, Profile killed) {
        killer.getTeam().gainPoint(1);
    }

}
