package me.pioula111.roleplaychat.mask;

import me.pioula111.roleplaychat.Roleplaychat;
import me.pioula111.roleplaychat.customNameTag.CustomNameTag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MaskManager implements Listener {
    private Roleplaychat plugin;

    public MaskManager(Roleplaychat plugin) {
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
            }.runTaskLater(this.plugin, 0);
        }
        else if (!CustomNameTag.hasNameTag(player)){
            CustomNameTag.setCustomNametag(player, CustomNameTag.getNameTag(player));
        }
    }
    //shedulery dlatego, bo najpierw jest kliknięcie, a potem zakłada hełm na głowe
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
        }.runTaskLater(this.plugin, 0);
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
        }.runTaskLater(this.plugin, 0);
    }


}
