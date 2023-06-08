package com.smithster.gr8plugin.handlers;

import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static com.smithster.gr8plugin.Plugin.Arenas;

public class leaveArena implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        for (Arena arena : Arenas) {

        }

        if (event.getPlayer()) {

        }

    }

}
