package me.pioula111.roleplaychat.ids;

import me.pioula111.roleplaychat.Roleplaychat;
import org.bukkit.Bukkit;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;

public class NameTagManager implements Listener {
    Roleplaychat plugin;
    IdManager idManager;

    public NameTagManager(Roleplaychat plugin, IdManager idManager) {
        this.plugin = plugin;
        this.idManager = idManager;
    }

    public static void clearNameTags() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            for (Entity entity : player.getPassengers()) {
                if (entity.getType() == EntityType.AREA_EFFECT_CLOUD)
                    entity.remove();
            }
        }
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
        if (event.getEntity() instanceof AreaEffectCloud) {
            if (event.getDismounted() instanceof Player) {
                event.getEntity().remove();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Player player = (Player) event.getDismounted();
                        if (((Player) event.getDismounted()).isOnline())
                            CustomNameTag.setIdAsCustomNameTag(player, String.valueOf(idManager.getIdByPlayer(player)));
                    }
                }.runTaskLater(this.plugin, 0);
            }
        }
    }
}
