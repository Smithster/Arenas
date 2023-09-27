package com.smithster.gr8plugin.classes;

import com.smithster.gr8plugin.gamemodes.gamemode;

public class MatchPlayer {

    private gamemode gamemode;
    private Arena arena;
    private Team team;

    public String getMatchType() {
        return gamemode.getType();
    }
}
