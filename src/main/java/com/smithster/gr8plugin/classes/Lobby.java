package com.smithster.gr8plugin.classes;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Lobby extends Plot {

    private ArrayList<UUID> players;
    private Integer limit;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void playerJoin(UUID playerUUID) {
        this.players.add(playerUUID);
    }

    public boolean isLobbyFull() {
        if (this.players.size() >= this.limit) {
            return true;
        }

        return false;
    }

    public void enter(Player player) {
        this.playerJoin(player.getUniqueId());
        player.teleport(this.getEntryLoc());
    }
}
