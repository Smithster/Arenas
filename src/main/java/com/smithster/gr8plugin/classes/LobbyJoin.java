package com.smithster.gr8plugin.classes;

import java.util.ArrayList;
import java.util.HashMap;

// import static com.smithster.gr8plugin.Plugin.lobbyJoins;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class LobbyJoin {
    // Static stored map of lobbyJoins
    public static HashMap<String, LobbyJoin> lobbyJoins;

    // Class variables
    private Integer blockX;
    private Integer blockY;
    private Integer blockZ;
    private String world;
    private boolean active = true;
    private Lobby lobby;

    // Static methods for lobbyJoins

    public static Boolean isLobbyJoin(Block block) {
        Location loc = block.getLocation();

        for (LobbyJoin lobbyJoin : lobbyJoins.values()) {
            if (lobbyJoin.isLocated(loc)) {
                return true;
            } else {
                continue;
            }
        }

        return false;
    }

    public static LobbyJoin getLobbyJoin(Block block) {
        Location loc = block.getLocation();

        for (LobbyJoin lobbyJoin : lobbyJoins.values()) {
            if (lobbyJoin.isLocated(loc)) {
                return lobbyJoin;
            } else {
                continue;
            }
        }

        return null;
    }

    // Class methods
    public boolean isActive() {
        return this.active;
    }

    public boolean isLocated(Location loc) {

        if (loc.getBlockX() == this.blockX && loc.getBlockY() == this.blockY
                && loc.getBlockZ() == this.blockZ && loc.getWorld().getName() == this.world) {
            return true;
        }

        return false;
    }

    public void setLocation(ArrayList<Integer> pos, String world) {
        this.blockX = pos.get(0);
        this.blockY = pos.get(1);
        this.blockZ = pos.get(2);
        this.world = world;
    }

    public void attach(Lobby lobby) {
        this.lobby = lobby;
    }

    public void save(String name) {
        lobbyJoins.put(name, this);
    }

    public void join(Player player) {
        lobby.enter(player);
    }

}
