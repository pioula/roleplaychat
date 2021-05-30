package me.pioula111.roleplaychat.mask;

import org.bukkit.entity.Player;

public abstract class Mask {
    public static final String MASK = "Maska";

    public static boolean isWearingMask(Player player) {
        if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasDisplayName())
            return player.getInventory().getHelmet().getItemMeta().getDisplayName().equals(MASK);
        else
            return false;
    }

}
