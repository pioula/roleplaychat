package me.pioula111.roleplaychat.ids;

import me.pioula111.roleplaychat.Roleplaychat;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;

public class NameTagManager implements Listener {
    Roleplaychat plugin;
    CustomNameTag customNameTag;
    IdManager idManager;

    public NameTagManager(Roleplaychat plugin, IdManager idManager) {
        this.plugin = plugin;
        this.idManager = idManager;
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
