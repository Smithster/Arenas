package uk.smithster.arenas.handlers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import uk.smithster.arenas.arena.Flag;
import uk.smithster.arenas.utils.Profile;

public class setArenaTools implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        Profile profile = Profile.profiles.get(player.getUniqueId());

        if (!profile.isSettingFlag()) {
            return;
        }

        if (event.getBlock() == null) {
            return;
        }

        Location loc = event.getBlock().getLocation();

        if (Flag.flags.containsKey(loc)) {
            player.sendMessage("Flag already exists at this location.");
        }

        player.sendMessage("Flag set!");
        event.setCancelled(true);
        Flag flag = profile.getFlag();
        flag.setLocation(loc);
        flag.save();
        profile.settingFlag(false);

    }
}
