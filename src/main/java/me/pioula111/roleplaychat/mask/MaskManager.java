package me.pioula111.roleplaychat.mask;

import me.pioula111.roleplaychat.Roleplaychat;
import me.pioula111.roleplaychat.ids.CustomNameTag;
import me.pioula111.roleplaychat.ids.IdManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MaskManager implements Listener {
    private IdManager idManager;
    private Roleplaychat plugin;

    public MaskManager(IdManager idManager, Roleplaychat plugin) {
        this.idManager = idManager;
        this.plugin = plugin;
    }

    private void wearMask(Player player) {
        if (Mask.isWearingMask(player)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.isOnline())
                        CustomNameTag.removeNameTag(player);
                }
            }.runTaskLater(this.plugin, 1);
        }
        else if (!CustomNameTag.hasNameTag(player)){
            CustomNameTag.setIdAsCustomNameTag(player, String.valueOf(idManager.getIdByPlayer(player)));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline())
                    wearMask(player);
            }
        }.runTaskLater(this.plugin, 0);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline())
                    wearMask(player);
            }
        }.runTaskLater(this.plugin, 1);
    }

    @EventHandler
    public void onInterract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline())
                    wearMask(player);
            }
        }.runTaskLater(this.plugin, 1);
    }


}
