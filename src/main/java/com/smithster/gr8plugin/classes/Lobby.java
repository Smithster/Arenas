package com.smithster.gr8plugin.classes;

import java.util.ArrayList;

public class Lobby extends Plot {

    private ArrayList<String> players;
    private Integer limit;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void playerJoin(String playerUUID) {
        this.players.add(playerUUID);
    }

    public boolean isLobbyFull() {
        if (this.players.size() >= this.limit) {
            return true;
        }

        return false;
    }

}
