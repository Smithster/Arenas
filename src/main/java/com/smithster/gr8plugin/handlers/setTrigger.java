package com.smithster.gr8plugin.handlers;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import static com.smithster.gr8plugin.Plugin.states;

public class setTrigger implements Listener {

    @EventHandler
    public void onBlockDamage(Player player, Block block, ItemStack itemInHand, boolean instaBreak){

        Material[] interactableMaterials = {
                            Material.STONE_BUTTON, 
                            Material.OAK_BUTTON,
                            Material.BIRCH_BUTTON,
                            Material.JUNGLE_BUTTON,
                            Material.ACACIA_BUTTON,
                            Material.SPRUCE_BUTTON,
                            Material.DARK_OAK_BUTTON,
                        };

        if (!(Arrays.asList(interactableMaterials).contains(block.getType()))){
            return;
        }

        if (states.get(player.getName()).isSettingTrigger){
            
        }
        block.getLocation()
    }

}
