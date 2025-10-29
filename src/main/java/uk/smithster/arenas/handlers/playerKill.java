package uk.smithster.arenas.handlers;

import static uk.smithster.arenas.utils.Profile.profiles;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

import uk.smithster.arenas.arena.Arena;
import uk.smithster.arenas.utils.Profile;

public class playerKill implements Listener {

    private Plugin plugin;

    public playerKill(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {

        Entity hit = event.getEntity();
        Entity hitter = event.getDamager();

        if (!(hit instanceof Player) || !(hitter instanceof Player)) {
            return;
        }

        Player killed = (Player) hit;
        Player killer = (Player) hitter;

        Double damage = event.getFinalDamage();
        Double killedHealth = killed.getHealth();

        if (damage < killedHealth) {
            return;
        }

        Profile killerProfile = profiles.get(killer.getUniqueId());
        Profile killedProfile = profiles.get(killed.getUniqueId());

        Arena arena = killedProfile.getArena();
        if (arena == null) {
            return;
        }
        event.setCancelled(true);

        if (arena != killerProfile.getArena()) {
            return;
        }

        killedProfile.respawn();
        killed.setHealth(killed.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
        arena.handleKill(killerProfile, killedProfile);
        return;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player killed = (Player) event.getEntity();
        Player killer = (Player) killed.getKiller();

        if (killer == null) {
            return;
        }

        Profile killerProfile = profiles.get(killer.getUniqueId());
        Profile killedProfile = profiles.get(killed.getUniqueId());

        Arena arena = killerProfile.getArena();
        if (arena == null || !(arena == killedProfile.getArena())) {
            return;
        }
        arena.handleKill(killerProfile, killedProfile);
        return;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        final Profile profile = Profile.profiles.get(player.getUniqueId());
        final Runnable backToLobby = () -> {
            profile.getLobby().moveBackToLobby(player);
        };
        player.sendMessage(profile.getLobby() == null ? "Respawning normally" : "Sending back to lobby");
        if (profile.getArena() == null && profile.getLobby() != null) {
            Bukkit.getScheduler().runTaskLater(plugin, backToLobby, 1L);
        }
    }
}
