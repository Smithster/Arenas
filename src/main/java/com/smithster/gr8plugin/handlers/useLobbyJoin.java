package com.smithster.gr8plugin.handlers;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.smithster.gr8plugin.classes.LobbyJoin;

import static com.smithster.gr8plugin.classes.LobbyJoin.getLobbyJoin;

public class useLobbyJoin implements Listener {

    @EventHandler
    public void onRedstoneTrigger(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            LobbyJoin lobbyJoin = getLobbyJoin(block);

            if (lobbyJoin != null) {
                event.setCancelled(true);
                Player player = event.getPlayer();
                lobbyJoin.join(player);
            }
        }
    }
}
