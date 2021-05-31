package me.pioula111.roleplaychat.customNameTag;

import me.pioula111.roleplaychat.Roleplaychat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomNameTag implements Listener {
    private static final int INFINITY = 2000000000;
    private static Roleplaychat plugin;

    public CustomNameTag(Roleplaychat plugin) {
        this.plugin = plugin;
    }


    private static void spawnNameTag(Entity vehicle, String name, Location spawnPoint)
    {
        // spawn name tag away from player in same chunk, then set invisible
        AreaEffectCloud nameTag = (AreaEffectCloud) spawnPoint.getWorld().spawnEntity(spawnPoint, EntityType.AREA_EFFECT_CLOUD);
        nameTag.setParticle(Particle.TOWN_AURA);
        nameTag.setRadius(0);

        // mount over vehicle and set name
        vehicle.addPassenger(nameTag);
        nameTag.setCustomName(name);
        nameTag.setCustomNameVisible(true);

        // set duration and return
        nameTag.setWaitTime(0);
        nameTag.setDuration(CustomNameTag.INFINITY);
    }

    public static boolean hasNameTag(Player player) {
        for (Entity entity : player.getPassengers()) {
            if (entity.getType() == EntityType.AREA_EFFECT_CLOUD)
                return true;
        }

        return false;
    }

    public static void removeNameTag(Player player) {
        for (Entity entity : player.getPassengers()) {
            if (entity.getType() == EntityType.AREA_EFFECT_CLOUD && entity.getCustomName() != null &&
                    entity.getCustomName().equals(player.getCustomName())) {
                entity.setCustomName(null);
                entity.remove();
            }
        }
    }

    public static void komunikatZmienImie(Player player) {
        player.sendMessage(ChatColor.RED + "Pamiętaj aby zmienić imię swojej postaci! /imie <imie>");
    }

    public static String getNameTag(Player player) {
        if (player.getCustomName() == null) {
            player.setCustomName("Bezimienny");
            komunikatZmienImie(player);
        }
        return player.getCustomName();
    }

    private static void setNameAsCustomNameTag(Player player, String name)
    {
        Location spawnPoint = player.getLocation();
        spawnPoint.setY(-1);
        player.setDisplayName(player.getCustomName());
        spawnNameTag(player, name, spawnPoint);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setDisplayName(getNameTag(event.getPlayer()));
        sheduleNaming(event.getPlayer(), getNameTag(event.getPlayer()));
    }

    public static void setCustomNametag(Player player, String name) {
        sheduleNaming(player, name);
    }

    private static void sheduleNaming(Player player, String playerName)
    {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline())
                    setNameAsCustomNameTag(player, playerName);
            }
        }.runTaskLater(plugin, 0);
    }
}
