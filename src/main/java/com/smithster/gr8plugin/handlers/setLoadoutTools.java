package com.smithster.gr8plugin.handlers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.smithster.gr8plugin.classes.LoadoutSelect;
import com.smithster.gr8plugin.utils.Profile;

public class setLoadoutTools implements Listener{

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();

        Profile profile = Profile.profiles.get(player.getUniqueId());

        if(!profile.isSettingSelect()){
            return;
        }

        if (event.getBlock() == null){
            return;
        }

        Location loc = event.getBlock().getLocation();

        if(LoadoutSelect.LoadoutSelects.containsKey(loc)){
            player.sendMessage("Loadout select already exists at this location.");
        }

        player.sendMessage("Loudout select set!");
        event.setCancelled(true);
        LoadoutSelect ls = profile.getSelect();
        ls.setLocation(loc);
        ls.save();
        profile.settingSelect(false);
        
    }
}
