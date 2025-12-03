package uk.smithster.arenas.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import uk.smithster.arenas.loadouts.LoadoutSelect;

public class useLoadoutTools implements Listener {

    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {

        if (event.getClickedBlock() == null) {
            return;
        }

        LoadoutSelect ls = LoadoutSelect.LoadoutSelects.get(event.getClickedBlock().getLocation());

        if (ls == null) {
            return;
        }

        event.setCancelled(true);
        ls.use(event.getPlayer());
        return;
    }
}
