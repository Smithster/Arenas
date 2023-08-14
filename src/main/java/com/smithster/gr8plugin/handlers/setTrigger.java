package com.smithster.gr8plugin.handlers;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.smithster.gr8plugin.classes.Trigger;

import static com.smithster.gr8plugin.Plugin.states;

public class setTrigger implements Listener {

    @EventHandler
    public void onBlockDamage(Player player, Block block, ItemStack itemInHand, boolean instaBreak) {

        Material[] interactableMaterials = {
                Material.STONE_BUTTON,
                Material.OAK_BUTTON,
                Material.BIRCH_BUTTON,
                Material.JUNGLE_BUTTON,
                Material.ACACIA_BUTTON,
                Material.SPRUCE_BUTTON,
                Material.DARK_OAK_BUTTON,
        };

        if (!(Arrays.asList(interactableMaterials).contains(block.getType()))) {
            return;
        }

        if (!states.get(player.getName()).isSettingTrigger) {
            return;
        }

        Trigger trigger = new Trigger();
        Location loc = block.getLocation(null);
        ArrayList<Integer> pos = new ArrayList<Integer>();
        pos.add(loc.getBlockX());
        pos.add(loc.getBlockY());
        pos.add(loc.getBlockZ());
        trigger.setLocation(pos, loc.getWorld().getName());

        trigger.save(states.get(player.getName()).getTriggerName());
    }

}
