package me.pioula111.roleplaychat.proximityCommands;

import me.pioula111.roleplaychat.Roleplaychat;
import me.pioula111.roleplaychat.jsonManager.AllPlayersData;
import me.pioula111.roleplaychat.mask.Mask;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class ProximityCommands {
    protected FileConfiguration config;
    private Roleplaychat plugin;
    private AllPlayersData allPlayersData;

    public ProximityCommands(Roleplaychat plugin, AllPlayersData allPlayersData) {
        this.config = plugin.getConfig();
        this.plugin = plugin;
        this.allPlayersData = allPlayersData;
    }

    protected ArrayList<Player> getNearByPlayers(Player player, double distance) {
        int squaredDistance = (int)(distance * distance);
        ArrayList<Player> result = new ArrayList<>();
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getWorld() == player.getWorld() && p.getLocation().distanceSquared(player.getLocation()) <= squaredDistance) {
                result.add(p);
            }
        }

        return result;
    }

    private String getCorrectName(Player player, Player friend) {
        if (Mask.isWearingMask(friend))
            return "Zamaskowany";
        if (friend.getName().equals(player.getName()))
            return player.getDisplayName() + "|Ja";
        String name =  allPlayersData.getFriendsName(player, friend);
        if (name == null)
            return friend.getDisplayName();
        return friend.getDisplayName() + "|" + name;
    }

    protected void sendMessages(Player sender, StringBuilder prefix, String[] words, double distance) {
        StringBuilder sufix = new StringBuilder();

        for (String word : words) {
            sufix.append(word);
        }

        ArrayList<Player> nearByPlayers = getNearByPlayers(sender, distance);

        for (Player p : nearByPlayers) {
            p.sendMessage(prefix.toString() + " " + getCorrectName(p, sender) + ": " + sufix);
        }
    }
}
