package me.pioula111.roleplaychat.customNameTag;

import me.pioula111.roleplaychat.Roleplaychat;
import me.pioula111.roleplaychat.mask.Mask;
import org.bukkit.Bukkit;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;

public class NameTagManager implements Listener {
    Roleplaychat plugin;

    public NameTagManager(Roleplaychat plugin) {
        this.plugin = plugin;
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
        if (event.getEntity() instanceof AreaEffectCloud && event.getEntity().getCustomName() != null) {
            if (event.getDismounted() instanceof Player) {
                if (event.getEntity().getCustomName().equals(event.getDismounted().getCustomName())) {
                    event.getEntity().remove();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Player player = (Player) event.getDismounted();
                            if (((Player) event.getDismounted()).isOnline() && !Mask.isWearingMask((Player) event.getDismounted()))
                                CustomNameTag.setCustomNametag(player, CustomNameTag.getNameTag(player));
                        }
                    }.runTaskLater(this.plugin, 0);
                }
            }
        }
    }
}
