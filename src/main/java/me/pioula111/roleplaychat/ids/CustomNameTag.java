package me.pioula111.roleplaychat.ids;

import me.pioula111.roleplaychat.Roleplaychat;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomNameTag implements Listener {
    private static final int INFINITY = 2000000000;
    private IdManager idManager;
    private Roleplaychat plugin;

    public CustomNameTag(IdManager idManager, Roleplaychat plugin) {
        this.idManager = idManager;
        this.plugin = plugin;
    }


    private void spawnNameTag(Entity vehicle, String id, Location spawnPoint, int duration)
    {
        // spawn name tag away from player in same chunk, then set invisible
        AreaEffectCloud nameTag = (AreaEffectCloud) spawnPoint.getWorld().spawnEntity(spawnPoint, EntityType.AREA_EFFECT_CLOUD);
        nameTag.setParticle(Particle.TOWN_AURA);
        nameTag.setRadius(0);

        // mount over vehicle and set name
        vehicle.addPassenger(nameTag);
        nameTag.setCustomName(id);
        nameTag.setCustomNameVisible(true);

        // set duration and return
        nameTag.setWaitTime(0);
        nameTag.setDuration(duration);
    }

    private void setIdAsCustomNameTag(Player player, String id)
    {
        Location spawnPoint = player.getLocation();
        spawnPoint.setY(-1);

        spawnNameTag(player, id, spawnPoint, INFINITY);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        int id = idManager.getIdByPlayer(event.getPlayer());
        event.getPlayer().setDisplayName(String.valueOf(id));
        sheduleIdNaming(event.getPlayer(), String.valueOf(id));
    }

    private void sheduleIdNaming(Player player, String playerId)
    {
        new BukkitRunnable() {
            @Override
            public void run() {
                setIdAsCustomNameTag(player, playerId);
            }
        }.runTaskLater(this.plugin, 20);
    }
}
